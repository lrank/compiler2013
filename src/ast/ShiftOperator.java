package ast;

public class ShiftOperator extends Operator {

	public static enum Type {
		SHL, SHR;
		}

	public Type shiftOperator;

	public ShiftOperator(Type a) {
		shiftOperator = a;
		}
}
