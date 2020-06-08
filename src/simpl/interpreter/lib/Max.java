package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Max extends Fn {
    public static final Max max = new Max();

    private Max(){
        super(Symbol.symbol("list"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                Value l = s.E.get(x);
                int maximum = 0;

                for(; !(l instanceof NilValue); l=((ConsValue)l).v2) {
                    ConsValue cons = (ConsValue) l;
                    maximum = Math.max(((IntValue)cons.v1).n, maximum);
                }
                return new IntValue(maximum);
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        ArrowType t = new ArrowType(new ListType(Type.INT), Type.INT);
        return TypeResult.of(t);
    }
}
