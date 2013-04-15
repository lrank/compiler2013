package appetizer.ast;

public class OPXORAndExpressionStar extends Expressions {

	public AndExpression andExpression;
	public OPXORAndExpressionStar oPXORAndExpressionStar;

	public OPXORAndExpressionStar(AndExpression aa, OPXORAndExpressionStar bb) {
		andExpression = aa;
		oPXORAndExpressionStar = bb;
	}
}
