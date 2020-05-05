package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Hd extends FunValue {
    private static final Symbol sym = Symbol.symbol("list");

    private Hd(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Hd hd = new Hd(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) {
                    TypeVar T = new TypeVar(true);
                    ListType listType = new ListType(T);
                    ArrowType t = new ArrowType(listType, T);
                    return TypeResult.of(new PolyType(t, E));
                }

                @Override
                public Value eval(State s) throws RuntimeError {
                    Value l = s.E.get(sym);
                    if(l instanceof NilValue)
                        throw new RuntimeError("Taking head of nil value");
                    return ((ConsValue)l).v1;
                }
            }
    );
}
