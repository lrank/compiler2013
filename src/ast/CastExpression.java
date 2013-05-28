package ast;

public class CastExpression extends Expressions {

	public TypeName type;
	public CastExpression FacastExpression;

	public CastExpression() {
	}
	public CastExpression(TypeName aa, CastExpression bb) {
		type = aa;
		FacastExpression = bb;
	}
}
