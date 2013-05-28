package Env;

import translate.TPARA;
import Types.Type;

public class PARAEntry implements Entry{
	public Type ty = null;
	public TPARA name = null;

	public PARAEntry(TPARA s, Type t) {
		ty = t;
		name = s;
	}
	public String to() {
		return name.to();
	}
}
