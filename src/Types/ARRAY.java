package Types;

import ast.*;

public final class ARRAY extends Type {
	public Type element;
	public ConstantExpression ex = null;
	public int size;

	public ARRAY(Type e, ConstantExpression xx) {
		element = e;
		ex = xx;
		if (ex != null) {
			constint tmp = new constint();
			size = tmp.transConstantExpression(ex);
		}
	}
	public boolean coerceTo(Type t) {
		return this.actual() == t.actual();
	}
}
