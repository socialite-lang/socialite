package socialite.util;

import java.io.PrintStream;

public class SociaLiteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SociaLiteException(Throwable t) { super(t.getMessage());	}
	public SociaLiteException(Exception e) { super(e.getMessage()); }
	public SociaLiteException(String _msg) { super(_msg); }
	public SociaLiteException() {}
	
	@Override
	public void printStackTrace(PrintStream s) {
		synchronized(s) {
			super.printStackTrace(s);
		}
	}
	@Override
	public String toString() {
		String s = getClass().getName();
		String message = getLocalizedMessage();
		return (message != null) ? (message) : s;
    }
}
