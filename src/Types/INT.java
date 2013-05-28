package Types;

public class INT extends Type {
	public boolean coerceTo(Type t) {
		return t.actual() instanceof INT;
	}
}
