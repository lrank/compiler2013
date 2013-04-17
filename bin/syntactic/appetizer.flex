package syntactic;
import java_cup.runtime.*;

%%

%unicode
%line
%column
%cup
%implements Symbols

%{
	String string = new String();

	private void err(String message) {
		System.out.println("Scanning error in line " + yyline + ", column " + yycolumn + ": " + message);
	}

	private java_cup.runtime.Symbol tok(int kind) {
		return new java_cup.runtime.Symbol(kind, yyline, yycolumn);
	}

	private java_cup.runtime.Symbol tok(int kind, Object value) {
		return new java_cup.runtime.Symbol(kind, yyline, yycolumn, value);
	}

%}

%eofval{
	{
		if (yystate() == YYCOMMENT) {
			err("Comment symbol do not match (EOF)!");
		}
		return tok(EOF, null);
	}
%eofval}

LineTerm = \n|\r|\r\n
Identifier = [A-Za-z]+[0-9A-Za-z_$]*
DecInteger = 0|[1-9][0-9]*
OctInteger = 0[0-7]+
HexInteger = 0[x|X][0-9|A-F|a-f]+
Whitespace = {LineTerm}|[ \t\f]
Char = '[^\\]'|'\\.'|'\\n'|'\\t'|'\\\'|'\\''

%state STRING
%state PRE

%state	YYCOMMENT
%state	YYCOMMENT_TWO

%%

