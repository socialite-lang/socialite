package socialite.dist.worker;

import java.net.InetAddress;
import java.nio.channels.SocketChannel;

public class RecvTask {
	public InetAddress nodeAddr;
	public SocketChannel selectedChannel;	
	public RecvTask(InetAddress _nodeAddr, SocketChannel _selectedChannel) {
		nodeAddr=_nodeAddr;
		selectedChannel=_selectedChannel;
	}
}
