package Types;

import symbol.Symbol;
import ast.*;

public final class RECORD extends Type {
	private RECORD() {
		// let type a={} in end
	}
	
	public static final RECORD empty = new RECORD();
	
	public StructOrUnion sou = null;
	public Symbol fieldName = null;
	public Type fieldType = null;
	public RECORD tail = null;
	public boolean variable = false;

	public RECORD(StructOrUnion sss, Symbol n, Type t, RECORD x) {
		sou = sss;
		fieldName = n;
		fieldType = t;
		tail = x;
	}

	public boolean coerceTo(Type t) {
		return this.actual() == t.actual();
	}
	
	public int size() {
		return 1 + (tail == null ? 0 : tail.size());
	}

	public RECORD getField(Symbol field) {
		if (fieldName == field) {
			return this;
		}
		if (tail != null) {
			return tail.getField(field);
		}
		return null;
	}

	public int getFieldIndex(Symbol field) {
		return getFieldIndex(field, 0);
	}
	
	private int getFieldIndex(Symbol field, int index) {
		if (fieldName == field) {
			return index;
		}
		if (tail != null) {
			return tail.getFieldIndex(field, index + 1);
		}
		return -1;
	}
}
