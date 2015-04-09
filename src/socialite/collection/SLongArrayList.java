package socialite.collection;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import gnu.trove.list.array.TLongArrayList;

public class SLongArrayList extends TLongArrayList {
	static final long serialVersionUID = 1L;

	public SLongArrayList() {
		//	should be only called by the serialization code
		super(0);
	}

	public SLongArrayList(int capacity) {
		super(capacity);
	}
	public int capacity() { return _data.length; }
	public boolean filledToCapacity() {
		if (_pos == _data.length)
			return true;
		return false;
	}
	
	public void addAllFast(SLongArrayList other) {
		if (other==null) return;
		int remain = _data.length - _pos; 
			
		if (remain < other.size()) {			
			int newCapacity=Math.max((int)(_data.length*1.5f), _pos+other.size()+1);
			_data = Arrays.copyOf(_data, newCapacity);
		}
		
		System.arraycopy(other._data, 0, _data, _pos, other.size());
		_pos += other.size();
	}
	
	@Override
	public boolean add(long val) {
		return super.add(val);		
	}	

	public void writeExternal(ObjectOutput out) throws IOException {
		// VERSION out.writeByte(0);
		
		// POSITION
		out.writeInt(_pos);
		// NO_ENTRY_VALUE
		out.writeLong(no_entry_value);

		// ENTRIES
		int len = _pos;
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			out.writeLong(_data[i]);
		}
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// VERSION in.readByte();
		
		// POSITION
		_pos = in.readInt();
		// NO_ENTRY_VALUE
		no_entry_value = in.readLong();

		// ENTRIES
		int len = in.readInt();
		if (_data==null) _data = new long[len];
		if (_data.length < len) _data = new long[len];
		
		for (int i = 0; i < len; i++) {
			_data[i] = in.readLong();
		}
	}	
}
