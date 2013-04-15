package appetizer.ast;

public class TypeName extends Expressions {

	public TypeSpecifier typeSpecifier;
	public TIMESStar tIMESStar;

	public TypeName(TypeSpecifier aa, TIMESStar bb) {
		typeSpecifier = aa;
		tIMESStar = bb;
	}
}
