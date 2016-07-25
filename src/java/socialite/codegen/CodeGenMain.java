package socialite.codegen;

import gnu.trove.list.array.TIntArrayList;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.master.MasterNode;
import socialite.eval.Eval;
import socialite.eval.EvalDist;
import socialite.eval.EvalParallel;
import socialite.parser.Const;
import socialite.parser.GeneratedT;
import socialite.parser.Predicate;
import socialite.parser.Query;
import socialite.parser.Rule;
import socialite.parser.Table;
import socialite.parser.Variable;
import socialite.resource.SRuntime;
import socialite.resource.TableInstRegistry;
import socialite.tables.QueryRunnable;
import socialite.tables.QueryVisitor;
import socialite.util.InternalException;
import socialite.util.Loader;
import socialite.util.SociaLiteException;


public class CodeGenMain {
    static final Log L=LogFactory.getLog(CodeGenMain.class);

    static CodeClassCache joinerClass$ = new CodeClassCache();
    static QueryCache<Class> queryClass$ = new QueryCache<>();
    public static void clearCache() {
        joinerClass$.clear();
        queryClass$.clear();
    }
    public static void maybeClearCache() {
        joinerClass$.clearIfLarge();
        queryClass$.clearIfLarge();
    }
    static final LinkedHashMap<String,byte[]> EMPTY_MAP = new LinkedHashMap<String,byte[]>(0);

    List<Epoch> epochs;
    List<Table> newTables;
    Query query;
    Analysis an;

    List<Eval> evalInsts=new ArrayList<Eval>();

    LinkedHashMap<String, byte[]> generatedClasses = new LinkedHashMap<String, byte[]>();

    SRuntime runtime;
    Map<String, Table> tableMap;

    Class<? extends Runnable> queryClass;
    QueryVisitor queryVisitor;

    protected CodeGenMain() {}
    public CodeGenMain(Analysis _an, SRuntime rt) {
        CodeGenMain.maybeClearCache();

        runtime = rt;
        an = _an;
        evalInsts = new ArrayList<Eval>();
        epochs = an.getEpochs();
        newTables = an.getNewTables();

        query = an.getQuery();

        runtime.updateTableMap(an.getTableMap());
        tableMap = runtime.getTableMap();
    }

    public Set<Table> getReads() {
        return an.getReads();
    }
    public Set<Table> getWrites() {
        return an.getWrites();
    }
    public SRuntime getRuntime() { return runtime; }

    public List<Rule> getRules() { return an.getRules(); }

    Eval getEvalInstance(Epoch e) {
        return runtime.getEvalInst(e);
    }

    void addToGeneratedClasses(LinkedHashMap<String, byte[]> classes) {
        generatedClasses.putAll(classes);
    }
    public void generate() {
        assert evalInsts.size()==0;

        generateVisitorBase();

        addToGeneratedClasses(TupleCodeGen.generate(an.getRules(), tableMap));
        try {
            addToGeneratedClasses(TableCodeGen.ensureExist(newTables));
        } catch (InternalException e) { throw new SociaLiteException(e); }

        for (Epoch e:epochs) {
            generateVisitors(e);
            generateAndSetEval(e);
        }
        prepareRuntime();
    }
    void prepareRuntime() {
        for (Epoch e:epochs) {
            runtime.update(e);
        }
    }

    public LinkedHashMap<String, byte[]> getGeneratedClasses() { return generatedClasses; }

    public List<Epoch> getEpoch() { return epochs; }

    public List<Eval> getEvalInsts() {
        if (evalInsts.size()>0) return evalInsts;

        for (int i=0; i<epochs.size(); i++) {
            Epoch epoch=epochs.get(i);
            Eval eval=getEvalInstance(epoch);

            assert eval!=null:"eval is null";
            evalInsts.add(eval);
        }
        return evalInsts;
    }

    void generateAndSetEval(Epoch e) {
        List<Table> newTablesToAlloc = new ArrayList<Table>();

        for (Table t:e.getNewTables()) {
            if (!(t instanceof GeneratedT))
                newTablesToAlloc.add(t);
        }

        if (newTablesToAlloc.isEmpty()) {
            if (isDistributed()) {
                e.setEvalClass(EvalDist.class);
            } else {
                e.setEvalClass(EvalParallel.class);
            }
        } else {
            EvalCodeGen evalGen = new EvalCodeGen(e, newTablesToAlloc, tableMap);
            Compiler c = new Compiler();
            boolean success = c.compile(evalGen.evalName(), evalGen.generate());
            if (!success) {
                String msg = "Error while compiling eval class:" + c.getErrorMsg();
                L.error(msg);
                throw new SociaLiteException(msg);
            }
            Class<?> evalClass = Loader.forName(evalGen.evalName());
            e.setEvalClass(evalClass);
            addToGeneratedClasses(c.getCompiledClasses());
        }
    }
    boolean isDistributed() {
        return MasterNode.getInstance() != null;
    }

    public TableInstRegistry getTableRegistry() {
        return runtime.getTableRegistry();
    }

    public Class<?> getQueryClass() {
        return queryClass;
    }

    public Runnable getQueryInst() {
        if (queryClass==null || query==null || queryVisitor==null)
            return null;

        Predicate p=query.getP();
        List<Object> args = p.getConstValues();

        Table t=tableMap.get(p.name());
        String queryClsName=queryClass.getName();
        QueryRunnable qr = runtime.getQueryInst(t.id(), queryClsName, queryVisitor);

        qr.setArgs(args);
        qr.setQueryVisitor(queryVisitor);
        //queryInst$.put(p, qr);
        return qr;
    }

