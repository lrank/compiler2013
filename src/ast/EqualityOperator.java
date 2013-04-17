package ast;

public class EqualityOperator extends Operator {

	public static enum Type {
		EQ, NE;
		}

	Type equalityOperator;

	public EqualityOperator(Type a) {
		equalityOperator = a;
		}
}
