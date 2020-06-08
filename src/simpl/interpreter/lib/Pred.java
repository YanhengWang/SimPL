package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Pred extends Fn {
    public static final Pred pred = new Pred();

    private Pred(){
        super(Symbol.symbol("x"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                IntValue v = (IntValue) s.E.get(x);
                if(v.n == 0)
                    return new IntValue(0);
                else
                    return new IntValue(v.n-1);
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        ArrowType t = new ArrowType(Type.INT, Type.INT);
        return TypeResult.of(t);
    }
}
