package syntactic;

import java.io.*;

import main.Main;

import com.google.gson.Gson;

final class ParserTest {

	private static void parse(String filename) throws IOException {
		InputStream inp = new FileInputStream(filename);
		Parser parser = new Parser(inp);
		java_cup.runtime.Symbol parseTree = null;
		try {
			parseTree = parser.parse();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Error(e.toString());
		} finally {
			inp.close();
		}
		Gson gson = new Gson();
		System.out.println(gson.toJson(parseTree.value));
	}

	public static void main(String argv[]) throws IOException {
		parse(Main.pathOf("test.c"));
	}
}
