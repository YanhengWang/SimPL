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

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult t1 = l.typecheck(E);
        TypeResult t2 = r.typecheck(E);
        if(t1.t != Type.BOOL || t2.t != Type.BOOL)
            throw new TypeError("Operands of andAlso are not booleans");
        return TypeResult.of(Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        BoolValue v1 = (BoolValue) l.eval(s);
        BoolValue v2 = (BoolValue) r.eval(s);
        return (v1.b && v2.b) ? BoolValue.TRUE : BoolValue.FALSE;
    }
}
