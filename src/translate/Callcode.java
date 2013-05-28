package translate;

public class Callcode extends Tcode{
	public String t1;
	public varinfo t2;
	public Label l;
	
	public Callcode(String r1, varinfo r2, Label la) {
		t1 = r1;
		t2 = r2;
		l = la;
	}
	
	public String tostring() {
		return "Call " + t1 + " returnValueTo " + t2.tostring();
	}
}
