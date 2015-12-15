package socialite.standalone;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
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
import socialite.rpc.standalone.StandaloneService;
import socialite.tables.Tuple;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

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
        catch (TTransportException e) {
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
    HashMap<String, SenderVisitor> senderMap = new HashMap<>();
    final ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream(128);

    QueryHandler(LocalEngine _engine) {
        engine = _engine;
    }
    @Override
    public void runSimple(QueryMessage query) throws TException {
        engine.run(query.getQuery());
    }

    @Override
    public void run(QueryMessage query, String addr, int port, long queryid) throws TException {
        String addrPort = addr + ":" + port;
        SenderVisitor sender;
        if (senderMap.containsKey(addrPort)) {
            sender = senderMap.get(addrPort);
        } else {
            sender = new SenderVisitor(addr, port, queryid);
            senderMap.put(addrPort, sender);
        }
        engine.run(query.getQuery(), sender);
    }


    @Override
    public TTuple getFirstTuple(QueryMessage query) throws TException {
        FirstEntryVisitor firstGetter = new FirstEntryVisitor();
        engine.run(query.getQuery(), firstGetter);
        Tuple tuple = firstGetter.firstTuple();
        TTuple ttuple = Bridge.translate(tuple);
        return ttuple;
    }

}
