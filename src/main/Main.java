package main;

import java.io.*;
import java.util.*;

import ast.Program;
import codegen.Codegen;
import syntactic.Parser;
import Semantic.Semant;
import syntactic.IDPrint;
import translate.Translate;

public class Main {
	
	public static IDPrint idPrint = new IDPrint();

	public static String pathOf(String filename) {
		return Main.class.getResource(filename).getPath();
	}

	private static void compile(String filename) throws IOException {
		
		//========================Parsing
		System.out.println("====================\nParsing");
		InputStream inp = new FileInputStream(pathOf(filename));
		System.out.println(filename);
		Parser parser = new Parser(inp);
		java_cup.runtime.Symbol parseTree = null;
		try {
			parseTree = parser.parse();
		} catch (Throwable e) {
			System.exit(1);
			e.printStackTrace();
			throw new Error(e.toString());
		} finally {
			inp.close();
		}
		idPrint.toprint();
		Program program = (Program) parseTree.value;
		System.out.println("OK!");
		
		//======================Semantics
		System.out.println("====================\nSemantics Checking");
		Semant semant = new Semant();
		semant.checkProg(program);
		if (semant.hasError()) {
			semant.printErrors();
			System.exit(1);
			return;
		} else {
			System.out.println("OK!");
		}
		
		//======================translator
		System.out.println("====================\nTranslating");
		Translate translate = new Translate();
		translate.transprog(program);
		translate.listallCode();
		System.out.println("OK!");
		
		//======================Codegen
		System.out.println("====================\nCodegen");
		PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(filename + ".s")));
		Codegen codegen = new Codegen();
		codegen.gen(translate);
		//System.out.println(codegen.tostring());
		out.println(codegen.tostring());
		
		out.println("########################################");
		out.println("############### CODE GEN ###############");
		out.println("########################################");
		
		Scanner scanner = new Scanner(new BufferedInputStream(Main.class.getResourceAsStream("printf.txt")));
		while (scanner.hasNextLine()) {
			out.println(scanner.nextLine());
		}
		scanner.close();
		out.close();
	}

	public static void main(String argv[]) throws IOException {
		//compile(pathOf("test.c"));
		//compile("factor.c");
		compile("a.c");
		System.exit(0);
	}
}
