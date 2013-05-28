package translate;

public class Label extends Tcode{

	private static int count = 0;

	private String i;

	public Label() {
		i = "" + (++count);
	}
	
	public Label(String s) {
		i = s;
	}

	public String tostring() {
		return "Label_" + i;
	}
}
