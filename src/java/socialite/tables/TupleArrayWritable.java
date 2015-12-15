package socialite.tables;

import org.apache.hadoop.io.ArrayWritable;

public class TupleArrayWritable extends ArrayWritable {
	public TupleArrayWritable() {
		super(TupleW.class);
	}
	public TupleArrayWritable(TupleW[] values) {
		super(TupleW.class, values);
	}
}
