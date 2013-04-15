package appetizer.ast;

public class UnaryExpressionToTypeName extends UnaryExpressionAll {

	public UnaryOperator unaryOperator;
	public TypeName typeName;

	public UnaryExpressionToTypeName(UnaryOperator o, TypeName aa) {
		unaryOperator = o;
		typeName = aa;
	}
}
