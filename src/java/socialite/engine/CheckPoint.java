package socialite.engine;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Iterator;


import java.lang.reflect.Array;

import socialite.resource.TableInstRegistry;
import socialite.tables.TableInst;
import socialite.util.Assert;
import socialite.util.SociaLiteException;
import socialite.util.SocialiteInputStream;
import socialite.util.SocialiteOutputStream;

public class CheckPoint {
	String backupPath;
	String indexFile;
	String sep=File.separator;
	
	public CheckPoint(String _path) {
		backupPath=_path;
		indexFile=backupPath+sep+"index";
	}
	
	ObjectOutputStream openIndexFile(boolean deletePrev) {
		try{
			File f=new File(indexFile);
			f.getParentFile().mkdirs();
			if (deletePrev) f.delete();
			FileOutputStream fos=new FileOutputStream(f);
			ObjectOutputStream index=new SocialiteOutputStream(fos);//new ObjectOutputStream(fos);
			return index;
		} catch (IOException e) {
			throw new SociaLiteException("Exception while checkpointing:"+e);	
		}
	}
	void close(Closeable file) {
		try { file.close(); }
		catch (IOException e) {
			Assert.die("Exception while checkpointing:"+e);
		}
	}
	
	ObjectInputStream readIndexFile() {
		try {
			File f=new File(indexFile);
			if (!f.exists()) 
				throw new SociaLiteException("Table index file does not exist");
			FileInputStream fis=new FileInputStream(f);
			ObjectInputStream index=new SocialiteInputStream(fis);
			return index;
		} catch (IOException e) {
			Assert.die("CheckPoint:Exception while reading index file");
		}
		return null;
	}
	public void checkpoint(TableInstRegistry registry) {
		ObjectOutputStream index=openIndexFile(true);
		ObjectOutput out;		
		
		Iterator<TableInst[]> it=registry.iterator();
		try { 
			while (it.hasNext()) {
				TableInst[] tableArray=it.next();
				if (tableArray==null) continue;
				
				index.writeObject(tableArray[0].getClass());
				index.writeInt(tableArray[0].id());
				index.writeInt(tableArray.length);
				int sliceIdx=0;			
				for (TableInst inst:tableArray) {					
					File f=new File(backupPath+"tableInsts"+sep+"table_"+inst.id()+"#"+sliceIdx);
					f.getParentFile().mkdirs();
					FileOutputStream fos=new FileOutputStream(f);
					ObjectOutputStream oos=new SocialiteOutputStream(fos);//new ObjectOutputStream(fos);
					oos.writeObject(inst);
					oos.close();
					sliceIdx++;
				}			
			}
		} catch (IOException e) {
			Assert.die("Exception while checkpointing:"+e);
		}
		close(index);
	}
	
	public void resume(TableInstRegistry registry) {
		ObjectInputStream index=readIndexFile();
		
		try {
			Class klass=(Class)index.readObject();
			int id=index.readInt();
			int len=index.readInt();
			TableInst[] tableArray=(TableInst[])Array.newInstance(klass, len);
			for (int i=0; i<tableArray.length; i++) {
				File f=new File(backupPath+"tableInsts"+sep+"table_"+id+"#"+i);
				Assert.true_(f.exists());
				FileInputStream fis=new FileInputStream(f);
				ObjectInputStream ois=new SocialiteInputStream(fis);
				tableArray[i]=(TableInst)ois.readObject();
				ois.close();
			}
			registry.setTableInstArray(id, tableArray);
		} catch (IOException e) {
			Assert.die("Error while resuming:"+e);
		} catch (ClassNotFoundException e) {
			Assert.die("Error while resuming:"+e);
		}
		close(index);
	}
	
	public static void main(String[] args) {
		System.out.println((new Double(10.0d)).toString());
	}
}
