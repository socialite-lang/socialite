package socialite.eval;

import java.util.ArrayList;
import java.util.List;

public class ManagerInputQueue {
	List<Command> commands;
	boolean wait;	
	ManagerInputQueue() {
		commands = new ArrayList<Command>();
		wait=false;
	}

	synchronized Command getCommand() throws InterruptedException {
		if (commands.isEmpty()) {
			wait=true;
			wait();
		}
		return commands.remove(0);
	}

	synchronized boolean isEmpty() {
		if (commands.isEmpty()) return true;
		return false;
	}
	
	synchronized void addCommand(Command cmd) {
		commands.add(cmd);
		if (wait) {
			wait=false;
			notify();
		}
	}
}