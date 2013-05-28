package translate;

import ast.*;

public class TConst extends TMEM{
	public String c = new String("");
	
	public TConst(Num t) {
		c = "" + t.numvalue;
	}
	
	public TConst(Char t) {
		c = "" + t.charvalue;
	}

	public TConst(SString t) {
		c = t.st;
	}
	
	public TConst(int t) {
		c = "" + t;
	}
	
	public String tostring() {
		return c;
	}
}
