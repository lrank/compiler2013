package ast;

public class PostfixStar extends Expressions {

	public Expressions postfix;
	public PostfixStar postfixStar;

	public PostfixStar(Expressions aa, PostfixStar bb) {
		postfix = aa;
		postfixStar = bb;
	}
}
