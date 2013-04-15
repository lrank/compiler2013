package appetizer.ast;

public class CompoundStatement extends Statements {

	public Decl declaration;
	public Statements statements;

	public CompoundStatement(Decl aa, Statements bb) {
		declaration = aa;
		statements = bb;
	}
}
