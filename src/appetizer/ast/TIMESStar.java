package appetizer.ast;

import appetizer.symbol.Symbol;

public class TIMESStar extends Decl {

	public Symbol sym;
	public TIMESStar tIMESStar;

	public TIMESStar(Symbol symbol, TIMESStar aa) {
		tIMESStar = aa;
		sym = symbol;
	}
}
