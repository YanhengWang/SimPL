package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {t=BOOL}
        TypeResult result;
        Substitution s1, s2;

        result = e.typecheck(E);
        s1 = result.s;
        s2 = result.t.unify(Type.BOOL);    //t=BOOL
        return TypeResult.of(s2.compose(s1), Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue v = (BoolValue) e.eval(s);
        return v.b ? BoolValue.FALSE : BoolValue.TRUE;
    }
}
