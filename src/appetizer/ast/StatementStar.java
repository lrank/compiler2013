package appetizer.ast;

public class StatementStar extends Statements {

	public Statements statements;
	public StatementStar statementStar;

	public StatementStar(Statements aa, StatementStar bb) {
		statements = aa;
		statementStar = bb;
	}
}
