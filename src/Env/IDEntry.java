package Env;

import translate.TID;
import Types.Type;

public class IDEntry implements Entry{

	public Type ty = null;
	public TID name = null;
	public int lev;

	public IDEntry(TID s, Type t, int l) {
		ty = t;
		name = s;
		lev = l;
	}
	public String to() {
		return name.to();
	}
}
