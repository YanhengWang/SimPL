package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class Expr {
    public abstract TypeResult typecheck(TypeEnv E) throws TypeError;
    public abstract Value eval(State s) throws RuntimeError;
    public void markTails(Symbol symbol){
        //default: do nothing because most constructs
        //block the possibility for tail recursion
        return;
    }
}
