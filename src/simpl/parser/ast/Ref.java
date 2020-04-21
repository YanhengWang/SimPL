package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type t = e.typecheck(E).t;
        return TypeResult.of(new RefType(t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v = e.eval(s);
        int location = s.p.get();
        s.M.put(location, v);  //create a new memory cell
        s.p.inc();  //increment the memory pointer
        return new RefValue(location);
    }
}
