package translate;

public class TRegister extends TMEM{
	private static int count = 0;
	private int i;
	
	public TRegister() {
		i = ++count;
	}
	
	public String to() {
		return "Register" + i;
	}
	
	public void toprint() {
		System.out.println(this.toString());
	}
}
