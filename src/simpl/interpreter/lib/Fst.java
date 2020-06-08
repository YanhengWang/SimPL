package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Fst extends Fn {
    public static final Fst fst = new Fst();

    private Fst(){
        super(Symbol.symbol("pair"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                PairValue p = (PairValue) s.E.get(x);
                return p.v1;
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        TypeVar T1 = new TypeVar(true);
        TypeVar T2 = new TypeVar(true);
        ArrowType t = new ArrowType(new PairType(T1, T2), T1);
        return TypeResult.of(new PolyType(t, E));
    }
}
