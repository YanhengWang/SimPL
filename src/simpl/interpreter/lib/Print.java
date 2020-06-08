package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.parser.ast.Fn;
import simpl.typing.*;

public class Print extends Fn {
    public static final Print print = new Print();

    private Print(){
        super(Symbol.symbol("x"), null);
        e = new Expr() {
            @Override
            public TypeResult typecheck(TypeEnv E) { return null; }

            @Override
            public Value eval(State s) {
                Value v = s.E.get(x);
                System.out.println(v);
                return UnitValue.UNIT;
            }
        };
    };

    @Override
    public TypeResult typecheck(TypeEnv E) {
        TypeVar X = new TypeVar(true);
        ArrowType t = new ArrowType(X, Type.UNIT);
        return TypeResult.of(new PolyType(t, E));
    }
}
