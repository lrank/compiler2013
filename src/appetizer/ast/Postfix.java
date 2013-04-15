package appetizer.ast;

import appetizer.symbol.Symbol;

public class Postfix extends Expressions {

	public static enum Type {
		DOT, PTR, INC, DEC;
		}

	public Type op;
	public Symbol sym;

	public Postfix(Type o, Symbol s) {
		op = o;
		sym = s;
	}
}
