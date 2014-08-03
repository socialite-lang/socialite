package socialite.resource;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.codegen.Analysis;
import socialite.parser.Const;
import socialite.parser.GeneratedT;
import socialite.parser.Predicate;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;
import socialite.visitors.IVisitor;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntObjectHashMap;

public class VisitorBuilder implements Serializable {
	public static final Log L=LogFactory.getLog(VisitorBuilder.class);
		
	SRuntime runtime;
	TIntObjectHashMap<Class> visitorMap;	
	TableSliceMap sliceMap;
	TableInstRegistry tableRegistry;
	Map<String, Table> tableMap;

	TIntObjectHashMap<RuleInfo> ruleInfoMap;
	boolean isParallel;

	public VisitorBuilder() {}
	public VisitorBuilder(SRuntime _rt, List<Rule> rules) {
		runtime = _rt;
		tableMap = runtime.getTableMap();
		sliceMap = runtime.getSliceMap();
		tableRegistry = runtime.getTableRegistry();
		visitorMap = new TIntObjectHashMap<Class>();
		isParallel = runtime.getConf().isParallel();
		
		//ruleInfos = new RuleInfo[8];
		ruleInfoMap = new TIntObjectHashMap<RuleInfo>(); 
		generateRuleInfo(rules);
	}
	public VisitorBuilder(List<VisitorBuilder> builders, List<Rule> rules) {
		VisitorBuilder builder = builders.get(0);
		runtime = builder.runtime;
		tableMap = builder.tableMap;
		sliceMap = builder.sliceMap;
		tableRegistry = builder.tableRegistry;
		visitorMap = new TIntObjectHashMap<Class>(builder.visitorMap);
		isParallel = builder.isParallel;
		
		ruleInfoMap = new TIntObjectHashMap<RuleInfo>(builder.ruleInfoMap);
		for (int i=1; i<builders.size(); i++) {
			ruleInfoMap.putAll(builders.get(i).ruleInfoMap);
		}
		generateRuleInfo(rules);
	}
	
	void generateRuleInfo(List<? extends Rule> rules) {
		int tmpTablePos = -1;
		for (Rule r:rules) {
			Predicate headP=r.getHead();
			Table headT = tableMap.get(headP.name());
			// getting body table ids
			TIntArrayList bodyTableIds = new TIntArrayList();
			for (Predicate p:r.getBodyP()) {
				Table t=tableMap.get(p.name());
				if (t instanceof GeneratedT) {
					bodyTableIds.add(t.id());
					tmpTablePos = bodyTableIds.size()-1;
				} else {
					bodyTableIds.add(t.id());
				}
			}			
			
			boolean startWithT=false;
			Object firstBody = r.getBody().get(0);
			if (firstBody instanceof Predicate)
				startWithT = true;

			int slicingColumn=findSlicingColumn(r);			
			RuleInfo info=new RuleInfo(r, headT, bodyTableIds, tmpTablePos, startWithT, slicingColumn, r.getConsts());
			ruleInfoMap.put(r.id(), info); 
		}
	}
	
	int OLD_findSlicingColumn(Rule r) {
		if (!isParallel) return 0;
		
		if (!Analysis.isParallelRule(r, tableMap)) return -1;
		
		Object first = r.getBody().get(0);
		assert first instanceof Predicate;
		Predicate firstP=(Predicate)first;
		Table firstT=tableMap.get(firstP.name());
		int column=Analysis.firstShardedColumnWithVar(firstP, firstT);
		if (column >= 0) {
			return column;
		} else {
			assert firstT.isModTable();
			return 0;
		}
	}
	int findSlicingColumn(Rule r) {
		if (!isParallel) return 0;
		
		if (!Analysis.isParallelRule(r, tableMap)) return -1;
		
		Predicate firstP=(Predicate)r.getBody().get(0);
		Object f = firstP.getAllInputParams()[0]; 
		Table firstT=tableMap.get(firstP.name());
		if (firstT.getColumn(0).isIndexed() && f instanceof Const) return -1;
		
		int column=Analysis.firstShardedColumnWithVar(firstP, firstT);
		if (column >= 0) {
			return column;
		} else {
			return 0;
		}
	}
	
	public void addRules(List<? extends Rule> rules) {
		generateRuleInfo(rules);		
	}

	int maxId(List<? extends Rule> rules) {
		int maxId = 0;
		for (Rule r : rules) {
			if (r.id() > maxId) maxId = r.id();
		}
		return maxId;
	}

