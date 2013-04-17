package ast;

public class AndExpression extends Expressions {

	public EqualityExpression equalityExpression;
	public OPANDEqualityExpressionStar oPANDEqualityExpressionStar;

	public AndExpression(EqualityExpression aa, OPANDEqualityExpressionStar bb) {
		equalityExpression = aa;
		oPANDEqualityExpressionStar = bb;
	}
}
