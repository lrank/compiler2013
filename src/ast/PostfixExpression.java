package ast;

public class PostfixExpression extends UnaryExpressionAll {

	public PrimaryExpression primaryExpression;
	public PostfixStar postfixStar;

	public PostfixExpression(PrimaryExpression aa, PostfixStar bb) {
		primaryExpression = aa;
		postfixStar = bb;
	}
}
