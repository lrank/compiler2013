package appetizer.Types;

public final class CHAR extends Type {
	public boolean coerceTo(Type t) {
		return t.actual() instanceof CHAR;
	}
}
