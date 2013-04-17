package ast;

public class ConstantExpression extends Expressions {

	public LogicalOrExpression logicalOrExpression;

	public ConstantExpression(LogicalOrExpression aa) {
		logicalOrExpression = aa;
	}
}
