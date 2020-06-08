package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Hd extends Fn {
    public static final Hd hd = new Hd();

    private Hd(){
        super(Symbol.symbol("list"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) throws RuntimeError {
                Value l = s.E.get(x);
                if(l instanceof NilValue)
                    throw new RuntimeError("Taking head of nil value");
                return ((ConsValue)l).v1;
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        TypeVar T = new TypeVar(true);
        ListType listType = new ListType(T);
        ArrowType t = new ArrowType(listType, T);
        return TypeResult.of(new PolyType(t, E));
    }
}
