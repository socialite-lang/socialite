package socialite.eval;

import socialite.codegen.Epoch;
import socialite.resource.RuleMap;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.resource.TablePartitionMap;

public abstract class Eval implements Runnable {
    public SRuntime runtime;
    public Epoch epoch;
    public RuleMap ruleMap;
    public TablePartitionMap partitionMap;
    public TableInstRegistry tableRegistry;

    public void init() { }
    public void run() { }
    public void finish() { }
}
