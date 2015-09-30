package socialite.resource;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.hadoop.net.NetUtils;
import socialite.dist.Host;
import socialite.dist.PortMap;
import socialite.parser.Table;
import socialite.util.Assert;

public class WorkerAddrMap implements Serializable {
    static final Log L=LogFactory.getLog(WorkerAddrMap.class);

    int lastUnusedIndex;
    ArrayList<InetSocketAddress> workerAddrs;
    ArrayList<InetSocketAddress> workerDataAddrs;
    transient int myIdx=-1;

    public WorkerAddrMap() {
        lastUnusedIndex=0;
        workerAddrs = new ArrayList<InetSocketAddress>();
        workerDataAddrs = new ArrayList<InetSocketAddress>();
        myIdx=-1;
    }

    public void add(InetSocketAddress addr, InetSocketAddress dataAddr) {
        workerAddrs.add(addr);
        workerDataAddrs.add(dataAddr);
    }

    public InetSocketAddress getDataAddr(int workerIdx) {
        return workerDataAddrs.get(workerIdx);
    }
    public InetSocketAddress get(int workerIdx) {
        return workerAddrs.get(workerIdx);
    }

    public int size() { return workerAddrs.size(); }

    public void initMyIndex() {
        assert myIdx == -1;
        String host = NetUtils.getHostname().split("/")[1];
        int port = PortMap.worker().getPort("workerCmd");
        InetSocketAddress myaddr = new InetSocketAddress(host, port);
        myIdx = 0;
        for (InetSocketAddress addr:workerAddrs) {
            if (myaddr.equals(addr)) { return; }
            myIdx++;
        }
        throw new AssertionError("Cannot find worker index (host="+host+", port="+port+")");
    }
    public int myIndex() {
        assert myIdx >= 0;

        return myIdx;
    }

    public String toString() {
        String str="";
        for (InetSocketAddress addr:workerAddrs) {
            str += addr + ", ";
        }
        return str;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        myIdx=-1;
    }
}
