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
        //New constraints: {tl=REF(tr)}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3;

        resultL = l.typecheck(E);
        s1 = resultL.s;

        resultR = r.typecheck(TypeEnv.embody(E,s1));
        s2 = resultR.s;
        s3 = s2.apply(resultL.t).unify(new RefType(resultR.t));    //tl=REF(tr)
        return TypeResult.of(
                s3.compose(s2.compose(s1)), Type.UNIT
        );
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
