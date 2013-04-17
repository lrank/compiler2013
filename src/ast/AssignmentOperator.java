package ast;

public class AssignmentOperator extends Operator {

	public static enum Type {
		 ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, ADD_ASSIGN,
		 SUB_ASSIGN, SHL_ASSIGN, SHR_ASSIGN, AND_ASSIGN, XOR_ASSIGN, OR_ASSIGN;
		}

	Type assignmentOperator;

	public AssignmentOperator(Type a) {
		assignmentOperator = a;
		}
}
