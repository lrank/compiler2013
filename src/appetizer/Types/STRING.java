package appetizer.Types;

public final class STRING extends Type {
	public boolean coerceTo(Type t) {
		return t.actual() instanceof STRING;
	}
}
