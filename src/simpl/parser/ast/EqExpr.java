package simpl.parser.ast;

import simpl.typing.ListType;
import simpl.typing.PairType;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //Constraints: tl & tr are equality types; {tl=tr}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3;

        resultL = l.typecheck(E);
        if(!resultL.t.isEqualityType())
            throw new TypeError("Objects incomparable by =");
        s1 = resultL.s;

        resultR = r.typecheck(TypeEnv.embody(E,s1));
        if(!resultR.t.isEqualityType())
            throw new TypeError("Objects incomparable by =");
        s2 = resultR.s;
        s3 = resultR.t.unify(s2.apply(resultL.t));    //tl=tr
        return TypeResult.of(
                s3.compose(s2.compose(s1)), Type.BOOL
        );
    }
}