<YYINITIAL> {
	"/*" { yybegin(YYCOMMENT); }
	"*/" { err("Comment symbol do not match!"); }
	"//" { yybegin(YYCOMMENT_TWO); }

	"typedef"	{System.out.print("typedef"); return tok(TYPEDEF); }
	"void"	{System.out.print("void"); return tok(VOID); }
	"char"	{System.out.print("char"); return tok(CHAR); }
	"int"    {System.out.print("int"); return tok(INT); }
	"struct"	{System.out.print("struct"); return tok(STRUCT); }
	"union"	{System.out.print("union"); return tok(UNION); }
	"if"     {System.out.print("if"); return tok(IF); }
	"else"	{System.out.print("else"); return tok(ELSE); }
	"while" {System.out.print("while"); return tok(WHILE); }
	"for"	{System.out.print("for"); return tok(FOR); }
	"continue"	{System.out.print("continue"); return tok(CONTINUE); }
	"break"	{System.out.print("break"); return tok(BREAK); }
	"return" {System.out.print("return"); return tok(RETURN); }
	"sizeof"	{System.out.print("sizeof"); return tok(SIZEOF); }

	"(" { System.out.print("("); return tok(LPAREN); }
	")" { System.out.print(")"); return tok(RPAREN); }
	"{" { System.out.print("{"); return tok(LBRACE); }
	"}" { System.out.print("}"); return tok(RBRACE); }
	"[" { System.out.print("["); return tok(LSQBRA); }
	"]" { System.out.print("]"); return tok(RSQBRA); }

	"." { System.out.print("."); return tok(DOT); }
	"," { System.out.print(","); return tok(COMMA); }
	";" { System.out.print(";"); return tok(SEMICOLON); }

	"~" { System.out.print("~"); return tok(NEGATE); }
	"!" { System.out.print("!"); return tok(NOT); }

	"+" { System.out.print("+"); return tok(PLUS); }
	"-" { System.out.print("-"); return tok(MINUS); }
	"*" { System.out.print("*"); return tok(TIMES); }
	"/" { System.out.print("/"); return tok(DIVIDE); }
	"%" { System.out.print("%"); return tok(MOD); }
	"|" { System.out.print("|"); return tok(OPOR); }
	"^" { System.out.print("^"); return tok(OPXOR); }
	"&" { System.out.print("&"); return tok(OPAND); }

	"||" { System.out.print("||"); return tok(OR); }
	"&&" { System.out.print("&&"); return tok(AND); }
	"==" { System.out.print("=="); return tok(EQ); }
	"!=" { System.out.print("!="); return tok(NE) ;}
	"<"  { System.out.print("<"); return tok(LT); }
	"<=" { System.out.print("<="); return tok(LE); }
	">"  { System.out.print(">"); return tok(GT); }
	">=" { System.out.print(">="); return tok(GE); }
	"<<" { System.out.print("<<"); return tok(SHL); }
	">>" { System.out.print(">>"); return tok(SHR); }
	"++" { System.out.print("++"); return tok(INC); }
	"--" { System.out.print("--"); return tok(DEC); }
	"->" { System.out.print("->"); return tok(PTR); }
	"..." { System.out.print("..."); return tok(ELLIPSIS); }

	"=" { System.out.print("="); return tok(ASSIGN); }
	"*=" { System.out.print("*="); return tok(MUL_ASSIGN); }
	"/=" { System.out.print("/="); return tok(DIV_ASSIGN); }
	"%=" { System.out.print("%="); return tok(MOD_ASSIGN); }
	"+=" { System.out.print("+="); return tok(ADD_ASSIGN); }
	"-=" { System.out.print("-="); return tok(SUB_ASSIGN); }
	"<<=" { System.out.print("<<="); return tok(SHL_ASSIGN); }
	">>=" { System.out.print(">>="); return tok(SHR_ASSIGN); }
	"&=" { System.out.print("&="); return tok(AND_ASSIGN); }
	"^=" { System.out.print("^="); return tok(XOR_ASSIGN); }
	"|=" { System.out.print("|="); return tok(OR_ASSIGN); }

/*	{Perprocess} {System.out.println("<PESPROCESS, "+ yytext() +">"); } */
	{Identifier} { System.out.print("<ID, "+ yytext() +">"); return tok(ID, yytext()); }
	{DecInteger} { System.out.print("<DecInteger, "+ yytext() +">"); return tok(NUM, new Integer(yytext())); }
	{OctInteger} {
		System.out.print("<OctInteger, "+ yytext() +">");
		int x = 0;
		String st = yytext();
		for (int i = 1; i < st.length(); ++i)
			x = x * 8 + (st.charAt(i) - '0');
	 	return tok(NUM, new Integer(x));
	 	}
	{HexInteger} {
		System.out.print("<HexInteger, "+ yytext() +">");
		int x = 0;
		String st = yytext();
		for (int i = 2; i < st.length(); ++i) {
			int t = 0;
			if ('0' <= st.charAt(i) && st.charAt(i) <= '9')
				t = st.charAt(i) - '0';
			else
				if ('A' <= st.charAt(i) && st.charAt(i) <= 'F')
					t = st.charAt(i) - 'A' + 10;
				else
					t = st.charAt(i) - 'a' + 10;
			x = x * 16 + t;
			}
	 	return tok(NUM, new Integer(x));
		}
	{Whitespace} { System.out.print(yytext()); }

	\"		{ string = ""; yybegin(STRING); }

	{Char}	{System.out.print("<Char, "+ yytext() +">"); return tok(Char, yytext()); }

	\#	{yybegin(PRE); }

	[^] { throw new RuntimeException("Illegal character " + yytext() + " in line " + (yyline + 1) + ", column " + (yycolumn + 1)); }
}

<PRE> {
	\n { yybegin(YYINITIAL); }
	[^] { }
}

<STRING> {
  \"                             { yybegin(YYINITIAL);
  								   System.out.print("<String, "+ string +">");
                                   return tok(String, string); }
  [^\n\r\"\\]+                   { string += yytext(); }
  \\t                            { string += "\\t"; }
  \\n                            { string += "\\n"; }

  \\r                            { string += "\\r"; }
  \\\"                           { string += "\\\""; }
  \\                             { string += "\\"; }
}

<YYCOMMENT> {
	"*/"	{ yybegin(YYINITIAL); }
	[^]  {}
}

<YYCOMMENT_TWO> {
	[\n]  { yybegin(YYINITIAL); }
	[^] {}
}
