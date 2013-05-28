package translate;

public class Ifcode extends Tcode{
	public varinfo t1 = null;

	public Ifcode(varinfo s) {
		t1 = s;
	}
	
	public String tostring() {
		return "If " + t1.tostring();
	}
}
