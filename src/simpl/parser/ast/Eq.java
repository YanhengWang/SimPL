package simpl.parser.ast;

import simpl.interpreter.*;

public class Eq extends EqExpr {

    public Eq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " = " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v1 = l.eval(s);
        Value v2 = r.eval(s);
        return v1.equals(v2) ? BoolValue.TRUE : BoolValue.FALSE;
    }
}
