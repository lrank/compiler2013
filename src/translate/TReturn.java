package translate;

public class TReturn extends Tcode{
	public varinfo t1 = null;
	
	public TReturn() {
	}
	
	public TReturn(varinfo r1) {
		t1 = r1;
	}
	
	public String tostring() {
		String ret = new String("RETURN");
		if (t1 != null)
			ret += " " + t1.tostring();
		return ret;
	}

}
