package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class ToStream extends FunValue {
    private static final Symbol sym = Symbol.symbol("list");

    private ToStream(Expr body){
        super(Env.empty, sym, body);
    }

    public static final ToStream toStream = new ToStream(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) {
                    TypeVar X = new TypeVar(true);
                    ArrowType t = new ArrowType(new ListType(X), new StreamType(X));
                    return TypeResult.of(new PolyType(t, E));
                }

                @Override
                public Value eval(State s) {
                    Value list = s.E.get(sym);
                    return new StreamValue(list);
                }
            }
    );
}
