package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Tl extends Fn {
    public static final Tl tl = new Tl();

    private Tl(){
        super(Symbol.symbol("list"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) throws RuntimeError {
                Value l = s.E.get(x);
                if(l instanceof NilValue)
                    throw new RuntimeError("Taking tail of nil value");
                return ((ConsValue)l).v2;
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        TypeVar T = new TypeVar(true);
        ListType listType = new ListType(T);
        ArrowType t = new ArrowType(listType, listType);
        return TypeResult.of(new PolyType(t, E));
    }
}
