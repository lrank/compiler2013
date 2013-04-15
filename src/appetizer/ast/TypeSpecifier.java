package appetizer.ast;

import appetizer.symbol.Symbol;

public class TypeSpecifier extends Decl {

	public Symbol sym = null;
	public StructOrUnion structOrUnion = null;
	public Decl decl = null;

	public TypeSpecifier(Symbol symbol) {
		sym = symbol;
	}

	public TypeSpecifier(StructOrUnion bb, Symbol symbol, Decl cc) {
		structOrUnion = bb;
		sym = symbol;
		decl = cc;
	}
}
