package ast;

public class TypeSpecifierDeclarators extends Decl {

	public TypeSpecifier typeSpecifier;
	public Declarators declarators;
	public TypeSpecifierDeclarators typeSpecifierDeclarators;

	public TypeSpecifierDeclarators(TypeSpecifier aa, Declarators bb, TypeSpecifierDeclarators cc) {
		typeSpecifier = aa;
		declarators = bb;
		typeSpecifierDeclarators = cc;
	}
}
