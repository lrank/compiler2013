package appetizer.ast;

public class RelationalOperatorShiftExpressionStar extends Expressions {

	public RelationalOperator relationalOperator;
	public ShiftExpression shiftExpression;
	public RelationalOperatorShiftExpressionStar relationalOperatorShiftExpressionStar;

	public RelationalOperatorShiftExpressionStar(RelationalOperator o, ShiftExpression aa, RelationalOperatorShiftExpressionStar bb) {
		relationalOperator = o;
		shiftExpression = aa;
		relationalOperatorShiftExpressionStar = bb;
	}
}
