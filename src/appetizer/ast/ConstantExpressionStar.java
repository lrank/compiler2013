package appetizer.ast;

public class ConstantExpressionStar extends Expressions {

	public ConstantExpression constantExpression = null;
	public ConstantExpressionStar constantExpressionStar = null;

	public ConstantExpressionStar(ConstantExpression aa, ConstantExpressionStar bb) {
		constantExpression = aa;
		constantExpressionStar = bb;
	}
}
