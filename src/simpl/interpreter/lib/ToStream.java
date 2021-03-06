package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class ToStream extends Fn {
    public static final ToStream toStream = new ToStream();

    private ToStream(){
        super(Symbol.symbol("x"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                Value list = s.E.get(x);
                return new StreamValue(list);
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        TypeVar X = new TypeVar(true);
        ArrowType t = new ArrowType(new ListType(X), new StreamType(X));
        return TypeResult.of(new PolyType(t, E));
    }
}
