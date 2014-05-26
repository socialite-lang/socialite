package socialite.resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.commons.lang3.exception.ExceptionUtils;

import socialite.dist.PathTo;
import socialite.parser.Table;
import socialite.tables.TableInst;
import socialite.util.Assert;
import socialite.util.FastClassLookup;
import socialite.util.FastInputStream;
import socialite.util.FastOutputStream;
import socialite.util.IdFactory;
import socialite.util.Loader;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;


// A registry for table instances
public class TableInstRegistry {
	public static final Log L=LogFactory.getLog(TableInstRegistry.class);
	
	static String tableDataFile = "_tables.dat";
	static String tableMapFile = "_tableMap.dat";
	
	public static String tableDataFile() { return tableDataFile; }
	public static String tableMapFile() { return tableMapFile; }
	
	SRuntime runtime;
	TableInst[][] tableInstArrayMap; // indexed by the table id
	
	public TableInstRegistry(SRuntime _runtime) {
		runtime = _runtime;		
		tableInstArrayMap = makeTableArrayMap(tableMap());
	}
	
	Map<String, Table> tableMap() {
		return runtime.getTableMap();
	}
	
	public void storeTableMap(ObjectOutputStream oos) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64*1024);
			SocialiteOutputStream sos = new SocialiteOutputStream(bout);
			sos.writeObject(tableMap());
			sos.close();
			oos.writeInt(bout.size());
			oos.write(bout.toByteArray());
			oos.close();
		} catch (IOException e) {
			L.error("Exception while storing table map:"+e);
			L.fatal(ExceptionUtils.getStackTrace(e));
		}
	}
	String getTableName(int tableid) {
		for (Entry<String, Table> entry:tableMap().entrySet()) {
			Table t=entry.getValue();
			if (t.id()==tableid) {
				return entry.getKey();
			}
		}
		throw new RuntimeException("Cannot find table id:"+tableid+" in tableMap");
	}
	
	public void store(File path) {
		if (!path.exists()) path.mkdirs();
		assert path.isDirectory();
		try {
			File f = new File(path.getAbsoluteFile(), tableMapFile);
			FileOutputStream fos=new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new SocialiteOutputStream(bos);//new FastOutputStream(bos, new FastClassLookup());
			storeTableMap(oos);
						
			f = new File(path.getAbsoluteFile(), tableDataFile);
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			oos = new SocialiteOutputStream(bos);
			storeTableInsts(oos);			
		} catch (Exception e) {
			L.error("Exception while store("+path+"):"+e);
			L.error(ExceptionUtils.getStackTrace(e));
		} 
	}
	public void storeTableInsts(ObjectOutputStream oos) {
		try {
			oos.writeInt(tableInstArrayMap.length);
			for (int i=0; i<tableInstArrayMap.length; i++) {
				if (tableInstArrayMap[i]==null) {
					oos.writeBoolean(false);
				} else {
					oos.writeBoolean(true);					
					String name = getTableName(i);
					L.info("Storing table "+name);
					Class<?> tableClass = tableInstArrayMap[i][0].getClass();
					oos.writeInt(tableClass.getName().length());
					oos.writeChars(tableClass.getName());
					oos.writeInt(tableInstArrayMap[i].length);
					int reportSize = (tableInstArrayMap[i].length+10-1)/10;
					if (reportSize<1) reportSize=1;
					for (int j=0; j<tableInstArrayMap[i].length; j++) {						
						oos.writeObject(tableInstArrayMap[i][j]);
						if (j%reportSize==reportSize-1) {
							int percent = 10*((j+1)/reportSize);
							if (j+1>=tableInstArrayMap[i].length) percent=100;
							L.info("    "+ percent+"% stored");
						}
					}
					L.info("Finished storing table "+name);
				}
			}
			oos.close();
		} catch (IOException e) {
			L.error("Exception while storing table instances:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
		}
	}
	public void loadTableInsts(ObjectInputStream ois) {
		TableInst[][] prevTableInstArray=null;		
		try {
			int len = ois.readInt();
			prevTableInstArray = new TableInst[len][];
			for (int i=0; i<len; i++) {
				if (ois.readBoolean()) {
					char[] _className = new char[ois.readInt()];
					for (int k=0; k<_className.length; k++) {
						_className[k] = ois.readChar();	
					}
					String className = new String(_className);
					Class<?> tableClass=Loader.forName(className);
					int arraySize = ois.readInt();
					String name = getTableName(i);
					L.info("Loading table "+name);					
					prevTableInstArray[i] = (TableInst[])Array.newInstance(tableClass, arraySize);
					int reportSize = (arraySize+10-1)/10;
					if (reportSize<1) reportSize=1;
					for (int j=0; j<arraySize; j++) {
						prevTableInstArray[i][j] = (TableInst)ois.readObject();
						if (j%reportSize==reportSize-1) {
							int percent = 10*((j+1)/reportSize);
							L.info("    " +percent+"% loaded");
						}
					}
					L.info("Finished loading table "+name);
				}
			}
			ois.close();
		} catch (Exception e) {
			L.error("Exception while loading table instances:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
			return;
		}		
		tableInstArrayMap = prevTableInstArray;
	}
	@SuppressWarnings({ "unchecked", "resource" })
	public Map<String, Table> loadTableMap(ObjectInputStream ois) {
		if (!tableMap().isEmpty()) {
			throw new SociaLiteException("Cannot load tables if there are new tables declared.");
		}
		Map<String, Table> prevTableMap=null;
		try {
			int len = ois.readInt();
			byte[] inbytes = new byte[len];
			for (int i=0; i<len; i++) inbytes[i] = ois.readByte();
			ByteArrayInputStream bin = new ByteArrayInputStream(inbytes);
			prevTableMap = (Map<String, Table>)new SocialiteInputStream(bin).readObject();			
			ois.close();
		} catch (Exception e) {
			L.error("Exception while loading a table map:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
			return null;
		}		
		tableMap().putAll(prevTableMap);
		updateLastUnusedTableId();
		return tableMap();
	}
	
	void updateLastUnusedTableId() {
		int maxTid=0;
		for (Table t:tableMap().values()) {
			if (t.id() > maxTid) 
				maxTid=t.id();
		}
		IdFactory.tableIdAdvanceTo(maxTid+1);
	}
	public Map<String, Table> load(File path) {
		assert path.isDirectory():path+" is not a directory";
		try {
			File f = new File(path.getAbsoluteFile(), tableMapFile);
			if (!f.exists()) return tableMap();
			FileInputStream fis=new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			SocialiteInputStream ois = new SocialiteInputStream(bis);//new FastInputStream(bis, new FastClassLookup());
			loadTableMap(ois);
			
			f = new File(path.getAbsoluteFile(), tableDataFile);
			if (!f.exists()) return tableMap();
			fis = new FileInputStream(f);
			bis = new BufferedInputStream(fis);
			ois = new SocialiteInputStream(bis);
			loadTableInsts(ois);
		} catch (IOException e) {
			L.error("Exception while loading tables:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
			return null;
		}
		return tableMap();
	}
	

	@SuppressWarnings({ "unchecked", "resource" })
	public void load(ObjectInputStream ois) {
		if (!tableMap().isEmpty()) {
			throw new SociaLiteException("Cannot load serialized tables if there are new tables declared.");
		}
		Map<String, Table> prevTableMap=null;
		TableInst[][] prevTableInstArrayMap=null;
		
		try {
			//prevTableMap=(Map)ois.readObject();
			int len = ois.readInt();
			byte[] inbytes = new byte[len];
			for (int i=0; i<len; i++) inbytes[i] = ois.readByte();
			ByteArrayInputStream bin = new ByteArrayInputStream(inbytes);
			prevTableMap = (Map<String, Table>)new SocialiteInputStream(bin).readObject();
			
			//prevTableInstArrayMap=(TableInst[][])ois.readObject();
			len = ois.readInt();
			prevTableInstArrayMap = new TableInst[len][];
			for (int i=0; i<len; i++) {
				if (ois.readBoolean()) {
					prevTableInstArrayMap[i] = (TableInst[])ois.readObject();
				}
			}
			ois.close();
		} catch (Exception e) {
			L.error("Exception while loading tables:"+e);
			L.error(ExceptionUtils.getStackTrace(e));
			return;
		}		
		tableMap().putAll(prevTableMap);
		
		tableInstArrayMap = prevTableInstArrayMap;
		updateLastUnusedTableId();
	}
	
	TableInst[][] makeTableArrayMap(Map<String, Table> tableMap) {
		int maxTableId=0;
		for (Table t:tableMap.values()) {
			if (t.id()>maxTableId) maxTableId=t.id();
		}
		TableInst[][] idToTableArray = new TableInst[maxTableId+1][];
		return idToTableArray;
	}
	
	public void setTableInstArray(int tableId, TableInst[] tableArray) {
		if (tableId >= tableInstArrayMap.length) ensureCapacity(tableId+1);
		
		//assert(tableIdToTableInst[tableId]==null);		
		tableInstArrayMap[tableId] = tableArray;
	}	
	
	public TableInst[] getTableInstArray(int tableId) {
		if (tableId >= tableInstArrayMap.length) return null;
		return tableInstArrayMap[tableId];		
	}
	public void drop(int tableId, String name) {
		Assert.not_supported("dropping is not supported!");
		assert tableId!=-1;
		tableInstArrayMap[tableId] = null;
		tableMap().put(name, null);
	}
	public void clearTable(String tableName) {
		Table t = tableMap().get(tableName);
		if (t==null) {
			throw new SociaLiteException(tableName+" does not exist");
		}
		int tableId = t.id();
		TableInst[] tableArray = tableInstArrayMap[tableId];
		for (TableInst inst:tableArray) {
			inst.clear();
		}
	}
	
	public Iterator<TableInst[]> iterator() {
		return new TableInstIter();
	}
	
	static class TableArray {
		final TableInst[][] array;
		TableArray(TableInst[][] old, int size) {
			array = Arrays.copyOf(old, size);
		}
	}
	synchronized void ensureCapacity(int minCapacity) {
		int oldCapacity=tableInstArrayMap.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity*3)/2+1;
			newCapacity = Math.max(minCapacity, newCapacity);
			tableInstArrayMap = new TableArray(tableInstArrayMap, newCapacity).array;
		}
	}
	
	class TableInstIter implements Iterator<TableInst[]> {
		int idx=0;
		@Override
		public boolean hasNext() {
			while (idx<tableInstArrayMap.length) {
				if (tableInstArrayMap[idx]!=null) {
					return true;
				}
				idx++;
			} 
			return false;
		}

		@Override
		public TableInst[] next() {
			return tableInstArrayMap[idx++];
		}
		@Override
		public void remove() { Assert.not_implemented(); }		
	}
}
