package appetizer.Types;

public final class BOOL extends Type {
	public boolean coerceTo(Type t) {
		return t.actual() instanceof BOOL;
	}
}
