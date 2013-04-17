package ast;

public class MultiplicativeExpression extends Expressions {

	public CastExpression castExpression;
	public MultiOperatorCastExpressionStar multiOperatorCastExpressionStar;

	public MultiplicativeExpression(CastExpression aa, MultiOperatorCastExpressionStar bb) {
		castExpression = aa;
		multiOperatorCastExpressionStar = bb;
	}
}
