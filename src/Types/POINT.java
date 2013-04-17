package Types;

public final class POINT extends Type {
	public Type element;

	public boolean coerceTo(Type t) {
		return this.actual() == t.actual();
	}
	public POINT(Type e) {
		element = e;
	}
}
