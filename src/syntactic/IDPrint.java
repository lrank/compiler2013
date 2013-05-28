package syntactic;

public class IDPrint {
	String s = new String();
	
	public IDPrint(){
	}
	
	public void add(String st) {
		s += st;
	}
	
	public void toprint() {
		System.out.println(s);
	}
}
