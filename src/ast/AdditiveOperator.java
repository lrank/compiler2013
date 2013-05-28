package ast;

public class AdditiveOperator extends Operator {

	public static enum Type {
		PLUS, MINUS;
		}

	public Type additiveOperator;

	public AdditiveOperator(Type a) {
		additiveOperator = a;
		}
}
