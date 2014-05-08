package socialite.parser.antlr;

public class GroupBy extends TableOpt {
	int groupby;
	public GroupBy() {}
	public GroupBy(int num) {
		groupby = num;
	}
	
	public int groupby() { return groupby; }
}
