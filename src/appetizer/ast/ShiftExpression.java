package appetizer.ast;

public class ShiftExpression extends Expressions {

	public AdditiveExpression additiveExpression;
	public ShiftOperatorAdditiveExpressionStar shiftOperatorAdditiveExpressionStar;

	public ShiftExpression(AdditiveExpression aa, ShiftOperatorAdditiveExpressionStar bb) {
		additiveExpression = aa;
		shiftOperatorAdditiveExpressionStar = bb;
	}
}
