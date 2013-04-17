package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import ast.Program;
import codegen.AssemblyWriter;
import codegen.MipsAssemblyWriter;
import syntactic.Parser;
import Semantic.Semant;
//import appetizer.translate.Translator;

public class Main {

	public static String pathOf(String filename) {
		return Main.class.getResource(filename).getPath();
	}

	private static void compile(String filename) throws IOException {
		
		//========================Parsing
		System.out.println("Parsing");
		InputStream inp = new FileInputStream(filename);
		//System.out.println(filename);
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

		Program program = (Program) parseTree.value;
		
		//======================Semantics
		System.out.println("Semantics Checking");
		Semant semant = new Semant();
		semant.checkProg(program);
		if (semant.hasError()) {
			semant.printErrors();
			System.exit(1);
			return;
		} else {
			System.out.println("OK!");
		}
	}

	public static void main(String argv[]) throws IOException {
		//compile(pathOf("test.c"));
		compile(argv[0]);
		System.exit(0);
	}
}
