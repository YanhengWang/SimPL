package simpl.interpreter;

import java.io.FileInputStream;
import java.io.InputStream;

import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.Expr;
import simpl.typing.DefaultTypeEnv;
import simpl.typing.TypeError;

public class Interpreter {

	public void run(String filename) {
		DefaultTypeEnv initialTypeEnv = new DefaultTypeEnv();
		try (InputStream inp = new FileInputStream(filename)) {
			Parser parser = new Parser(inp);
			java_cup.runtime.Symbol parseTree = parser.parse();
			Expr program = (Expr) parseTree.value;
			System.out.println(program.typecheck(initialTypeEnv).t);
			System.out.println(program.eval(new InitialState()));
		}
		catch (TypeError e) {
			System.out.println("type error");
			System.out.println(e.getMessage());
		}
		catch (RuntimeError e) {
			System.out.println("runtime error");
		}
		catch (Exception e) {
			//System.out.println("syntax error");
			e.printStackTrace(System.err);
		}
	}

	private static void interpret(String filename) {
		Interpreter i = new Interpreter();
		System.out.println(filename);
		i.run(filename);
	}

	public static void main(String[] args) {
		interpret("examples/stream.spl");
		interpret("examples/gc.spl");
		interpret("examples/mutualRec.spl");
		interpret("examples/polymorphism.spl");
		interpret("examples/list.spl");
		interpret("examples/reference.spl");
		interpret("examples/plus.spl");
		interpret("examples/factorial.spl");
		interpret("examples/gcd1.spl");
		interpret("examples/gcd2.spl");
		interpret("examples/max.spl");
		interpret("examples/sum.spl");
		interpret("examples/map.spl");
		interpret("examples/pcf.sum.spl");
		interpret("examples/pcf.even.spl");
		interpret("examples/pcf.minus.spl");
		interpret("examples/pcf.factorial.spl");
		interpret("examples/pcf.fibonacci.spl");
		interpret("examples/pcf.twice.spl");
	}
}
