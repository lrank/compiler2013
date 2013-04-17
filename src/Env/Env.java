package Env;

import symbol.*;
import Types.*;

public final class Env {
	public Table tEnv = null;
	public Table vEnv = null;
	public Env next = null;
	
	public Env() {
		initTEnv();
		initVEnv();
	}

	private static Symbol sym(String n) {
		return Symbol.symbol(n);
	}
	
	private void initTEnv() {
		tEnv = new Table();
		tEnv.put(sym("VOID"), Type.VOID);
		tEnv.put(sym("INT"), Type.INT);
		tEnv.put(sym("CHAR"), Type.CHAR);
		tEnv.put(sym("STRING"), Type.STRING);
	}

	/**
	 * All "library" functions are declared at the outermost level,
	 * which does not contain a frame or formal parameter list.
	 */
	private void initVEnv() {
		vEnv = new Table();
		
		vEnv.put(sym("malloc"), new FunEntry(new POINT(POINT.INT), "malloc",
				new RECORD(null, null, Type.INT, null)));
		vEnv.put(sym("getchar"), new FunEntry(Type.INT, "getchar", null));
		vEnv.put(sym("putchar"), new FunEntry(Type.INT, "putchar", null));
		
		vEnv.put(sym("strcpy"), new FunEntry(Type.VOID, "strcpy"));
		vEnv.put(sym("strcmp"), new FunEntry(Type.VOID, "strcmp"));
		vEnv.put(sym("strcat"), new FunEntry(Type.VOID, "strcat"));

		RECORD r1 = new RECORD(null, null, Type.STRING, null);
		r1.variable = true;
		vEnv.put(sym("printf"), new FunEntry(Type.VOID, "printf", r1));
		vEnv.put(sym("scanf"), new FunEntry(Type.VOID, "scanf", r1));
	}
	
	public void beginScope() {
		vEnv.beginScope();
		tEnv.beginScope();
	}
	
	public void endScope() {
		vEnv.endScope();
		tEnv.endScope();
	}
}
