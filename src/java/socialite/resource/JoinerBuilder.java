package socialite.resource;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.parser.*;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;
import gnu.trove.map.hash.TIntObjectHashMap;
import socialite.tables.Joiner;

public class JoinerBuilder {
    public static final Log L=LogFactory.getLog(JoinerBuilder.class);

    SRuntime runtime;
    TIntObjectHashMap<Class> joinerMap;
    TablePartitionMap partitionMap;
    TableInstRegistry tableRegistry;
    Map<String, Table> tableMap;

    TIntObjectHashMap<RuleInfo> ruleInfoMap;

    public JoinerBuilder(SRuntime _rt, List<Rule> rules) {
        runtime = _rt;
        tableMap = runtime.getTableMap();
        partitionMap = runtime.getPartitionMap();
        tableRegistry = runtime.getTableRegistry();
        joinerMap = new TIntObjectHashMap<>();

        ruleInfoMap = new TIntObjectHashMap<>();
        generateRuleInfo(rules);
    }

    void generateRuleInfo(List<? extends Rule> rules) {
        for (Rule r:rules) {
            Table ruleTable = r.getPartitionTable();
            Literal l = r.getBody().get(0);
            Table firstT=null;
            if (l instanceof Predicate) {
                Predicate firstP = (Predicate) l;
                firstT = tableMap.get(firstP.name());
            }
            RuleInfo info=new RuleInfo(r, ruleTable, firstT, r.getConsts());
            ruleInfoMap.put(r.id(), info);
        }
    }

    Object[] makeArgsForConstr(Constructor constr, int ruleId) {
        Class[] argTypes = constr.getParameterTypes();
        Object[] args = new Object[argTypes.length];
        RuleInfo info = ruleInfoMap.get(ruleId);
        int offset=0;
        args[offset++] = info.getEpochId();
        args[offset++] = ruleId;
        for (Const c:info.consts) {
            args[offset++] = c.val;
        }
        args[offset++] = runtime;
        args[offset] = 0; // first table partition idx. Can be overriden
        return args;
    }

    Object[] makeArgsForConstr(Constructor constr, int ruleId, TableInst[] tmpTable) {
        // tmpTable can be DeltaTable or RemoteTable, etc (of GeneratedT type).

        Class[] argTypes = constr.getParameterTypes();
        Object[] args = new Object[argTypes.length];
        RuleInfo info = ruleInfoMap.get(ruleId);
        int offset=0;
        args[offset++] = info.getEpochId();
        args[offset++] = ruleId;
        for (Const c:info.consts) {
            args[offset++] = c.val;
        }
        args[offset++] = tmpTable[0];
        args[offset++] = runtime;
        args[offset] = 0; // default value for first table partition idx.
        return args;
    }

    public Joiner[] getNewJoinerInst(int ruleId) {
        return getNewJoinerInst(ruleId, null);
    }

    public Joiner[] getNewJoinerInst(int ruleId, TableInst[] tmpTable) {
        Class joinerClass = getJoinerClass(ruleId);
        if (joinerClass==null) {
            throw new SociaLiteException("No joiner class for rule:" + ruleId);
        }

        Constructor[] constr = joinerClass.getConstructors();
        assert(constr.length == 1);

        Object[] args;
        if (tmpTable==null) {
            args = makeArgsForConstr(constr[0], ruleId);
        } else {
            args = makeArgsForConstr(constr[0], ruleId, tmpTable);
        }

        int joinerNum, ruleTablePartitionIdx;
        joinerNum = ruleInfoMap.get(ruleId).getJoinerNum(partitionMap);
        assert joinerNum>0;
        ruleTablePartitionIdx = ruleInfoMap.get(ruleId).getRuleTablePartitionIdx(partitionMap);

        Joiner[] joiners = new Joiner[joinerNum];
        try {
            if (joiners.length == 1) {
                args[args.length - 1] = ruleTablePartitionIdx;
                joiners[0] = (Joiner)constr[0].newInstance(args);
            } else {
                for (int j = 0; j < joiners.length; j++) {
                    args[args.length - 1] = j;
                    joiners[j] = (Joiner)constr[0].newInstance(args);
                }
            }
        } catch (Exception e) {
            /*
            if (tmpTable==null) { System.err.println("  tmpTable is null.");}
			else { System.err.println("  tmpTable: "+tmpTable[0].getClass());}
			Class[] argTypes = constr[0].getParameterTypes();
			for (int i=0; i<argTypes.length; i++) {
				System.err.println("argTypes["+i+"]:"+argTypes[i].getSimpleName());
			}			
			for (int i=0; i<argTypes.length; i++) {
				System.err.println("args["+i+"]:"+args[i].getClass().getSimpleName()+", "+args[i]);
			}
			*/
            L.error("Error while creating new joiner instance:"+e);
            L.error(ExceptionUtils.getStackTrace(e));
            throw new SociaLiteException(
                    "Error while creating joiner object for:"+ruleInfoMap.get(ruleId)+","+e);
        }
        return joiners;
    }

    public Joiner getNewPipelinedVisitorInst(int ruleId) {
        Joiner[] joiners = getNewJoinerInst(ruleId, null);
        assert joiners.length==1;
        return joiners[0];
    }

    public void setJoinerClass(int ruleId, Class visitor) {
        joinerMap.put(ruleId, visitor);
    }

    public Class getJoinerClass(int ruleId) {
        Class joinerClass = joinerMap.get(ruleId);
        assert joinerClass!=null: "Cannot find joiner class for rule-id:"+ruleId;//ruleInfoMap.get(ruleId);
        return joinerClass;
    }
}

class RuleInfo {
    final Rule r;
    final Table shardTable, firstTable;
    final List<Const> consts;

    RuleInfo(Rule _r, Table _shardT, Table _firstT, List<Const> _consts) {
        r = _r;
        shardTable = _shardT;
        firstTable = _firstT;
        consts = _consts;
    }
    public int getEpochId() {
        return r.getEpochId();
    }

    public int getRuleTablePartitionIdx(TablePartitionMap partitionMap) {
        if (shardTable == null) {
            return -1;
        }
        int id = shardTable.id();

        Predicate firstP = (Predicate)r.getBody().get(0);
        if (firstP.first() instanceof Const) {
            Const c = (Const)firstP.first();
            return partitionMap.getIndex(id, c.val);
        } else {
            assert firstP.first() instanceof Variable;
            return -1;
        }
    }

    public int getJoinerNum(TablePartitionMap partitionMap)  {
        if (shardTable == null) { return 1; }
        if (shardTable instanceof GeneratedT) {
            // Generated tables have a single partition
            return 1;
        }

        int col = shardTable.getPartitionColumn();
        Predicate p = r.getPartitionPredicate();
        if (p.paramAt(col) instanceof Const) {
            return 1;
        }

        int num = partitionMap.partitionNum(shardTable.id());
        return num;
    }

    public String toString() {
        return r.toString();
    }
}
