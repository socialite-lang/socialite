package socialite.eval;

public interface Task {
	public boolean safeToSteal();
	public void setPriority(int priority);
	public int getPriority();
	public void run(Worker w);	
	public int getRuleId();
}