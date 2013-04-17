package ast;

import symbol.Symbol;

public class TIMESStar extends Decl {

	public Symbol sym;
	public TIMESStar tIMESStar;

	public TIMESStar(Symbol symbol, TIMESStar aa) {
		tIMESStar = aa;
		sym = symbol;
	}
}
