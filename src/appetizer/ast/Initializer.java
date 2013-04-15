package appetizer.ast;

public class Initializer extends Decl {

	public Initializer initializer1 = null;
	public Initializer initializer2 = null;
	public AssignmentExpression assignmentExpression = null;

	public Initializer(Initializer aa, Initializer bb) {
		initializer1 = aa;
		initializer2 = bb;
	}
	public Initializer(AssignmentExpression xx) {
		assignmentExpression = xx;
	}
}
