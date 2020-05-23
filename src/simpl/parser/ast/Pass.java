package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

public class Pass extends BinaryExpr {

    public Pass(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " >> " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {tl=STREAM(X), tr=X->Y}
        TypeResult resultL, resultR;
        TypeVar X = new TypeVar(true);
        TypeVar Y = new TypeVar(true);
        Substitution s1, s2, s3, s4, s5, s6;

        resultL = l.typecheck(E);
        s1 = resultL.s;
        s2 = resultL.t.unify(new StreamType(X));    //tl=STREAM(X)
        s3 = s2.compose(s1);    //s2(s1(.))

        resultR = r.typecheck(TypeEnv.embody(E,s3));
        s4 = resultR.s;
        s5 = resultR.t.unify(new ArrowType(s3.apply(X),Y));    //tr=X->Y
        s6 = s5.compose(s4.compose(s3));
        return TypeResult.of(
                s6, new StreamType(s6.apply(Y))
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        StreamValue v1 = (StreamValue) l.eval(s);
        FunValue v2 = (FunValue) r.eval(s);
        StreamValue ret = v1.clone();
        ret.mountFunction(v2);
        return ret;
    }
}
