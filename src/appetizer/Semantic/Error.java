package appetizer.Semantic;

public final class Error {
	
	public static boolean exitOnFatal = false;
	
	private String message = null;
	
	public Error(Position pos, String str) {
		this(pos, str, false);
	}
	
	public Error(Position pos, String str, boolean silent) {
		message = "Error in line " + (pos.line + 1) + ", column " + (pos.column + 1) + ": " + str;
		
		if (!silent) {
			System.out.println(this);
			System.out.flush();
		}
	}
	
	public String toString() {
		return message;
	}
}
