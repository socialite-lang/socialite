package socialite.dist;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Host {
	static final Log L=LogFactory.getLog(Host.class);
	
	static String hostAddr=null;
	// work-around for the bug in InetAddress.getLocalHost() returning 127.0.0.1
	public static String get() {
		if (hostAddr!=null) return hostAddr;

		hostAddr = System.getProperty("socialite.hostname");
		if (hostAddr!=null) return hostAddr;

		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while(interfaces.hasMoreElements()) {
				NetworkInterface inet=interfaces.nextElement();
				if (!inet.isUp() || inet.isLoopback() || inet.isVirtual())
					continue;
				Enumeration<InetAddress> addrs=inet.getInetAddresses();
				while(addrs.hasMoreElements()) {
					InetAddress addr=addrs.nextElement();
					if (addr instanceof Inet4Address) {
						hostAddr= addr.getHostAddress();
						return hostAddr;
					}						
				}
			}
		} catch (SocketException e) {
			L.fatal("Error while getting local host address:"+e);
		}
		L.fatal("Cannot determine host address");
		return null;
	}	
	
	public static boolean equalsMyAddr(InetAddress addr) {
		return addr.getHostAddress().equals(get());
	}
}
