package appetizer.ast;

public class InclusiveOrExpression extends Expressions {

	public ExclusiveOrExpression exclusiveOrExpression;
	public OporExclusiveOrExpressionStar oporExclusiveOrExpressionStar;

	public InclusiveOrExpression(ExclusiveOrExpression aa, OporExclusiveOrExpressionStar bb) {
		exclusiveOrExpression = aa;
		oporExclusiveOrExpressionStar = bb;
	}
}
