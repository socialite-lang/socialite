package socialite.dist;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.net.NetUtils;
import socialite.yarn.SocialiteAppMasterClient;


public abstract class PortMap {
    public static final Log L = LogFactory.getLog(PortMap.class);

	static MasterPortMap masterMap = null;
	static WorkerPortMap workerMap = null;
	static ClientPortMap clientMap = null;
	public static PortMap master() {
		if (masterMap == null) { masterMap = new MasterPortMap(); }
		return masterMap;
	}
	public static PortMap worker() {
		if (workerMap == null) { workerMap = new WorkerPortMap(); }
		return workerMap;
	}
	public static PortMap client() {
		if (clientMap == null) { clientMap = new ClientPortMap(); }
		return clientMap;
	}

    static ServerSocket[] reserveServerSocket(int portFrom, int count) {
        ServerSocket[] socks = new ServerSocket[count];
		int portidx = 0;
        for (int p = portFrom; p < 65536; p++) {
            try {
                ServerSocket s = new ServerSocket(p);
                socks[portidx++] = s;
            } catch (IOException e) {
                L.error("Cannot reserve ports:"+e);
            }
            if (portidx == socks.length) { return socks; }
		}
		throw new RuntimeException("Cannot find enough free ports");
    }

    static int DEFAULT_BASE_PORT=50100;
    String master="localhost";
    HashMap<String, ServerSocket> reservedSocketMap = new HashMap<String, ServerSocket>();

	public String masterAddr() { return master; }

    public int getPort(String proto) {
        assert reservedSocketMap != null;
        return reservedSocketMap.get(proto).getLocalPort();
    }
    public int usePort(String proto) {
        ServerSocket sock = reservedSocketMap.get(proto);
        int port = sock.getLocalPort();
        try { sock.close(); }
        catch (IOException e) {
            throw new AssertionError("Impossible:"+e);
        }
        return port;
    }
    public String toString() {
        String str = "{";
        for (String proto: reservedSocketMap.keySet()) {
            str += "  " + proto + ":" + reservedSocketMap.get(proto) + "\n";
        }
        str += "}";
        return str;
    }
}
class MasterPortMap extends PortMap {
	MasterPortMap() {
		master = NetUtils.getHostname().split("/")[1];
        int basePort=DEFAULT_BASE_PORT;
        String _basePort = System.getProperty("socialite.port");
		if (_basePort != null) basePort = Integer.parseInt(_basePort);

        ServerSocket[] reservedServerSockets = reserveServerSocket(basePort, 3);

        reservedSocketMap.put("query", reservedServerSockets[0]);
        reservedSocketMap.put("workerReq", reservedServerSockets[1]);
        reservedSocketMap.put("tupleReq", reservedServerSockets[2]);
	}
}
abstract class RemoteMasterPortMap extends PortMap {
    HashMap<String, Integer> masterPortMap = new HashMap<String, Integer>();
    @Override
    public int getPort(String proto) {
        if (masterPortMap.containsKey(proto)) {
            return masterPortMap.get(proto);
        } else {
            return super.getPort(proto);
        }
    }
    @Override
    public int usePort(String proto) {
        if (masterPortMap.containsKey(proto)) {
            throw new AssertionError("Cannot use master's ports:"+proto);
        }
        return super.usePort(proto);
    }
    public String toString() {
        String str = "{";
        for (String proto: masterPortMap.keySet()) {
            str += "  master->" + proto + ":" + masterPortMap.get(proto) + "\n";
        }
        for (String proto: reservedSocketMap.keySet()) {
            str += "  " + proto + ":" + reservedSocketMap.get(proto) + "\n";
        }
        str += "}";
        return str;
    }
}
class WorkerPortMap extends RemoteMasterPortMap {
	WorkerPortMap() {
		SocialiteAppMasterClient cli = SocialiteAppMasterClient.get();
		master = cli.getHost();
        for (String proto: new String[] {"query", "workerReq", "tupleReq"}) {
            masterPortMap.put(proto, cli.getPort(proto));
        }

        int basePort = DEFAULT_BASE_PORT + new Random().nextInt(97);
        ServerSocket[] reservedServerSockets = reserveServerSocket(basePort, 2);
        reservedSocketMap.put("workerCmd", reservedServerSockets[0]);
        reservedSocketMap.put("data", reservedServerSockets[1]);
	}
}
class ClientPortMap extends RemoteMasterPortMap {
	ClientPortMap() {
        SocialiteAppMasterClient cli = SocialiteAppMasterClient.get();
		master = cli.getHost();
        for (String proto: new String[] {"query", "workerReq"}) {
            masterPortMap.put(proto, cli.getPort(proto));
        }

        int basePort = DEFAULT_BASE_PORT + new Random().nextInt(97);
        ServerSocket[] reservedServerSockets = reserveServerSocket(basePort, 1);
        reservedSocketMap.put("tupleReq", reservedServerSockets[0]);
    }
}
