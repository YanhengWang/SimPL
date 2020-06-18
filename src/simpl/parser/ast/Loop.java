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
        //New constraints: {tl=Bool, tr=Unit}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3, s4, s5;

        resultL = e1.typecheck(E);
        s1 = resultL.s;
        s2 = resultL.t.unify(Type.BOOL);    //tl=BOOL
        s3 = s2.compose(s1);    //s2(s1(.))

        resultR = e2.typecheck(TypeEnv.embody(E,s3));
        s4 = resultR.s;
        s5 = resultR.t.unify(Type.UNIT);
        return TypeResult.of(
                s5.compose(s4.compose(s3)), Type.UNIT
        );
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
