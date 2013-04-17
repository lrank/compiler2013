package ast;

public class Arguments extends Expressions {

	public AssignmentExpression assignmentExpression;
	public Arguments next;

	public Arguments(AssignmentExpression aa, Arguments bb) {
		assignmentExpression = aa;
		next = bb;
	}
}
