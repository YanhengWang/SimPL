package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type t1 = l.typecheck(E).t;
        Type t2 = r.typecheck(E).t;
        if(t1 instanceof RefType){
            Type t3 = ((RefType)t1).t;  //retrieve the type being referenced
            if(t3.toString().equals(t2.toString())) {
                return TypeResult.of(Type.UNIT);
            }else{
                throw new TypeError("Assigning incompatible types");
            }
        }else{
            throw new TypeError("Dereference non-pointer");
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RefValue v1 = (RefValue) l.eval(s);
        int location = v1.p;
        Value v2 = r.eval(s);
        s.M.put(location, v2);
        return UnitValue.UNIT;
    }
}
