package appetizer.ast;

public class UnaryExpression extends UnaryExpressionAll {

	public UnaryOperator unaryOperator;
	public UnaryExpressionAll unaryExpressionAll;

	public UnaryExpression(UnaryOperator o, UnaryExpressionAll aa) {
		unaryOperator = o;
		unaryExpressionAll = aa;
	}
}
