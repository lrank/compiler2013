package translate;

public class PARAcode extends Tcode{
	public varinfo t1;
	public Label l;
	
	public PARAcode(varinfo r1) {
		t1 = r1;
	}
	
	public PARAcode(varinfo r1, Label r2) {
		t1 = r1;
		l = r2;
	}
	
	public String tostring() {
		return "PARA " + t1.tostring();
	}
}
