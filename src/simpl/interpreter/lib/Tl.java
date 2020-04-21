package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Tl extends FunValue {
    private static final Symbol sym = Symbol.symbol("list");

    private Tl(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Tl tl = new Tl(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) throws TypeError {
                    ArrowType t = new ArrowType(new PairType(Type.INT, Type.INT), Type.INT);
                    return TypeResult.of(t);
                    //TODO: support variant types
                }

                @Override
                public Value eval(State s) throws RuntimeError {
                    Value l = s.E.get(sym);
                    if(l instanceof NilValue)
                        throw new RuntimeError("Taking tail of nil value");
                    return ((ConsValue)l).v2;
                }
            }
    );
}
