package ast;

public class OporExclusiveOrExpressionStar extends Expressions {

	public ExclusiveOrExpression exclusiveOrExpression;
	public OporExclusiveOrExpressionStar oporExclusiveOrExpressionStar;

	public OporExclusiveOrExpressionStar(ExclusiveOrExpression aa, OporExclusiveOrExpressionStar bb) {
		exclusiveOrExpression = aa;
		oporExclusiveOrExpressionStar = bb;
	}
}
