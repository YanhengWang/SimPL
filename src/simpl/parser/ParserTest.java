package simpl.parser;

import java.io.FileInputStream;
import java.io.InputStream;

public class ParserTest {

	private static void parse(String filename) {
		try (InputStream inp = new FileInputStream(filename)) {
			Parser parser = new Parser(inp);
			java_cup.runtime.Symbol parseTree = parser.parse();
			System.out.println(filename);
			System.out.println(parseTree.value);
			System.out.println(parseTree.value.getClass());
		}
		catch (Exception e) {
			System.out.println("syntax error");
		}
	}

	public static void main(String[] argv) {
		parse("examples/stream.spl");
		/*parse("examples/plus.spl");
		parse("examples/factorial.spl");
		parse("examples/gcd1.spl");
		parse("examples/gcd2.spl");
		parse("examples/max.spl");
		parse("examples/sum.spl");
		parse("examples/mutualRec.spl");
		parse("examples/pcf.sum.spl");
		parse("examples/pcf.even.spl");
		parse("examples/pcf.twice.spl");
		parse("examples/pcf.minus.spl");
		parse("examples/pcf.factorial.spl");
		parse("examples/pcf.fibonacci.spl");
		parse("examples/append.spl");*/
	}
}
