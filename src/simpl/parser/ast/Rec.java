package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Rec extends Expr {

    public Symbol x;
    public Expr e;

    public Rec(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(rec " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {t=X}
        TypeVar X = new TypeVar(true);
        TypeResult result = e.typecheck(TypeEnv.of(E, x, X));
        Substitution s = result.t.unify(result.s.apply(X));    //t=X
        Substitution ret = s.compose(result.s);
        return TypeResult.of(ret, ret.apply(X));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RecValue recValue = new RecValue(s.E, x, e);  //snapshot
        return e.eval(State.of(new Env(s.E, x, recValue), s.M, s.p));  //go inside the body
    }
}
