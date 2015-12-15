package socialite.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserLog {
	public static final Log L=LogFactory.getLog(UserLog.class);
	// user level logs
	public static void debug(Object msg) { L.debug(msg); }
	public static void info(Object msg) { L.info(msg); }
	public static void warn(Object msg) { L.warn(msg); }
	public static void error(Object msg) { L.error(msg); }
}