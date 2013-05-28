package translate;

public class TID extends TMEM{
	public static int count = 0;
	private int i;
	public int offset = -1;
	
	public TID(int o) {
		i = ++count;
		offset = o;
	}
	
	public String to() {
		return "ID" + i;
	}
	
	public void toprint() {
		System.out.println(this.toString());
	}
}
