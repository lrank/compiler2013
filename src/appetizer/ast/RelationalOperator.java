package appetizer.ast;

public class RelationalOperator extends Operator {

	public static enum Type {
		LT, GT, LE, GE;
		}

	Type relationalOperator;

	public RelationalOperator(Type a) {
		relationalOperator = a;
		}
}
