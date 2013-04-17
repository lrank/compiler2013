package ast;

import symbol.Symbol;

public class PlainDeclarator extends Decl {

	public Symbol sym;
	public Decl decl;

	public PlainDeclarator(Decl aa, Symbol symbol) {
		decl = aa;
		sym = symbol;
	}
}
