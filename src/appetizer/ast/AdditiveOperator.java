package appetizer.ast;

public class AdditiveOperator extends Operator {

	public static enum Type {
		PLUS, MINUS;
		}

	Type additiveOperator;

	public AdditiveOperator(Type a) {
		additiveOperator = a;
		}
}
