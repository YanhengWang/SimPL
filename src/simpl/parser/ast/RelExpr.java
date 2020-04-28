package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraints: {tl=INT, tr=INT}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3, s4, s5;

        resultL = l.typecheck(E);
        s1 = resultL.s;
        s2 = resultL.t.unify(Type.INT);    //tl=INT
        s3 = s2.compose(s1);    //s2(s1(.))

        resultR = r.typecheck(TypeEnv.embody(E,s3));
        s4 = resultR.s;
        s5 = resultR.t.unify(Type.INT);    //tr=INT
        return TypeResult.of(
                s5.compose(s4.compose(s3)), Type.BOOL
        );
    }
}
