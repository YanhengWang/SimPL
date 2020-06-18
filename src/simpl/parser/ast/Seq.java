package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraints: {tl=UNIT}
        TypeResult resultL, resultR;
        Substitution s1, s2, s3;

        resultL = l.typecheck(E);
        s1 = resultL.s;
        s2 = resultL.t.unify(Type.UNIT);    //tl=UNIT
        s3 = s2.compose(s1);    //s2(s1(.))

        resultR = r.typecheck(TypeEnv.embody(E,s3));
        return TypeResult.of(
                resultR.s.compose(s3),
                resultR.t
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        l.eval(s);
        return r.eval(s);
    }

    @Override
    public void markTails(Symbol symbol){
        r.markTails(symbol);
    }
}
