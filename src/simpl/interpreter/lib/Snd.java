package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Snd extends FunValue {

    private static final Symbol sym = Symbol.symbol("pair");

    private Snd(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Snd snd = new Snd(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) throws TypeError {
                    ArrowType t = new ArrowType(new PairType(Type.INT, Type.INT), Type.INT);
                    return TypeResult.of(t);
                    //TODO: support variant types
                }

                @Override
                public Value eval(State s) throws RuntimeError {
                    PairValue p = (PairValue) s.E.get(sym);
                    return p.v2;
                }
            }
    );
}
