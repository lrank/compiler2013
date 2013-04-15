package appetizer.ast;

public class LogicalAndExpression extends Expressions {

	public InclusiveOrExpression inclusiveOrExpression;
	public AndInclusiveOrExpressionStar andInclusiveOrExpressionStar;

	public LogicalAndExpression(InclusiveOrExpression aa, AndInclusiveOrExpressionStar bb) {
		inclusiveOrExpression = aa;
		andInclusiveOrExpressionStar = bb;
	}
}
