package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {t=REF(X)}
        TypeResult result = e.typecheck(E);
        TypeVar X = new TypeVar(true);
        Substitution s1, s2;

        s1 = result.t.unify(new RefType(X));    //t=REF(X)
        s2 = s1.compose(result.s);
        return TypeResult.of(s2, s2.apply(X));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        RefValue v = (RefValue) e.eval(s);
        return s.M.get(v.p);
    }
}
