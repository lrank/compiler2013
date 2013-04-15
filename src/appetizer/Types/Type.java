package appetizer.Types;

public abstract class Type {
	public Type actual() {
		return this;
	}

	public boolean coerceTo(Type t) {
		return false;
	}
	
	public int size() {
		return 1;
	}
	
	public static final Type INT = new INT();
	public static final Type CHAR = new CHAR();
	public static final Type NIL = new NIL();
	public static final Type STRING = new STRING();
	public static final Type VOID = new VOID();
	public static final Type BOOL = new BOOL();
}
