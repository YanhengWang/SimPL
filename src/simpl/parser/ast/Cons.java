package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type t1 = l.typecheck(E).t;
        Type t2 = r.typecheck(E).t;
        if(t2 instanceof ListType){
            Type t3 = ((ListType) t2).t;
            if(t1.toString().equals(t3.toString()))
                return TypeResult.of(t2);
            else
                throw new TypeError("Incompatible types among list elements");
        }else{
            throw new TypeError("List construction illegal");
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v1 = l.eval(s);
        Value v2 = r.eval(s);
        return new ConsValue(v1, v2);
    }
}
