package appetizer.Env;

import appetizer.Types.Type;

public class VarEntry implements Entry {
	public Type ty = null;
	public String name = null;

	public VarEntry(Type t, String s) {
		ty = t;
		name = s;
	}
}
