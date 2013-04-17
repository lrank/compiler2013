package Env;

import Types.*;

public class FunEntry implements Entry {
	public Type type = null;
	public String name = null;
	public RECORD para = null;
	
	protected FunEntry(Type r, String l) {
		type = r;
		name = l;
	}
	public FunEntry(Type r, String l, RECORD p) {
		this(r, l);
		para = p;
	}
}
