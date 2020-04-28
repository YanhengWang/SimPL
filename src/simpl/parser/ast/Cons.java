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
        //New constraint: {tr=LIST(tl)}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3, s4;

        resultL = l.typecheck(E);
        s1 = resultL.s;

        resultR = r.typecheck(TypeEnv.embody(E,s1));
        s2 = resultR.s;
        s3 = resultR.t.unify(new ListType(resultL.t));    //tr=LIST(tl)
        s4 = s3.compose(s2.compose(s1));
        return TypeResult.of(
                s4, s4.apply(resultR.t)
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v1 = l.eval(s);
        Value v2 = r.eval(s);
        return new ConsValue(v1, v2);
    }
}
