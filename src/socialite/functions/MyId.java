package socialite.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.SRuntime;
import socialite.util.Assert;


public class MyId {
	public static final Log L=LogFactory.getLog(MyId.class);
	
	public static int invoke() {
		SRuntime runtime=SRuntime.workerRt();
		int id = runtime.getWorkerAddrMap().myIndex();
		return id;
	}
	public static int get() {
		return invoke();
	}
}