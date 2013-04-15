package appetizer.ast;

public class DeclarationStar extends Decl {

	public Decl decl;
	public DeclarationStar declarationStar;

	public DeclarationStar(Decl aa, DeclarationStar bb) {
		decl = aa;
		declarationStar = bb;
	}
}
