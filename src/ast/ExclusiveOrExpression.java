package ast;

public class ExclusiveOrExpression extends Expressions {

	public AndExpression andExpression;
	public OPXORAndExpressionStar oPXORAndExpressionStar;

	public ExclusiveOrExpression(AndExpression aa, OPXORAndExpressionStar bb) {
		andExpression = aa;
		oPXORAndExpressionStar = bb;
	}
}
