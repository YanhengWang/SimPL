package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult resultL = l.typecheck(E);
        TypeResult resultR = r.typecheck(TypeEnv.embody(E, resultL.s));
        return TypeResult.of(
                resultR.s.compose(resultL.s),
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
