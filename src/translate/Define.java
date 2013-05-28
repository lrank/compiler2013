package translate;

import Types.*;

public class Define extends Tcode{
	public varinfo v;
	
	public Define(varinfo a) {
		v = a;
	}
	
	public String tostring() {
		return "DEF " + v.name + " " + v.level + " " + v.offset;
	}
}
