package appetizer.ast;

public class FunctionDefinition extends Decl {

	public Decl decl1, decl2, decl3;
	public Statements statements;

	public FunctionDefinition(Decl aa, Decl bb, Decl cc, Statements dd) {
		decl1 = aa;
		decl2 = bb;
		decl3 = cc;
		statements = dd;
	}
}
