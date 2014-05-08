package socialite.codegen;

import socialite.engine.Config;
import socialite.resource.WorkerAddrMap;
import socialite.resource.SRuntime;

public class DistCodeGenMain extends CodeGenMain {
	public DistCodeGenMain(Config _conf, Analysis _an, SRuntime runtime) {
		super(_conf, _an, runtime);
	}
}
