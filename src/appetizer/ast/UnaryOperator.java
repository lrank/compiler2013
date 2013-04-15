package appetizer.ast;

public class UnaryOperator extends Operator {

	public static enum Type {
		INC, DEC, SIZEOF, OPAND, TIMES, PLUS, MINUS, NEGATE, NOT;
		}

	public Type unaryOperator;
	public UnaryOperator(Type a) {
		unaryOperator = a;
		}
	public boolean equals(Type t) {
		return (unaryOperator == t);
	}
}
