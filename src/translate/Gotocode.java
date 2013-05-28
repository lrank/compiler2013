package translate;

public class Gotocode extends Tcode{
	public Label l = null;
	
	public Gotocode(Label ll) {
		l = ll;
	}
	
	public String tostring() {
		return "Goto " + l.tostring();
	}
}
