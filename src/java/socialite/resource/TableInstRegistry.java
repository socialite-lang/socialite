package socialite.resource;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.parser.Table;
import socialite.tables.TableInst;
import socialite.util.SociaLiteException;
import socialite.util.concurrent.AtomicUtil;

// A registry for table instances
public class TableInstRegistry {
    public static final Log L=LogFactory.getLog(TableInstRegistry.class);

    SRuntime runtime;
    volatile TableInst[][] tableInstArrayMap; // [][] = [/* table-id */][/* partition */]

    public TableInstRegistry(SRuntime _runtime) {
        runtime = _runtime;
        tableInstArrayMap = makeTableArrayMap(runtime.getTableMap());
    }

    TableInst[][] makeTableArrayMap(Map<String, Table> tableMap) {
        int maxId=0;
        for (Table t:tableMap.values()) {
            if (t.id()>maxId) maxId=t.id();
        }
        TableInst[][] idToTableArray = new TableInst[maxId+1][];
        return idToTableArray;
    }

    /* invoked from eval-code (generated by EvalCodeGen) */
    public void setTableInstArray(int tableId, TableInst[] tableArray) {
        if (tableId >= tableInstArrayMap.length) {
            ensureCapacity(tableId+1);
        }
        tableInstArrayMap[tableId] = tableArray;
    }

    public TableInst[] getTableInstArray(int tableId) {
        if (tableId >= tableInstArrayMap.length) return null;
        return tableInstArrayMap[tableId];
    }

    public void clearTable(String tableName) {
        Table t = runtime.getTableMap().get(tableName);
        if (t==null) {
            throw new SociaLiteException(tableName+" does not exist");
        }
        int tableId = t.id();
        TableInst[] tableArray = tableInstArrayMap[tableId];
        for (TableInst inst:tableArray) {
            inst.clear();
        }
    }
    public void clearTable(int id) {
        TableInst[] tableArray = tableInstArrayMap[id];
        for (TableInst inst:tableArray) {
            inst.clear();
        }
    }
    public void dropTable(String tableName) {
        Table t = runtime.getTableMap().get(tableName);
        if (t==null) {
            throw new SociaLiteException(tableName+" does not exist");
        }
        int tableId = t.id();
        tableInstArrayMap[tableId] = null;
    }
    public void dropTable(int id) {
        tableInstArrayMap[id] = null;
    }

    synchronized void ensureCapacity(int minCapacity) {
        int oldCapacity=tableInstArrayMap.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity*3)/2+1;
            newCapacity = Math.max(minCapacity, newCapacity);
            tableInstArrayMap = AtomicUtil.atomicIncreaseCapacity(tableInstArrayMap, newCapacity);
        }
    }
}