	void makeRestArgs(Class[] argTypes, Object[] args, int restArgStartIdx) {
		int i=restArgStartIdx;			
		assert (argTypes[i].equals(SRuntime.class));
		args[i] = runtime;
		i++;
		args[i] = 0;
		assert i+1==args.length;
	}

	Object[] makeArgsForConstr(Constructor constr, int ruleId) {
		Class[] argTypes = constr.getParameterTypes();
		Object[] args = new Object[argTypes.length];

		RuleInfo info = ruleInfoMap.get(ruleId);
		TIntArrayList tableIds = info.bodyTableIds;
		assert argTypes[0].equals(int.class): "rule "+ruleId+" constructor has unexpected argument types";
		int offset=0;
		args[offset++] = ruleId;
		for (Const c:info.consts) {
			args[offset++] = c.val;
		}
		int i;
		for (i=0; i < tableIds.size(); i++) {
			int tableId = tableIds.get(i);
			TableInst[] tableArray = tableRegistry.getTableInstArray(tableId);
			assert (tableArray != null);
			if (tableArray.length == 1)
				args[offset+i] = tableArray[0];
			else args[offset+i] = tableArray;
		}
		int headT=info.headTableId();
		if (headT >= 0) {
			TableInst[] tableArray = tableRegistry.getTableInstArray(headT);
			if (tableArray.length==1) args[offset+i] = tableArray[0];
			else args[offset+i] = tableArray;
			i++;
		}		
		makeRestArgs(argTypes, args, offset+i);
		return args;
	}
	
	int tableId(TableInst[] tmpTable) {
		if (tmpTable==null) return -1;
		for (int i=0; i<tmpTable.length; i++) {
			if (tmpTable[i]!=null) {
				return tmpTable[i].id();
			}
		}
		assert false;
		return -1;
	}
	
	Object[] makeArgsForConstr(Constructor constr, int ruleId, TableInst tmpTable) {
		Class[] argTypes = constr.getParameterTypes();
		Object[] args = new Object[argTypes.length];

		RuleInfo info = ruleInfoMap.get(ruleId);
		TIntArrayList tableIds = info.bodyTableIds;
		int tmpTablePos = info.tmpTablePos;
		assert tmpTablePos>=0:"tmpTable:"+tmpTable;
		int offset=0;
		args[offset++] = ruleId;
		for (Const c:info.consts) {
			args[offset++] = c.val;
		}
		int i;
		for (i=0; i<tableIds.size(); i++) {
			if (i==tmpTablePos) {
				args[offset+i] = tmpTable;
			} else {
				int tableId = tableIds.get(i);					
				TableInst[] tableArray;
				tableArray = tableRegistry.getTableInstArray(tableId);
				if (tableArray.length==1) args[offset+i] = tableArray[0];
				else args[offset+i] = tableArray;
			}			
			assert(args[offset+i]!=null): "passing null argument to visitor constructor";
		}
		int headT=info.headTableId();
		if (headT >= 0) {
			TableInst[] tableArray = tableRegistry.getTableInstArray(headT);
			if (tableArray.length==1) args[offset+i] = tableArray[0];
			else args[offset+i] = tableArray;
			i++;
		}		
		makeRestArgs(argTypes, args, offset+i);
		return args;
	}
	
	Object[] makeArgsForConstr(Constructor constr, int ruleId, TableInst[] tmpTable) {
		// tmpTable is either DeltaTable or ThreadLocalTable
		
		Class[] argTypes = constr.getParameterTypes();
		Object[] args = new Object[argTypes.length];
		RuleInfo info = ruleInfoMap.get(ruleId);
		int tmpTablePos = info.tmpTablePos;
		assert tmpTablePos>=0: "rule "+info.r+ "has no tmp table slot";
		//TIntArrayList tableIds = ruleInfos[ruleId].bodyTableIds;
		TIntArrayList tableIds = info.bodyTableIds;
		int offset=0;
		args[offset++] = ruleId;
		for (Const c:info.consts) {
			args[offset++] = c.val;
		}
		int i;
		for (i=0; i < tableIds.size(); i++) {
			TableInst[] tableArray=null;
			if (i==tmpTablePos) {
				tableArray = tmpTable;				
			} else {
				int tableId = tableIds.get(i);						
				tableArray = tableRegistry.getTableInstArray(tableId);				
			}
			assert(tableArray!=null);
			
			if (tableArray.length == 1) args[offset+i] = tableArray[0];
			else args[offset+i] = tableArray;
		
			assert(args[offset+i]!=null): "passing null argument to visitor constructor";
		}		
		int headT=info.headTableId();
		if (headT >= 0) {
			TableInst[] tableArray = tableRegistry.getTableInstArray(headT);
			if (tableArray.length==1) args[offset+i] = tableArray[0];
			else args[offset+i] = tableArray;
			i++;
		}		
		makeRestArgs(argTypes, args, offset+i);
		return args;
	}

