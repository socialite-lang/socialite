package socialite.eval;

import socialite.codegen.Epoch;
import socialite.engine.Config;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.resource.TableSliceMap;
import socialite.resource.VisitorBuilder;

public abstract class Eval implements Runnable {
	public SRuntime runtime;
	public Epoch epoch;
	public RuleMap ruleMap;
	public TableSliceMap sliceMap;
	public TableInstRegistry tableRegistry;
	public Config conf;	
	
	public void init() {}	
	public void finish() {}	
}
