package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Fst extends FunValue {
    private static final Symbol sym = Symbol.symbol("pair");

    private Fst(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Fst fst = new Fst(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) {
                    TypeVar T1 = new TypeVar(true);
                    TypeVar T2 = new TypeVar(true);
                    ArrowType t = new ArrowType(new PairType(T1, T2), T1);
                    return TypeResult.of(t);
                }

                @Override
                public Value eval(State s) {
                    PairValue p = (PairValue) s.E.get(sym);
                    return p.v1;
                }
            }
    );
}
