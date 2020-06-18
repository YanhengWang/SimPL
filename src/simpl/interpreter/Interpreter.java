package simpl.interpreter;

import java.io.FileInputStream;
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

			program.typecheck(initialTypeEnv);
			Value value = program.eval(state);
			if(value instanceof RefValue){
				System.out.println("ref@" + state.M.get(((RefValue) value).p));
			}else if(value instanceof ConsValue){
				int length = 0;
				for(Value v=value; v instanceof ConsValue; v=((ConsValue)v).v2)
					length++;
				System.out.println("list@" + length);
			}else{
				System.out.println(value);
			}
		//	System.out.println(program.typecheck(initialTypeEnv).t);
		//	System.out.println(program.eval(state));
		} catch (TypeError e) {
			System.out.println("type error");
		//	System.out.println(e.getMessage());
		} catch (RuntimeError e) {
			System.out.println("runtime error");
		} catch (Exception e){
			System.out.println("syntax error");
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
	/*	interpret("examples/list.spl");
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
		interpret("examples/polymorphism.spl");
		interpret("examples/mutualRec.spl");
		interpret("examples/tailRec.spl");
		interpret("examples/gc.spl");
		interpret("examples/stream.spl");*/
	}
}
