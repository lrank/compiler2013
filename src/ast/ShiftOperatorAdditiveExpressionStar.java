package ast;

public class ShiftOperatorAdditiveExpressionStar extends Expressions {

	public ShiftOperator shiftOperator;
	public AdditiveExpression additiveExpression;
	public ShiftOperatorAdditiveExpressionStar shiftOperatorAdditiveExpressionStar;

	public ShiftOperatorAdditiveExpressionStar(ShiftOperator o, AdditiveExpression aa, ShiftOperatorAdditiveExpressionStar bb) {
		shiftOperator = o;
		additiveExpression = aa;
		shiftOperatorAdditiveExpressionStar = bb;
	}
}
