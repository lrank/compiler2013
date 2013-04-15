package appetizer.ast;

public class PlainDeclaration extends Decl {

	public TypeSpecifier typeSpecifier;
	public Decl decl;

	public PlainDeclaration(TypeSpecifier aa, Decl bb) {
		typeSpecifier = aa;
		decl = bb;
	}
}
