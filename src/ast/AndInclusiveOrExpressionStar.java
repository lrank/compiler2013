package ast;

public class AndInclusiveOrExpressionStar extends Expressions {

	public InclusiveOrExpression inclusiveOrExpression;
	public AndInclusiveOrExpressionStar andInclusiveOrExpressionStar;

	public AndInclusiveOrExpressionStar(InclusiveOrExpression aa, AndInclusiveOrExpressionStar bb) {
		inclusiveOrExpression = aa;
		andInclusiveOrExpressionStar = bb;
	}
}
