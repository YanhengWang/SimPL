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
        TypeResult t1 = l.typecheck(E);
        TypeResult t2 = r.typecheck(E);
        ArrowType tabs;

        if(t1.t instanceof ArrowType){
            tabs = (ArrowType)t1.t;
            if(tabs.t1 == t2.t){
                //match
                return TypeResult.of(tabs.t2);
            }else{
                throw new TypeError("Types of parameter and argument mismatch");
            }
        }else{
            throw new TypeError("Applying non-function");
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // TODO
        return null;
    }
}
