package socialite.rpc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import socialite.rpc.queryCallback.QueryCallbackService;
import socialite.rpc.queryCallback.TTupleList;
import socialite.tables.*;
import socialite.util.FastOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SenderVisitor extends QueryVisitor {
    public static final Log L= LogFactory.getLog(SenderVisitor.class);

    final int numRowsThreshold = 512;
    final String remoteAddr;
    final int remotePort;
    List<TTuple> tupleList = new ArrayList<>(numRowsThreshold);
    long queryid;

    TTransport transport;
    QueryCallbackService.Client client;

    public SenderVisitor(String addr, int port, long qid) {
        remoteAddr = addr;
        remotePort = port;
        queryid = qid;
        try {
            transport = new TSocket(remoteAddr, remotePort);
            transport.open();
            TProtocol protocol = new TCompactProtocol(transport);
            client = new QueryCallbackService.Client(protocol);
        } catch (TException x) {
            L.error("Cannot connect to client:"+x);
        }
    }

    void returnTuples() {
        returnTuples(false);
    }
    void returnTuples(boolean finish) {
        try {
            TTupleList ttupleList = new TTupleList(tupleList, finish);
            client.returnTuples(queryid, ttupleList);
            tupleList.clear();
        } catch (TException e) {
            L.error("Cannot send data to client:"+e);
        }
    }
    public void finish() {
        returnTuples(true);
    }
    public boolean visit(int i) {
        tupleList.add(Bridge.translate(new Tuple_int(i)));
        if (tupleList.size() == numRowsThreshold) {
            returnTuples();
        }
        return true;
    }

    public boolean visit(long l) {
        tupleList.add(Bridge.translate(new Tuple_long(l)));
        if (tupleList.size() == numRowsThreshold) {
            returnTuples();
        }
        return true;
    }

    public boolean visit(float f) {
        tupleList.add(Bridge.translate(new Tuple_float(f)));
        if (tupleList.size() == numRowsThreshold) {
            returnTuples();
        }
        return true;
    }

    public boolean visit(double d) {
        tupleList.add(Bridge.translate(new Tuple_double(d)));
        if (tupleList.size() == numRowsThreshold) {
            returnTuples();
        }
        return true;
    }

    public boolean visit(Object o) {
        tupleList.add(Bridge.translate(new Tuple_Object(o)));
        if (tupleList.size() == numRowsThreshold) {
            returnTuples();
        }
        return true;
    }

     public boolean visit(Tuple t) {
        tupleList.add(Bridge.translate(t));
        if (tupleList.size() == numRowsThreshold) {
            returnTuples();
        }
        return true;
    }

}
