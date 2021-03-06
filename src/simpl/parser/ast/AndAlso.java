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

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraints: {tl=BOOL, tr=BOOL}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3, s4, s5;

        resultL = l.typecheck(E);
        s1 = resultL.s;
        s2 = resultL.t.unify(Type.BOOL);    //tl=BOOL
        s3 = s2.compose(s1);    //s2(s1(.))

        resultR = r.typecheck(TypeEnv.embody(E,s3));
        s4 = resultR.s;
        s5 = resultR.t.unify(Type.BOOL);    //tr=BOOL
        return TypeResult.of(
                s5.compose(s4.compose(s3)), Type.BOOL
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue v1 = (BoolValue) l.eval(s);
        if(!v1.b)
            return BoolValue.FALSE;    //short circuit
        return r.eval(s);
    }
}
