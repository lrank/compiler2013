package ast;

public class COMMAInitDeclaratorsStar extends Decl {

	public InitDeclarator initDeclarator;
	public COMMAInitDeclaratorsStar cOMMAInitDeclaratorsStar;

	public COMMAInitDeclaratorsStar(InitDeclarator aa, COMMAInitDeclaratorsStar bb) {
		initDeclarator = aa;
		cOMMAInitDeclaratorsStar = bb;
	}
}
