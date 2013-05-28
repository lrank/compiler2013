package Env;

import translate.TID;
import Types.Type;

public class IDEntry implements Entry{

	public Type ty = null;
	public TID name = null;

	public IDEntry(TID s, Type t) {
		ty = t;
		name = s;
	}
	public String to() {
		return name.to();
	}
}
