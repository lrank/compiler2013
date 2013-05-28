package translate;

public class InCode extends Tcode{
	public varinfo t1 = null, t2 = null, t3 = null;
	public String op = null;
	
	public InCode(varinfo r1, varinfo r2, String o, varinfo r3) {
		t1 = r1;
		t2 = r2;
		op = o;
		t3 = r3;
	}
	
	public String tostring() {
		return t1.tostring() + " = " + t2.tostring() + " " + op + " " + t3.tostring();
	}
}
