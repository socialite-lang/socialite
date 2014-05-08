package socialite.dist;

public class PortMap {
	static PortMap theInst=null;
	public static PortMap get() {
		if (theInst==null) 
			theInst = new PortMap();
		return theInst;
	}
	
	static int DEFAULT_PORT=50100;
	int basePort=DEFAULT_PORT;
	String master="localhost";
	
	public PortMap() {
		String _basePort=System.getProperty("socialite.port");
		if (_basePort!=null) basePort=Integer.parseInt(_basePort);
		String _master=System.getProperty("socialite.master");
		if (_master!=null) master=_master;
	}
	public PortMap(String machine, int port) {
		master = machine;
		basePort = port;
	}
	
	public String masterAddr() { return master; }
	
	public int queryListen() { return basePort; }
	public int workerReqListen() { return basePort+1; }
	public int masterListen() { return basePort+2; }
	public int workerCmdListen() { return basePort+3; }
	public int workerToWorkerListen() { return basePort+4; }
	public int tupleReqListen() { return basePort+5; }	
	public int tupleReqClientListen() { return basePort+9; }
}