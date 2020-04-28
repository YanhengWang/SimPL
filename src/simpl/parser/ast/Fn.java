package simpl.parser.ast;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(λ" + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeVar X = new TypeVar(true);
        TypeResult result = e.typecheck(TypeEnv.of(E, x, X));
        return TypeResult.of(result.s, new ArrowType(result.s.apply(X), result.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new FunValue(s.E, x, e);
    }
}
