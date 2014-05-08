package socialite.parser.antlr;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import socialite.util.Assert;

public class SortBy extends TableOpt {
	boolean asc=true;
	public SortBy() {}
	public SortBy(String columnName) { super(columnName); }
	public SortBy(String columnName, boolean _asc) {
		this(columnName);
		asc=_asc;
	}
	
	public boolean isAsc() { return asc; }
	public boolean isDesc() { return !asc; }	

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		super.readExternal(in);
		asc=in.readBoolean();
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(asc);
	}
}