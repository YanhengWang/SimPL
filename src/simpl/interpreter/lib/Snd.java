package simpl.interpreter.lib;

import simpl.interpreter.PairValue;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Snd extends Fn {
    public static final Snd snd = new Snd();

    private Snd(){
        super(Symbol.symbol("pair"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                PairValue p = (PairValue) s.E.get(x);
                return p.v2;
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        TypeVar T1 = new TypeVar(true);
        TypeVar T2 = new TypeVar(true);
        ArrowType t = new ArrowType(new PairType(T1, T2), T2);
        return TypeResult.of(new PolyType(t, E));
    }
}
