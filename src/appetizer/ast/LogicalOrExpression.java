package appetizer.ast;

public class LogicalOrExpression extends AssignmentExpression {

	public LogicalAndExpression logicalAndExpression;
	public OrLogicalAndExpressionStar orLogicalAndExpressionStar;

	public LogicalOrExpression(LogicalAndExpression aa, OrLogicalAndExpressionStar bb) {
		logicalAndExpression = aa;
		orLogicalAndExpressionStar = bb;
	}
}
