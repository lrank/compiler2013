package appetizer.ast;

public class MultiOperatorCastExpressionStar extends Expressions {

	public MultiplicativeOperator multiplicativeOperator;
	public CastExpression castExpression;
	public MultiOperatorCastExpressionStar multiOperatorCastExpressionStar;

	public MultiOperatorCastExpressionStar(MultiplicativeOperator o, CastExpression aa, MultiOperatorCastExpressionStar bb) {
		multiplicativeOperator = o;
		castExpression = aa;
		multiOperatorCastExpressionStar = bb;
	}
}
