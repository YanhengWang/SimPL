package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Tl extends FunValue {
    private static final Symbol sym = Symbol.symbol("list");

    private Tl(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Tl tl = new Tl(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) {
                    TypeVar T = new TypeVar(true);
                    ListType listType = new ListType(T);
                    ArrowType t = new ArrowType(listType, listType);
                    return TypeResult.of(new PolyType(t, E));
                }

                @Override
                public Value eval(State s) throws RuntimeError {
                    Value l = s.E.get(sym);
                    if(l instanceof NilValue)
                        throw new RuntimeError("Taking tail of nil value");
                    return ((ConsValue)l).v2;
                }
            }
    );
}
