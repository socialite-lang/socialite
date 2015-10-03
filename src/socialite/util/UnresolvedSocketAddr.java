package socialite.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.InetSocketAddress;

/**
 * Similar to InetSocketAddress, intended to be used as keys of maps.
 */
public class UnresolvedSocketAddr implements Externalizable {
    private static int checkPort(int port) {
        if (port < 0 || port > 0xFFFF)
            throw new IllegalArgumentException("port out of range:" + port);
        return port;
    }

    private static String checkHost(String hostname) {
        if (hostname == null)
            throw new IllegalArgumentException("hostname can't be null");
        return hostname;
    }

    String host;
    int port;
    transient InetSocketAddress sockaddr;
    public UnresolvedSocketAddr() {}
    public UnresolvedSocketAddr(String _host, int _port) {
        host = checkHost(_host);
        port = checkPort(_port);
    }

    public String getHostName() {
        return host;
    }
    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UnresolvedSocketAddr)) { return false; }
        UnresolvedSocketAddr addr = (UnresolvedSocketAddr)o;
        return host.equals(addr.host) && port == addr.port;
    }

    @Override
    public int hashCode() {
        return host.hashCode() ^ port;
    }
    @Override
    public String toString() {
        return host + ":" + port;
    }

    public InetSocketAddress getInetSocketAddr() {
        if (sockaddr == null) {
            sockaddr = new InetSocketAddress(host, port);
        }
        return sockaddr;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(host.length());
        out.writeChars(host);
        out.writeInt(port);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        char ch[] = new char[len];
        for (int i=0; i < len ; i++) {
            ch[i] = in.readChar();
        }
        host = new String(ch);
        port = in.readInt();
    }
}

