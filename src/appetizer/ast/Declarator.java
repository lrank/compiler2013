package appetizer.ast;

public class Declarator extends Decl {

	public PlainDeclarator plainDeclarator = null;
	public ConstantExpressionStar constantExpressionStar = null;
	public Parameters parameters = null;
	
	public Declarator(PlainDeclarator aa) {
		plainDeclarator = aa;
	}
	public Declarator(PlainDeclarator aa, ConstantExpressionStar bb) {
		plainDeclarator = aa;
		constantExpressionStar = bb;
	}
	public Declarator(PlainDeclarator aa, Parameters bb) {
		plainDeclarator = aa;
		parameters = bb;
	}
}
