package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;
import sun.jvm.hotspot.debugger.cdbg.IntType;

public class Sum extends Fn {
    public static final Sum sum = new Sum();

    private Sum(){
        super(Symbol.symbol("list"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                Value l = s.E.get(x);
                int summation = 0;

                for(; !(l instanceof NilValue); l=((ConsValue)l).v2) {
                    ConsValue cons = (ConsValue) l;
                    summation += ((IntValue)cons.v1).n;
                }
                return new IntValue(summation);
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        ArrowType t = new ArrowType(new ListType(Type.INT), Type.INT);
        return TypeResult.of(t);
    }
}
