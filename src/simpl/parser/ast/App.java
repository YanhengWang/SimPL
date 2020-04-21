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
        Type t1 = l.typecheck(E).t;
        Type t2 = r.typecheck(E).t;

        if(t1 instanceof ArrowType){
            ArrowType t3 = (ArrowType)t1;
            if(t3.t1.toString().equals(t2.toString())){
                return TypeResult.of(t3.t2);
            }else{
                throw new TypeError("Types of parameter and argument mismatch");
            }
        }else{
            throw new TypeError("Applying non-function");
        }
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
