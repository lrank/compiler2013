package ast;

public class OrLogicalAndExpressionStar extends Expressions {

	public LogicalAndExpression head;
	public OrLogicalAndExpressionStar next;

	public OrLogicalAndExpressionStar(LogicalAndExpression aa, OrLogicalAndExpressionStar bb) {
		head = aa;
		next = bb;
	}
}
