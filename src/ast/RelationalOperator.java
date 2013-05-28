package ast;

public class RelationalOperator extends Operator {

	public static enum Type {
		LT, GT, LE, GE;
		}

	public Type relationalOperator;

	public RelationalOperator(Type a) {
		relationalOperator = a;
		}
}
