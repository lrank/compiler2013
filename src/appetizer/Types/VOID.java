package appetizer.Types;

public final class VOID extends Type {
	public boolean coerceTo(Type t) {
		return t.actual() instanceof VOID;
	}
}
