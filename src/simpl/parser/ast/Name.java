package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Name extends Expr {

    public Symbol x;
    public boolean isRecTail;

    public Name(Symbol x) {
        this.x = x;
        this.isRecTail = false;
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
            if(isRecTail)
                return new LazyAppValue(Env.empty, recValue.e);
            Rec rec = new Rec(recValue.x, recValue.e);    //"copy"
            return rec.eval(State.of(recValue.E, s.M, s.p));
        }else if(v instanceof UnitValue){
            ((UnitValue) v).takeAction(s);
        }
        return v;
    }

    @Override
    public void markTails(Symbol symbol){
        if(x == symbol)
            isRecTail = true;
    }
}
