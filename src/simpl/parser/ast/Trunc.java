package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

public class Trunc extends BinaryExpr {

    public Trunc(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + "[" + r + "]";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraints: {tl=STREAM(X), tr=INT}
        TypeResult resultL, resultR;
        StreamType streamType = new StreamType(new TypeVar(true));
        Substitution s1, s2, s3, s4, s5, s6;

        resultL = l.typecheck(E);
        s1 = resultL.s;
        s2 = resultL.t.unify(streamType);    //tl=STREAM(X)
        s3 = s2.compose(s1);    //s2(s1(.))

        resultR = r.typecheck(TypeEnv.embody(E,s3));
        s4 = resultR.s;
        s5 = resultR.t.unify(Type.INT);    //tr=INT
        s6 = s5.compose(s4.compose(s3));
        return TypeResult.of(
                s6, s6.apply(streamType)
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        StreamValue v1 = (StreamValue) l.eval(s);
        IntValue v2 = (IntValue) r.eval(s);
        StreamValue ret = v1.clone();
        ret.truncate(v2.n);
        return ret;
    }
}
