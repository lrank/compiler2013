package translate;

public class TPARA {
	private static int count = 0;
	private int i;
	public int offset = -1;
	
	public TPARA(int o) {
		i = ++count;
		offset = o;
	}
	
	public String to() {
		return "PARA" + i;
	}
	
	public void toprint() {
		System.out.println(this.toString());
	}
}
