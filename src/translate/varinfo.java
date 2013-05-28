package translate;

import Types.*;

public class varinfo {
	public String name;
	public Type type;
	public int offset, level = 0;
	public varinfo off = null;
	
	public varinfo(String a, Type b, int c, int d) {
		name = a;
		type = b;
		offset = c;
		level = d;
	}
	
	public varinfo(String a, Type b, varinfo c, int d) {
		name = a;
		type = b;
		off = c;
		level = d;
	}
	
	public String tostring() {
		return name;
	}
}
