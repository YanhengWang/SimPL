package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class IsZero extends Fn {
    public static final IsZero iszero = new IsZero();

    private IsZero(){
        super(Symbol.symbol("x"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                IntValue v = (IntValue) s.E.get(x);
                return v.n==0 ? BoolValue.TRUE : BoolValue.FALSE;
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        ArrowType t = new ArrowType(Type.INT, Type.BOOL);
        return TypeResult.of(t);
    }
}
