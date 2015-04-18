package socialite.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.resource.SRuntime;
import socialite.resource.SRuntimeWorker;
import socialite.util.Assert;


public class MyId {
	public static final Log L=LogFactory.getLog(MyId.class);
	
	public static int invoke() {
		SRuntimeWorker runtime=SRuntimeWorker.getInst();
        if (runtime==null) return 0;
		int id = runtime.getWorkerAddrMap().myIndex();
		return id;
	}
	public static int get() {
		return invoke();
	}
}