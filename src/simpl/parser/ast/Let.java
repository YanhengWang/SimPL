package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.*;

public class Let extends Expr {

    public Symbol x;
    public Expr e1, e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult resultL;
        TypeResult resultR;
        PolyType polyType;
        Substitution s1, s2;

        resultL = e1.typecheck(E);
        s1 = resultL.s;
        if(resultL.t instanceof RefType){
            //don't create let-polymorphism
            resultR = e2.typecheck(TypeEnv.embody(TypeEnv.of(E,x,resultL.t), s1));
        } else {
            //create let-polymorphism
            polyType = new PolyType(resultL.t, E);
            resultR = e2.typecheck(TypeEnv.embody(TypeEnv.of(E, x, polyType), s1));
        }
        s2 = resultR.s;
        return TypeResult.of(s2.compose(s1), resultR.t);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v = e1.eval(s);
        return e2.eval(State.of(new Env(s.E, x, v), s.M, s.p));
    }
}
