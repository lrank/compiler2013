package appetizer.ast;

public class ShiftOperator extends Operator {

	public static enum Type {
		SHL, SHR;
		}

	Type shiftOperator;

	public ShiftOperator(Type a) {
		shiftOperator = a;
		}
}
