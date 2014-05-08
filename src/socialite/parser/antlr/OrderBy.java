package socialite.parser.antlr;

public class OrderBy extends TableOpt {
	public OrderBy() {}
	public OrderBy(String columnName) {
		super(columnName);
	}
}