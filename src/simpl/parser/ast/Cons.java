package simpl.parser.ast;

import simpl.interpreter.ConsValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
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
        // TODO
        return null;
    }
}
