package ast;

public class CastExpression extends Expressions {

	public TypeName type;
	public CastExpression castExpression;

	public CastExpression() {
	}
	public CastExpression(TypeName aa, CastExpression bb) {
		type = aa;
		castExpression = bb;
	}
}
