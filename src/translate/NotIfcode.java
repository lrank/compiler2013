package translate;

public class NotIfcode extends Tcode{
	public varinfo t1 = null;
	public Label label;
	
	public NotIfcode(varinfo s, Label la) {
		t1 = s;
		label = la;
	}

	public String tostring() {
		return "NotIf " + t1.tostring() + " " + label.tostring();
	}
}
