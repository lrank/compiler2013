package appetizer.ast;

public class IterationWhileStatement extends Statements {

	public Expressions expressions;
	public Statements statements;

	public IterationWhileStatement(Expressions bb, Statements aa) {
		statements = aa;
		expressions = bb;
	}
}
