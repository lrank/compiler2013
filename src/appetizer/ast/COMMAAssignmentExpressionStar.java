package appetizer.ast;

public class COMMAAssignmentExpressionStar extends Expressions {

	public AssignmentExpression assignmentExpression;
	public COMMAAssignmentExpressionStar cOMMAAssignmentExpressionStar;

	public COMMAAssignmentExpressionStar(AssignmentExpression aa, COMMAAssignmentExpressionStar bb) {
		assignmentExpression = aa;
		cOMMAAssignmentExpressionStar = bb;
	}
}
