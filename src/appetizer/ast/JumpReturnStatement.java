package appetizer.ast;

public class JumpReturnStatement extends Statements {

	public Expressions expressions;

	public JumpReturnStatement(Expressions ee) {
		expressions = ee;
	}
}
