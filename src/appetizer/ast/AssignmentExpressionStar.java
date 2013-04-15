package appetizer.ast;

public class AssignmentExpressionStar extends Expressions {

	public AssignmentExpression assignmentExpression;
	public AssignmentExpressionStar assignmentExpressionStar;

	public AssignmentExpressionStar(AssignmentExpression aa, AssignmentExpressionStar bb) {
		assignmentExpression = aa;
		assignmentExpressionStar = bb;
		}
}
