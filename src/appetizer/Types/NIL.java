package appetizer.Types;

public final class NIL extends Type {
	public boolean coerceTo(Type t) {
		Type a = t.actual();
		return (a instanceof RECORD) || (a instanceof NIL);
	}
}
