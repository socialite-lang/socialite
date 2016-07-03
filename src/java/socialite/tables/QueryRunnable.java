package socialite.tables;

import java.util.List;

public interface QueryRunnable extends Runnable {
    void setArgs(List args);
    void setQueryVisitor(QueryVisitor qv);
	
    void run();
    void kill();
}
