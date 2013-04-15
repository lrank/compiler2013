package appetizer.ast;

public class ExpressionStatement extends Statements {

	public Expressions expressions = null;

	public ExpressionStatement(Expressions aa) {
		expressions = aa;
	}
}
