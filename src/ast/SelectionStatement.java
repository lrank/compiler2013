package ast;

public class SelectionStatement extends Statements {

	public Expressions expressions = null;
	public Statements statements1 = null;
	public Statements statements2 = null;

	public SelectionStatement(Expressions ee, Statements aa, Statements bb) {
		expressions = ee;
		statements1 = aa;
		statements2 = bb;
	}
}
