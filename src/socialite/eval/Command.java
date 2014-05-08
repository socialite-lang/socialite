package socialite.eval;

import java.io.Externalizable;

public interface Command extends Externalizable { 
	public static final long serialVersionUID = Command.class.hashCode();
	public void setReceived();
	public boolean isReceived();
}