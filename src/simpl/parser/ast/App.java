package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
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
        Value valueL = l.eval(s);
        Value valueR = r.eval(s);

        if(valueL instanceof LazyAppValue){
            // It's at tail, don't call it at this level, but just add the binding virtually
            LazyAppValue lazy = (LazyAppValue)valueL;
            Fn f = (Fn) lazy.e;
            return new LazyAppValue(
                new Env(lazy.binding, f.x, valueR), f.e
            );
        }

        FunValue f = (FunValue) valueL;
        Value ret = f.e.eval(
            State.of(new Env(f.E, f.x, valueR), s.M, s.p)
        );  //call the function body under f's environment, with f.x mapping to v

        while(ret instanceof LazyAppValue){
            // This is the right place to execute the lazy application
            LazyAppValue lazy = (LazyAppValue)ret;
            Env E = f.E;
            for(Env env=lazy.binding; env!=Env.empty; env=env.E)
                E = new Env(E, env.x, env.v);    //put the bindings onto E
            ret = lazy.e.eval(State.of(E, s.M, s.p));
        }

        return ret;
    }

    @Override
    public void markTails(Symbol symbol){
        l.markTails(symbol);
    }
}
