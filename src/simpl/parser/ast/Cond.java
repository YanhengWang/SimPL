package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraints: {tl=BOOL, tm=tr}
        TypeResult resultL, resultM, resultR;
        Substitution s1, s2, s3, s4, s5;

        resultL = e1.typecheck(E);
        s1 = resultL.t.unify(Type.BOOL);    //tl=BOOL
        s2 = s1.compose(resultL.s);    //s2(s1(.))

        resultM = e2.typecheck(TypeEnv.embody(E,s2));
        s3 = resultM.s.compose(s2);
        resultR = e3.typecheck(TypeEnv.embody(E,s3));
        s4 = resultR.s.compose(s3);
        s5 = resultR.t.unify(s4.apply(resultM.t));    //tr=tm
        return TypeResult.of(
                s5.compose(s4), s5.apply(resultR.t)
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue v = (BoolValue) e1.eval(s);
        if(v.b){
            return e2.eval(s);
        }else{
            return e3.eval(s);
        }
    }
}
