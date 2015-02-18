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

import socialite.dist.Host;
import socialite.parser.Table;
import socialite.util.Assert;

public class WorkerAddrMap implements Serializable {
	static final Log L=LogFactory.getLog(WorkerAddrMap.class);
	
	int lastUnusedIndex;
	InetAddress[] machineAddrs;
	transient int myIdx=-1;
	
	public WorkerAddrMap() {
		lastUnusedIndex=0;
		machineAddrs = new InetAddress[64];
        myIdx=-1;
	}
	
	void ensureCapacity(int minCapacity) {
		int oldCapacity=machineAddrs.length;
		if (minCapacity > oldCapacity) {
			int newCapacity=(oldCapacity*3)/2+1;
            newCapacity = Math.max(newCapacity, minCapacity);
			machineAddrs=Arrays.copyOf(machineAddrs, newCapacity);
		}
	}
	public void add(InetAddress machineAddr) {		
		if (lastUnusedIndex>=machineAddrs.length)
			ensureCapacity(lastUnusedIndex+1);
		machineAddrs[lastUnusedIndex]=machineAddr;
		
		//L.info("Adding "+machineAddr+" at index:"+lastUnusedIndex);
		lastUnusedIndex++;
	}
	
	public InetAddress get(int machineIndex) {
		assert machineIndex < lastUnusedIndex;
		return machineAddrs[machineIndex];
	}
	
	public int size() { return lastUnusedIndex; }
	
	public int myIndex() {
		if (myIdx==-1) {
			String myAddrStr=Host.get();
			InetAddress addr=null;
			try { addr=InetAddress.getByName(myAddrStr);
			} catch (UnknownHostException e) {
				L.fatal("Unknown Host:"+e);
				L.fatal(ExceptionUtils.getStackTrace(e));
			}
			for (int i=0; i<lastUnusedIndex; i++) {
				if (addr.equals(machineAddrs[i])) {
					myIdx=i;
					break;
				}
			}
			if (SRuntimeMaster.getInst()==null)
				assert(myIdx!=-1); // if it's worker node, it must have a node index
		}
		return myIdx;
	}
	
	public List<InetSocketAddress> getSockAddrs(int port) {
		ArrayList<InetSocketAddress> list=new ArrayList<InetSocketAddress>(lastUnusedIndex);
		for (int i=0; i<lastUnusedIndex; i++) {
			list.add(new InetSocketAddress(machineAddrs[i], port));
		}
		return list;
	}
	public List<InetAddress> getAddrs() {
		ArrayList<InetAddress> list=new ArrayList<InetAddress>(lastUnusedIndex);
		for (int i=0; i<lastUnusedIndex; i++) {
			list.add(machineAddrs[i]);
		}
		return list;
	}
	
	public String toString() {
		String str="";
		for (int i=0; i<lastUnusedIndex; i++) {
			str += i+":"+machineAddrs[i].toString()+ ",";
		}
		return str;
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        myIdx=-1;
	}
}
