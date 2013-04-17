package ast;

public class Declaration extends Decl {

	public Decl decl1;
	public Decl decl2;

	public Declaration(Decl aa, Decl bb) {
		decl1 = aa;
		decl2 = bb;
	}
}
