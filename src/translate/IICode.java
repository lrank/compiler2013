package translate;

public class IICode extends Tcode{
	public varinfo t1 = null, t3 = null;
	public String op = null;
	
	public IICode(varinfo r1, String o, varinfo r3) {
		t1 = r1;
		op = o;
		t3 = r3;
	}
	
	public String tostring() {
		return t1.tostring() + " = " + op + " " + t3.tostring();
	}
}
