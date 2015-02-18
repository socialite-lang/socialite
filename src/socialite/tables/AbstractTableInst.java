package socialite.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.util.Assert;

public abstract class AbstractTableInst implements TableInst {
	public static final Log L=LogFactory.getLog(AbstractTableInst.class);
	
	public void clear(int from, int to) {
		Assert.not_supported();
	}
	public int totalAllocSize() {
		Assert.not_implemented();
		return -1;
	}
}
