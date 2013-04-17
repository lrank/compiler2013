package ast;

public class InitDeclarators extends Decl {

	public InitDeclarator initDeclarator = null;
	public InitDeclarators next = null;

	public InitDeclarators(InitDeclarator aa, InitDeclarators bb) {
		initDeclarator = aa;
		next = bb;
	}
}
