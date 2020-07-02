package simpl.interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import simpl.parser.Parser;
import simpl.parser.ast.Expr;
import simpl.typing.DefaultTypeEnv;
import simpl.typing.TypeError;

public class Interpreter {

	public void run(String filename) {
		DefaultTypeEnv initialTypeEnv = new DefaultTypeEnv();
		State state = new InitialState();

		try (InputStream inp = new FileInputStream(filename)) {
			Parser parser = new Parser(inp);
			java_cup.runtime.Symbol parseTree = parser.parse();
			Expr program = (Expr) parseTree.value;

			System.out.println("Type: " + program.typecheck(initialTypeEnv).t);
			System.out.println("Value = " + program.eval(state));
		} catch (TypeError e) {
			System.out.println("Type error [" + e.getMessage() + "].");
		} catch (RuntimeError e) {
			System.out.println("Runtime error [" + e.getMessage() +"].");
		} catch (IOException e) {
			System.out.println("Source file not found.");
		} catch (Exception e){
			System.out.println("Syntax error.");
		}
	}

	private static void interpret(String filename) {
		Interpreter i = new Interpreter();
		i.run(filename);
	}

	public static void main(String[] args) {
		if(args.length > 0)
			interpret(args[0]);
		else
			System.out.println("Please provide a file name.");
	}
}
