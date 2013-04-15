package appetizer.ast;

public class Parameters extends Decl {

	public Decl decl1;
	public Parameters decl2;

	public Parameters(Decl aa, Parameters bb) {
		decl1 = aa;
		decl2 = bb;
	}
}
