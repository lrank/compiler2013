package ast;

public class Program {

	public Decl decl;
	public Program list;

	public Program(Decl s, Program l) {
		decl = s;
		list = l;
	}
}
