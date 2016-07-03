package socialite.standalone;


import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;

import socialite.engine.LocalEngine;
import socialite.rpc.Bridge;
import socialite.rpc.FirstEntryVisitor;
import socialite.rpc.SenderVisitor;
import socialite.rpc.query.QueryMessage;
import socialite.rpc.query.QueryService;
import socialite.rpc.TTuple;
import socialite.rpc.query.TQueryError;
import socialite.rpc.queryCallback.QueryCallbackService;
import socialite.rpc.standalone.StandaloneService;
import socialite.tables.Tuple;

import socialite.functions.EnumId;
import java.util.List;

/**
 * Socialite server running on a single machine.
 */
public class SingleNodeServer {
    public static final Log L = LogFactory.getLog(SingleNodeServer.class);
    static final String DEFAULT_PORT = "51001";

    LocalEngine engine;
    TMultiplexedProcessor processor;
    TServer server;
    int port;

    public SingleNodeServer() {
        engine = new LocalEngine();
        processor = new TMultiplexedProcessor();
        processor.registerProcessor("QueryService",
                new QueryService.Processor(new QueryHandler(engine)));
        processor.registerProcessor("StandaloneService",
                new StandaloneService.Processor(new StandaloneServerHandler(this)));
        String _port = System.getProperty("socialite.standalone.port", DEFAULT_PORT);
        port = Integer.parseInt(_port);
    }


    public void serve() throws TTransportException {
        TNonblockingServerTransport trans = new TNonblockingServerSocket(port);
        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(trans);
        args.transportFactory(new TFramedTransport.Factory());
        args.protocolFactory(new TCompactProtocol.Factory());
        args.processor(processor);
        args.selectorThreads(2);
        args.workerThreads(4);
        server = new TThreadedSelectorServer(args);
        server.serve();
    }

    public void stop() {
        server.stop();
        engine.shutdown();
    }

    public static void main(String[] args) {
        SingleNodeServer server = new SingleNodeServer();
        try { server.serve(); }
        catch (Exception e) {
            L.fatal("Cannot run SingleNodeServer:"+e);
        }
    }
}

class StandaloneServerHandler implements StandaloneService.Iface {
    SingleNodeServer server;
    StandaloneServerHandler(SingleNodeServer _server) {
        server = _server;
    }
    @Override
    public void terminate() throws TException {
        server.stop();
    }
}
class QueryHandler implements QueryService.Iface {
    LocalEngine engine;
    QueryCallbackService.Client client;

    QueryHandler(LocalEngine _engine) {
        engine = _engine;
    }
    @Override
    public void runSimple(QueryMessage query) throws TException {
        try {
            engine.run(query.getQuery());
        } catch (Exception e) {
            SingleNodeServer.L.info("Exception:"+ org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            throw new TQueryError(e.getMessage());
        }
    }
    QueryCallbackService.Client getClient(String addr, int port) throws TException {
        if (client == null) {
            client = makeClientConnection(addr, port);
        }
        return client;
    }
    QueryCallbackService.Client makeClientConnection(String addr, int port) throws TException {
        TTransport transport;
        transport = new TSocket(addr, port);
        try {
            transport.open();
        } catch (Exception e) {
            throw new TQueryError(e.getMessage());
        }
        TProtocol protocol = new TCompactProtocol(transport);
        client = new QueryCallbackService.Client(protocol);
        return client;
    }

    @Override
    public void run(QueryMessage query, String addr, int port, long queryid) throws TException {
        QueryCallbackService.Client _client = getClient(addr, port);
        SenderVisitor sender = new SenderVisitor(_client, queryid);
        try {
            engine.run(query.getQuery(), sender);
        } catch (Exception e) {
            SingleNodeServer.L.info("Exception:"+ org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            throw new TQueryError(e.getMessage());
        }
        //long usedMem = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024/1024;
        //SingleNodeServer.L.warn("Used Memory:"+usedMem+"MB");
    }

    @Override
    public TTuple getFirstTuple(QueryMessage query) throws TException {
        FirstEntryVisitor firstGetter = new FirstEntryVisitor();
        try {
            engine.run(query.getQuery(), firstGetter);
            Tuple tuple = firstGetter.firstTuple();
            TTuple ttuple = Bridge.translate(tuple);
            return ttuple;
        } catch (Exception e) {
            throw new TQueryError(e.getMessage());
        }
    }

    @Override
    public void clear(String table) throws TQueryError, TException {
        try {
            engine.clearTable(table);
        } catch (Exception e) {
            throw new TQueryError(e.getMessage());
        }
    }

    @Override
    public void drop(String table) throws TQueryError, TException {
        try {
            engine.dropTable(table);
        } catch (Exception e) {
            throw new TQueryError(e.getMessage());
        }
    }

    @Override
    public int getEnumId(String kind, String key) throws TQueryError, TException {
        return EnumId.get(kind, key);
    }

    @Override
    public String getEnumKey(String kind, int id) throws TQueryError, TException {
        return EnumId.get(kind, id);
    }

    @Override
    public List<String> getEnumKeyList(String kind) throws TQueryError, TException {
        return EnumId.get(kind);
    }

    @Override
    public void gc() throws TQueryError, TException {
        System.gc();
    }
}
