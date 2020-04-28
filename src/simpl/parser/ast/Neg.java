package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {t=INT}
        TypeResult result;
        Substitution s1, s2;

        result = e.typecheck(E);
        s1 = result.s;
        s2 = result.t.unify(Type.INT);    //t=INT
        return TypeResult.of(s2.compose(s1), Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError{
        IntValue v1 = (IntValue) e.eval(s);
        return new IntValue(-v1.n);
    }
}