    public Query getQuery() { return query; }

    @SuppressWarnings("unchecked")
    public void generateQuery(QueryVisitor qv) {
        if (query==null) return;

        queryVisitor = qv;
        queryClass = queryClass$.get(query.getP(), tableMap);
        if (queryClass!=null) return;

        QueryCodeGen qgen = new QueryCodeGen(query, tableMap, qv);
        Compiler c = new Compiler();
        boolean success=c.compile(qgen.queryName(), qgen.generate());
        if (!success) {
            String msg="Error while compiling a query class:"+c.getErrorMsg();
            throw new SociaLiteException(msg);
        }
        queryClass=(Class<? extends Runnable>)Loader.forName(qgen.queryName());
        queryClass$.put(query.getP(), tableMap, queryClass);
        addToGeneratedClasses(c.getCompiledClasses());
    }

    void generateVisitorBase() {
        addToGeneratedClasses(VisitorBaseGen.generate(newTables));
        addToGeneratedClasses(VisitorBaseGen.generate(an.getRemoteTables()));
    }

    void generateVisitors(Epoch e) {
        Iterator<RuleComp> it = e.getRuleComps().iterator();
        while(it.hasNext()) {
            RuleComp rc=it.next();
            for (Rule r:rc.getRules()) {
                if (r.isSimpleArrayInit()) continue;
                genVisitor(e, r);
            }
        }
    }

    boolean isFirstTableEmpty(Rule r) {
        Object body1=r.getBody().get(0);
        if (body1 instanceof Predicate) {
            Predicate p=(Predicate)body1;
            Table t=tableMap.get(p.name());
            if (newTables.contains(t))
                return true;
        }
        return false;
    }

    void genVisitor(Epoch e, Rule r) {
        String sig=r.signature(tableMap);
        Class<?> klass = joinerClass$.get(sig);
        if (klass==null) {
            JoinerCodeGen vgen = new JoinerCodeGen(e, r, tableMap);
            Compiler c = new Compiler();
            boolean success=c.compile(vgen.joinerName(), vgen.generate());
            if (!success) {
                String msg="Error while compiling visitor class:"+c.getErrorMsg();
                throw new SociaLiteException(msg);
            }
            addToGeneratedClasses(c.getCompiledClasses());
            klass=Loader.forName(vgen.joinerName());
            joinerClass$.put(r.signature(tableMap), klass);
        }
        e.addVisitorClass(r.id(), klass);
    }
}


class QuerySignature {
    String name;
    TIntArrayList constIdx;
    TIntArrayList dontcareIdx;
    public QuerySignature(String _name, TIntArrayList _constIdx, TIntArrayList _dontcareIdx) {
        name = _name;
        constIdx = _constIdx;
        dontcareIdx = _dontcareIdx;
    }
    public int hashCode() { return name.hashCode() ^ constIdx.hashCode(); }
    public boolean equals(Object o) {
        if (!(o instanceof QuerySignature)) return false;

        QuerySignature sig = (QuerySignature)o;
        return name.equals(sig.name) && constIdx.equals(sig.constIdx) && dontcareIdx.equals(sig.dontcareIdx);
    }
    public QuerySignature clone() {
        return new QuerySignature(name, new TIntArrayList(constIdx), new TIntArrayList(dontcareIdx));
    }
}

class CodeClassCache {
    Map<String, Class<?>> classMap;
    int size=0;
    CodeClassCache() {
        classMap = new HashMap<>();
    }
    public synchronized void clear() {
        classMap.clear();
        size=0;
    }
    public synchronized void clearIfLarge() {
        if (size>1024*128) {
            classMap.clear();
            size=0;
        }
    }
    public synchronized Class<?> get(String ruleSig) {
        return classMap.get(ruleSig);
    }
    public synchronized void put(String ruleSig, Class<?> cls) {
        Class<?> old = classMap.put(ruleSig, cls);
        if (old==null) size++;
    }
}
class QueryCache<T> {
    int size=0;
    Map<QuerySignature, T> queryMap;
    QuerySignature sig$ = new QuerySignature("tmp-name", new TIntArrayList(), new TIntArrayList());
    QueryCache() {
        queryMap = new HashMap<QuerySignature, T>();
        size=0;
    }

    public synchronized void clear() {
        queryMap.clear();
        size=0;
    }
    public synchronized void clearIfLarge() {
        if (size > 1024*128) {
            queryMap.clear();
            size=0;
        }
    }
    public synchronized T get(Predicate queryP, Map<String, Table> tableMap) {
        sig$ = getQuerySig(queryP, tableMap);
        return queryMap.get(sig$);
    }
    public synchronized void put(Predicate queryP, Map<String, Table> tableMap, T query) {
        QuerySignature querySig = getNewQuerySig(queryP, tableMap);
        T old = queryMap.put(querySig, query);
        if (old==null) size++;
    }
    QuerySignature getNewQuerySig(Predicate p, Map<String, Table> tableMap) {
        return getQuerySig(p, tableMap).clone();
    }
    QuerySignature getQuerySig(Predicate p, Map<String, Table> tableMap) {
        sig$.name = tableMap.get(p.name()).className();
        sig$.constIdx.resetQuick();
        sig$.dontcareIdx.resetQuick();
        int i=0;
        for (Object o:p.inputParams()) {
            if (o instanceof Const)
                sig$.constIdx.add(i);
            if (isDontCare(o))
                sig$.dontcareIdx.add(i);
            i++;
        }
        return sig$;
    }
    boolean isDontCare(Object o) {
        if (o instanceof Variable) {
            Variable v=(Variable) o;
            return v.dontCare;
        } else { return false; }
    }
}