package socialite.tables;

import java.util.List;

public interface QueryRunnable extends Runnable {
	public void setArgs(List args);
	public void setQueryVisitor(QueryVisitor qv);
	
	public void run();
	public void setTerminated();
}
