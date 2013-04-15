package appetizer.ast;

import appetizer.symbol.Symbol;

public class PrimaryExpression extends Expressions {

	public Expressions expressions = null;
	public Symbol sym = null;

	public PrimaryExpression(Expressions aa) {
		expressions = aa;
	}
	public PrimaryExpression(Symbol s) {
		sym = s;
	}
}
