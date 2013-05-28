package Env;

import translate.Label;
import Types.Type;

public class LaEntry implements Entry{
	public Type ty = null;
	public Label name = null;

	public LaEntry(Label s, Type t) {
		ty = t;
		name = s;
	}
}
