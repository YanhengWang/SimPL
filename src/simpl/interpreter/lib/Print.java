package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.*;

public class Print extends FunValue {
    private static final Symbol sym = Symbol.symbol("x");

    private Print(Expr body){
        super(Env.empty, sym, body);
    }

    public static final Print print = new Print(
            new Expr() {
                @Override
                public TypeResult typecheck(TypeEnv E) {
                    TypeVar X = new TypeVar(true);
                    ArrowType t = new ArrowType(X, Type.UNIT);
                    return TypeResult.of(new PolyType(t, E));
                }

                @Override
                public Value eval(State s) {
                    Value v = s.E.get(sym);
                    System.out.println(v);
                    return UnitValue.UNIT;
                }
            }
    );
}
