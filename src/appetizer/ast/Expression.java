package appetizer.ast;

public class Expression extends Expressions {

	public AssignmentExpression assignmentExpression;
	public Expression next;

	public Expression(AssignmentExpression aa, Expression bb) {
		assignmentExpression = aa;
		next = bb;
		}
}
