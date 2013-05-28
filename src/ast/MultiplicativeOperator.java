package ast;

public class MultiplicativeOperator extends Operator {

	public static enum Type {
		TIMES, DIVIDE, MOD;
		}

	public Type multiplicativeOperator;

	public MultiplicativeOperator(Type a) {
		multiplicativeOperator = a;
		}
}