	public int firstTableId(int ruleId) {
		return ruleInfoMap.get(ruleId).firstTableId();
	}
	boolean startWithT(int ruleId) {
		return ruleInfoMap.get(ruleId).startWithT;
	}	
	
	public IVisitor[] getNewVisitorInst(int ruleId) {
		return getNewVisitorInst(ruleId, null);
	}
	
	public IVisitor[] getNewVisitorInst0(int ruleId, TableInst[] localT) {
		IVisitor[] visitors = getNewVisitorInst(ruleId, localT);
		for (IVisitor v:visitors) 
			if (v!=null) v.incPriority();
		return visitors;
	}

	int getVisitorNumFromT(TableInst[] t) {		
		// t can only be either DeltaTable or RemoteHeadTable
		assert t.length==1;
		assert t[0]!=null;
		return t[0].virtualSliceNum();
	}
	
	public IVisitor[] getNewVisitorInst(int ruleId, TableInst localT, int sliceIdx) {
		Class visitorClass = getVisitorClass(ruleId);
		if (visitorClass == null) {
			L.error("No visitor class for rule:"+ruleId);
			throw new SociaLiteException("No visitor class for rule:"+ruleId);
		}
		Constructor[] constr = visitorClass.getConstructors();
		assert(constr.length == 1);
		
		Object[] args;
		if (localT==null) args = makeArgsForConstr(constr[0], ruleId);
		else args = makeArgsForConstr(constr[0], ruleId, localT);
		
		IVisitor[] visitors = new IVisitor[1];
		try {
			args[args.length-1] = sliceIdx;
			visitors[0] = (IVisitor)constr[0].newInstance(args);
		} catch (Exception e) {
			/*System.out.println("Visitor class:"+visitorClass.getSimpleName());
			Class[] argTypes = constr[0].getParameterTypes();
			for (int i=0; i<argTypes.length; i++) {
				System.out.println("argTypes["+i+"]:"+argTypes[i].getSimpleName());
			}			
			for (int i=0; i<argTypes.length; i++) {
				System.out.println("args["+i+"]:"+args[i].getClass().getSimpleName()+", "+args[i]);
			}*/
			L.error("Error while creating new visitor instance:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException("Error creating visitor(rule:"+ruleInfoMap.get(ruleId)+")");
		}
		return visitors;
	}
	
	
	public IVisitor[] getNewVisitorInst(int ruleId, TableInst[] localT) {
		Class visitorClass = getVisitorClass(ruleId);
		if (visitorClass==null) throw new SociaLiteException("No visitor class for rule:"+ruleId);
		
		Constructor[] constr = visitorClass.getConstructors();
		assert(constr.length == 1);

		Object[] args;
		if (localT==null) args = makeArgsForConstr(constr[0], ruleId);
		else args = makeArgsForConstr(constr[0], ruleId, localT);
		
		int visitorNum, firstTableIdx;
		visitorNum = ruleInfoMap.get(ruleId).getVisitorNum(sliceMap);		
		if (visitorNum==0)
			visitorNum = getVisitorNumFromT(localT);
		assert visitorNum>0;
		firstTableIdx = ruleInfoMap.get(ruleId).getFirstTableIdx(sliceMap);
		
		IVisitor[] visitors = new IVisitor[visitorNum];
		try {
			if (visitors.length == 1) {
				args[args.length - 1] = firstTableIdx;
				visitors[0] = (IVisitor) constr[0].newInstance(args);
			} else {
				for (int j = 0; j < visitors.length; j++) {
					if (localT!=null && tableId(localT) == ruleInfoMap.get(ruleId).firstTableId() &&
							localT.length==visitors.length && localT[j]==null) {
						visitors[j]=null;
					} else {
						args[args.length - 1] = j;
						visitors[j] = (IVisitor) constr[0].newInstance(args);
					}
				}
			}
		} catch (Exception e) {		
			/*System.out.println("Visitor class:"+visitorClass.getSimpleName());
			Class[] argTypes = constr[0].getParameterTypes();
			for (int i=0; i<argTypes.length; i++) {
				System.out.println("argTypes["+i+"]:"+argTypes[i].getSimpleName());
			}			
			for (int i=0; i<argTypes.length; i++) {
				System.out.println("args["+i+"]:"+args[i].getClass().getSimpleName()+", "+args[i]);
			}
			*/
			L.error("Error while creating new visitor instance:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
			throw new SociaLiteException(
				"Error while creating visitor object for:"+ruleInfoMap.get(ruleId)+","+e);
		}	
		return visitors;
	}
	
	public IVisitor getNewPipelinedVisitorInst(int ruleId) {
		IVisitor[] visitors = getNewVisitorInst(ruleId, null);
		assert visitors.length==1;
		return visitors[0];
	}
	
	public void setVisitorClass(int ruleId, Class visitor) {
		visitorMap.put(ruleId, visitor);
	}

	public Class getVisitorClass(int ruleId) {		
		Class visitorClass = visitorMap.get(ruleId);
		assert visitorClass!=null: "Visitor class is not generated yet for the rule-id:"+ruleId;//ruleInfoMap.get(ruleId);
		return visitorClass;
	}
}

class RuleInfo {
	final Rule r;
	final Table headT;
	final TIntArrayList bodyTableIds;
	final boolean startWithT;
	final int slicingColumn;
	final int tmpTablePos;
	final List<Const> consts;
	
	// RuleInfo(r, headT, bodyTableIds, startWithT, slicingColumn);
	RuleInfo(Rule _r, Table _headT, TIntArrayList _bodyTableIds, int _tmpTablePos, boolean _startWithT, int _slicingColumn, List<Const> _consts) {
		r = _r;
		headT = _headT;
		bodyTableIds = _bodyTableIds;
		startWithT = _startWithT;
		slicingColumn = _slicingColumn;
		consts = _consts;
		tmpTablePos = _tmpTablePos;		
	}
	
	public int accessedTableCount() {
		int count=bodyTableIds.size();
		if (headTableId() >= 0)
			count++;
		return count;
	}
	public int headTableId() {
		if (headT instanceof GeneratedT) return -1;
		return headT.id();
	}
	public int firstTableId() {
		if (bodyTableIds.size()>0) return bodyTableIds.get(0);
		else return -1;
	}
		
	public int getFirstTableIdx(TableSliceMap sliceMap) {
		if (!startWithT) return 0;
		assert bodyTableIds.size()>0;
		int id = bodyTableIds.get(0);
		
		Predicate firstP = r.firstP();
				
		if (slicingColumn==-1) {
			Object param = firstP.getAllInputParams()[0];
			if (param instanceof Variable) 
				return 0; // this table has an order-by option
			
			assert param instanceof Const;
			Const c=(Const)param;
			int idx=sliceMap.getIndex(id, c.val);
			assert idx>=0;
			return idx;
		}
		if (sliceMap.virtualSliceNum(id, slicingColumn)==1) 
			return 0;		
		
		Object param = firstP.getAllInputParams()[slicingColumn];
		if (param instanceof Variable) {
			return -1;
		} else if (param instanceof Const) {
			Const c=(Const)param;
			int idx=sliceMap.getIndex(id, slicingColumn, c.val);
			if (idx>=0) return idx;
			return -1;
		} else {
			assert false:"Impossible parameter type:"+param.getClass().getSimpleName();
			return -1;
		}			
	}
	
	public int getVisitorNum(TableSliceMap sliceMap)  {
		if (!startWithT) return 1;
		assert bodyTableIds.size()>0;
		int id = bodyTableIds.get(0);

		if (slicingColumn==-1) return 1;
		int sliceNum = sliceMap.virtualSliceNum(id, slicingColumn);
		if (sliceNum==1) return 1;
		
		Predicate firstP = r.firstP();
		Object param = firstP.getAllInputParams()[slicingColumn];
		if (param instanceof Const) {
			Const c=(Const)param;
			int idx=sliceMap.getIndex(id, slicingColumn, c.val);
			if (idx>=0) return 1;
			else return sliceNum;			
		} else {
			assert param instanceof Variable;
			return sliceNum;
		}
	}
	
	public String toString() {
		return r.toString();
	}
}
