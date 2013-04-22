package syntactic;
import java_cup.runtime.*;
import main.Main;

%%

%unicode
%line
%column
%cup
%implements Symbols

%{
	String string = new String();
	
	private void print(String st) {
		Main.idPrint.add(st);
	}

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

	"typedef"	{print("typedef"); return tok(TYPEDEF); }
	"void"	{print("void"); return tok(VOID); }
	"char"	{print("char"); return tok(CHAR); }
	"int"    {print("int"); return tok(INT); }
	"struct"	{print("struct"); return tok(STRUCT); }
	"union"	{print("union"); return tok(UNION); }
	"if"     {print("if"); return tok(IF); }
	"else"	{print("else"); return tok(ELSE); }
	"while" {print("while"); return tok(WHILE); }
	"for"	{print("for"); return tok(FOR); }
	"continue"	{print("continue"); return tok(CONTINUE); }
	"break"	{print("break"); return tok(BREAK); }
	"return" {print("return"); return tok(RETURN); }
	"sizeof"	{print("sizeof"); return tok(SIZEOF); }

	"(" { print("("); return tok(LPAREN); }
	")" { print(")"); return tok(RPAREN); }
	"{" { print("{"); return tok(LBRACE); }
	"}" { print("}"); return tok(RBRACE); }
	"[" { print("["); return tok(LSQBRA); }
	"]" { print("]"); return tok(RSQBRA); }

	"." { print("."); return tok(DOT); }
	"," { print(","); return tok(COMMA); }
	";" { print(";"); return tok(SEMICOLON); }

	"~" { print("~"); return tok(NEGATE); }
	"!" { print("!"); return tok(NOT); }

	"+" { print("+"); return tok(PLUS); }
	"-" { print("-"); return tok(MINUS); }
	"*" { print("*"); return tok(TIMES); }
	"/" { print("/"); return tok(DIVIDE); }
	"%" { print("%"); return tok(MOD); }
	"|" { print("|"); return tok(OPOR); }
	"^" { print("^"); return tok(OPXOR); }
	"&" { print("&"); return tok(OPAND); }

	"||" { print("||"); return tok(OR); }
	"&&" { print("&&"); return tok(AND); }
	"==" { print("=="); return tok(EQ); }
	"!=" { print("!="); return tok(NE) ;}
	"<"  { print("<"); return tok(LT); }
	"<=" { print("<="); return tok(LE); }
	">"  { print(">"); return tok(GT); }
	">=" { print(">="); return tok(GE); }
	"<<" { print("<<"); return tok(SHL); }
	">>" { print(">>"); return tok(SHR); }
	"++" { print("++"); return tok(INC); }
	"--" { print("--"); return tok(DEC); }
	"->" { print("->"); return tok(PTR); }
	"..." { print("..."); return tok(ELLIPSIS); }

	"=" { print("="); return tok(ASSIGN); }
	"*=" { print("*="); return tok(MUL_ASSIGN); }
	"/=" { print("/="); return tok(DIV_ASSIGN); }
	"%=" { print("%="); return tok(MOD_ASSIGN); }
	"+=" { print("+="); return tok(ADD_ASSIGN); }
	"-=" { print("-="); return tok(SUB_ASSIGN); }
	"<<=" { print("<<="); return tok(SHL_ASSIGN); }
	">>=" { print(">>="); return tok(SHR_ASSIGN); }
	"&=" { print("&="); return tok(AND_ASSIGN); }
	"^=" { print("^="); return tok(XOR_ASSIGN); }
	"|=" { print("|="); return tok(OR_ASSIGN); }

/*	{Perprocess} {println("<PESPROCESS, "+ yytext() +">"); } */
	{Identifier} { print("<ID, "+ yytext() +">"); return tok(ID, yytext()); }
	{DecInteger} { print("<DecInteger, "+ yytext() +">"); return tok(NUM, new Integer(yytext())); }
	{OctInteger} {
		print("<OctInteger, "+ yytext() +">");
		int x = 0;
		String st = yytext();
		for (int i = 1; i < st.length(); ++i)
			x = x * 8 + (st.charAt(i) - '0');
	 	return tok(NUM, new Integer(x));
	 	}
	{HexInteger} {
		print("<HexInteger, "+ yytext() +">");
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
	{Whitespace} { print(yytext()); }

	\"		{ string = ""; yybegin(STRING); }

	{Char}	{print("<Char, "+ yytext() +">"); return tok(Char, yytext()); }

	\#	{yybegin(PRE); }

	[^] { throw new RuntimeException("Illegal character " + yytext() + " in line " + (yyline + 1) + ", column " + (yycolumn + 1)); }
}

<PRE> {
	\n { yybegin(YYINITIAL); }
	[^] { }
}

<STRING> {
  \"                             { yybegin(YYINITIAL);
  								   print("<String, "+ string +">");
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
