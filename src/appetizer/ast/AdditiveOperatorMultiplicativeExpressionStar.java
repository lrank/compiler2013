package appetizer.ast;

public class AdditiveOperatorMultiplicativeExpressionStar extends Expressions {

	public AdditiveOperator additiveOperator;
	public MultiplicativeExpression multiplicativeExpression;
	public AdditiveOperatorMultiplicativeExpressionStar additiveOperatorMultiplicativeExpressionStar;

	public AdditiveOperatorMultiplicativeExpressionStar(AdditiveOperator o, MultiplicativeExpression aa, AdditiveOperatorMultiplicativeExpressionStar bb) {
		additiveOperator = o;
		multiplicativeExpression = aa;
		additiveOperatorMultiplicativeExpressionStar = bb;
	}
}
