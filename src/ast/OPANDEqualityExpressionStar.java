package ast;

public class OPANDEqualityExpressionStar extends Expressions {

	public EqualityExpression equalityExpression;
	public OPANDEqualityExpressionStar oPANDEqualityExpressionStar;

	public OPANDEqualityExpressionStar(EqualityExpression aa, OPANDEqualityExpressionStar bb) {
		equalityExpression = aa;
		oPANDEqualityExpressionStar = bb;
	}
}
