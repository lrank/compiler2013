package ast;

public class EqualityExpression extends Expressions {

	public RelationalExpression relationalExpression;
	public EqualityOperatorRelationalExpressionStar equalityOperatorRelationalExpressionStar;

	public EqualityExpression(RelationalExpression aa, EqualityOperatorRelationalExpressionStar bb) {
		relationalExpression = aa;
		equalityOperatorRelationalExpressionStar = bb;
	}
}
