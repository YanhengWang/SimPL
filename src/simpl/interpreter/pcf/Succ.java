package simpl.interpreter.pcf;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Succ extends FunValue {
    private static final Symbol sym = Symbol.symbol("n");

    private Succ(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Succ succ = new Succ(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) {
                    ArrowType t = new ArrowType(Type.INT, Type.INT);
                    return TypeResult.of(t);
                }

                @Override
                public Value eval(State s) {
                    IntValue v = (IntValue) s.E.get(sym);
                    return new IntValue(v.n+1);
                }
            }
    );
}
