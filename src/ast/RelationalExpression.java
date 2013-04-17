package ast;

public class RelationalExpression extends Expressions {

	public ShiftExpression shiftExpression;
	public RelationalOperatorShiftExpressionStar relationalOperatorShiftExpressionStar;

	public RelationalExpression(ShiftExpression aa, RelationalOperatorShiftExpressionStar bb) {
		shiftExpression = aa;
		relationalOperatorShiftExpressionStar = bb;
	}
}
