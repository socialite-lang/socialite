package socialite.parser;

import gnu.trove.list.array.TIntArrayList;

public interface GeneratedT {
	public Table origT();
	public int origId();
	
	public static class ConstCols {
		public static int[] get(Predicate p) {
			TIntArrayList constCols = new TIntArrayList();			
			for (int i=0; i<p.inputParams().length; i++) {			
				Object o=p.inputParams()[i];
				if (o instanceof Const) 
					constCols.add(i);
			}
			return constCols.toArray();
		}
		public static void init(Table t, Predicate p) {			
			TIntArrayList constCols = new TIntArrayList();			
			assert t.numColumns()==p.inputParams().length;
			for (int i=0; i<p.inputParams().length; i++) {			
				Object o=p.inputParams()[i];
				if (o instanceof Const) {
					Const c=(Const)o;
					Column col=t.getColumn(i);
					col.setConst(c.val);
					if (col.hasRange()) {
						assert c.val instanceof Integer;
						int r=(Integer)c.val;
						col.setRange(r, r);
					}
					constCols.add(i);
				}
			}
		}
	}
}
