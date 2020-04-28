package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {tl=tr->Y}
        TypeResult resultL;
        TypeResult resultR;
        TypeVar Y = new TypeVar(true);
        Substitution s1, s2, s3, s4;

        resultL = l.typecheck(E);
        s1 = resultL.s;

        resultR = r.typecheck(TypeEnv.embody(E,s1));
        s2 = resultR.s;
        s3 = s2.apply(resultL.t).unify(new ArrowType(resultR.t, Y));
        s4 = s3.compose(s2.compose(s1));
        return TypeResult.of(s4, s4.apply(Y));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        FunValue f = (FunValue) l.eval(s);
        Value v = r.eval(s);
        return f.e.eval(
            State.of(new Env(f.E, f.x, v), s.M, s.p)
        );  //call the function body under f's environment, with f.x maps to v
    }
}
