package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        if(e1.typecheck(E).t == Type.BOOL) {
            return TypeResult.of(e2.typecheck(E).t);
        }else{
            throw new TypeError("Type of condition is not bool");
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue v = (BoolValue) e1.eval(s);
        while(v.b){
            e2.eval(s);
            v = (BoolValue) e1.eval(s);
        }
        return UnitValue.UNIT;
    }
}
