package simpl.interpreter.pcf;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class IsZero extends FunValue {
    private static final Symbol sym = Symbol.symbol("n");

    private IsZero(Expr body){
        super(Env.empty, sym, body);
    }

    public static final IsZero iszero = new IsZero(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) throws TypeError {
                    ArrowType t = new ArrowType(Type.INT, Type.BOOL);
                    return TypeResult.of(t);
                }

                @Override
                public Value eval(State s) throws RuntimeError {
                    IntValue v = (IntValue) s.E.get(sym);
                    return v.n==0 ? BoolValue.TRUE : BoolValue.FALSE;
                }
            }
    );
}
