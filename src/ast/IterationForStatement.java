package ast;

public class IterationForStatement extends Statements {

	public Expressions expressions1;
	public Expressions expressions2;
	public Expressions expressions3;
	public Statements statements;

	public IterationForStatement(Expressions aa, Expressions bb, Expressions cc, Statements ss) {
		expressions1 = aa;
		expressions2 = bb;
		expressions3 = cc;
		statements = ss;
	}
}
