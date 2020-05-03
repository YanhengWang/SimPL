package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type t = E.get(x);
        if(t == null)
            throw new TypeError("Symbol " + x + " is free");
        if(t instanceof PolyType)
            return TypeResult.of(((PolyType) t).instantiate());    //instantiate according to type scheme
        return TypeResult.of(t);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v = s.E.get(x);
        if(v instanceof RecValue) {
            RecValue recValue = (RecValue) v;
            Rec rec = new Rec(recValue.x, recValue.e);
            return rec.eval(State.of(recValue.E, s.M, s.p));  //run it recursively
        }
        return v;
    }
}
