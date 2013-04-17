package ast;

public class InitDeclarator extends Decl {

	public Declarator declarator;
	public Initializer initializer;

	public InitDeclarator(Declarator aa, Initializer bb) {
		declarator = aa;
		initializer = bb;
	}
}
