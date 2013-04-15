package appetizer.ast;

public class AssignmentExpression extends Expressions {

	public UnaryExpressionAll unaryExpressionAll = null;
	public AssignmentOperator assignmentOperator = null;
	public AssignmentExpression assignmentExpression = null;

	public AssignmentExpression(){
	}
	public AssignmentExpression(UnaryExpressionAll bb, AssignmentOperator cc, AssignmentExpression dd) {
		unaryExpressionAll = bb;
		assignmentOperator = cc;
		assignmentExpression = dd;
		}
}
