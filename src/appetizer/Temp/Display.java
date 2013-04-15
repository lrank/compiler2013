package appetizer.Temp;

public class Display implements Addr {
	
	public int depth = 0;
	public int index = 0;
	
	public Display(int d, int i) {
		depth = d;
		index = i;
	}
	
	public String toString() {
		return "D(" + depth + "," + index + ")";
	}
}
