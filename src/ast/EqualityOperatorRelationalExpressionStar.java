package ast;

public class EqualityOperatorRelationalExpressionStar extends Expressions {

	public EqualityOperator equalityOperator;
	public RelationalExpression relationalExpression;
	public EqualityOperatorRelationalExpressionStar equalityOperatorRelationalExpressionStar;

	public EqualityOperatorRelationalExpressionStar(EqualityOperator o, RelationalExpression aa, EqualityOperatorRelationalExpressionStar bb) {
		equalityOperator = o;
		relationalExpression = aa;
		equalityOperatorRelationalExpressionStar = bb;
	}
}
