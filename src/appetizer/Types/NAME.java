package appetizer.Types;

import appetizer.Semantic.Semant;
import appetizer.symbol.Symbol;

public final class NAME extends Type {
	public Symbol name;
	private Type binding;

	public NAME(Symbol n) {
		name = n;
	}
	
	public Type getfieldType(Semant s) {
		if (binding != null)
			return binding;
		binding = s.getType(name.toString());
		return binding;
	}

	public boolean isLoop() {
		Type b = binding;
		boolean any;
		binding = null;
		if (b == null)
			any = true;
		else if (b instanceof NAME)
			any = ((NAME) b).isLoop();
		else
			any = false;
		binding = b;
		return any;
	}

	public Type actual() {
		return binding.actual();
	}

	public boolean coerceTo(Type t) {
		return this.actual().coerceTo(t);
	}
	
	public int size() {
		return this.actual().size();
	}

	public void bind(Type t) {
		binding = t;
	}
}
