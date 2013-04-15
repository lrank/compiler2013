package appetizer.ast;

public class UnaryExpressionToCastExpression extends UnaryExpressionAll {

	public UnaryOperator unaryOperator;
	public CastExpression castExpression;

	public UnaryExpressionToCastExpression(UnaryOperator o, CastExpression aa) {
		unaryOperator = o;
		castExpression = aa;
	}
}
