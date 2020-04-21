package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Hd extends FunValue {
    private static final Symbol sym = Symbol.symbol("list");

    private Hd(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Hd hd = new Hd(
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
                        throw new RuntimeError("Taking head of nil value");
                    return ((ConsValue)l).v1;
                }
            }
    );
}
