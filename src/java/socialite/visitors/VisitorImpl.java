package socialite.visitors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.eval.Worker;
import socialite.tables.TableInst;
import socialite.util.Assert;

/** 
 * Auto-generated from VisitorBaseGen.java.
 * Do not edit manually, unless you know what you're doing.
 */
public abstract class VisitorImpl implements IVisitor {
	public static final Log L = LogFactory.getLog(VisitorImpl.class);
	
	protected Worker worker=null;

	private boolean priority=false;	
	public void incPriority() { priority = true; }
	public boolean hasPriority() { return priority; }

	public void setWorker(Worker w) { worker = w; }
	public Worker getWorker() { return worker; }	
	public int getWorkerId() { return worker.id(); }	

	public void run() { Assert.not_implemented(); }
    public abstract int getEpochId();
	public abstract int getRuleId();
	public TableInst[] getDeltaTables() { return null; }
	public TableInst[] getPrivateTable() { return null; }

	public boolean visit_0(int a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0(long a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0(float a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0(double a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0(Object a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_1(int a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_1(long a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_1(float a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_1(double a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_1(Object a1) {
		Assert.not_implemented();
		return false;
	}

	public boolean visit_0_1(int a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(int a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(int a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(int a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(int a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(long a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(long a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(long a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(long a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(long a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(float a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(float a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(float a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(float a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(float a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(double a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(double a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(double a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(double a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(double a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(Object a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(Object a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(Object a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(Object a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1(Object a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(int a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(long a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(float a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(double a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit_0_1_2(Object a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,int a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,long a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,float a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,double a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,Object a2) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(int a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(long a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(float a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(double a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,int a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,int a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,int a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,int a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,int a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,long a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,long a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,long a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,long a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,long a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,float a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,float a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,float a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,float a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,float a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,double a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,double a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,double a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,double a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,double a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,Object a2,int a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,Object a2,long a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,Object a2,float a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,Object a2,double a3) {
		Assert.not_implemented();
		return false;
	}
	public boolean visit(Object a1,Object a2,Object a3) {
		Assert.not_implemented();
		return false;
	}
}
