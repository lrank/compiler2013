package appetizer.ast;

public class AdditiveExpression extends Expressions {

	public MultiplicativeExpression multiplicativeExpression;
	public AdditiveOperatorMultiplicativeExpressionStar additiveOperatorMultiplicativeExpressionStar;

	public AdditiveExpression(MultiplicativeExpression aa, AdditiveOperatorMultiplicativeExpressionStar bb) {
		multiplicativeExpression = aa;
		additiveOperatorMultiplicativeExpressionStar = bb;
	}
}
