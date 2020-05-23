package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class Dispatch extends UnaryExpr {

    public Dispatch(Expr e) {
        super(e);
    }

    public String toString() {
        return e + "⊣";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {t=STREAM(X)}
        TypeResult result;
        TypeVar X = new TypeVar(true);
        Substitution s1, s2, s3;

        result = e.typecheck(E);
        s1 = result.s;
        s2 = result.t.unify(new StreamType(X));    //t=STREAM(X)
        s3 = s2.compose(s1);
        return TypeResult.of(
                s3, new ListType(s3.apply(X))
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        StreamValue stream = (StreamValue) e.eval(s);
        Deque<FunValue> pipeline = stream.getPipeline();
        Iterator<FunValue> iter;
        Deque<Value> list = new ArrayDeque<>();

        //Data processing. Results stored in list.
        if(stream.list != null){
            Value cons = stream.list;
            for(int i=0; i!=stream.limit && !(cons instanceof NilValue); i++){
                //foreach element in list
                iter = pipeline.iterator();
                Value val = ((ConsValue)cons).v1;
                while(iter.hasNext()){
                    //process val by chain of functions
                    FunValue f = iter.next();
                    val = f.e.eval(
                        State.of(new Env(f.E, f.x, val), s.M, s.p)
                    );
                }
                list.addFirst(val);
                cons = ((ConsValue)cons).v2;
            }
        }else{
            //foreach element generated
            Value input = stream.seed;
            FunValue g = stream.gen;
            for(int i=0; i<stream.limit; i++){
                iter = pipeline.iterator();
                Value val = input;
                while(iter.hasNext()){
                    //process val by chain of functions
                    FunValue f = iter.next();
                    val = f.e.eval(
                        State.of(new Env(f.E, f.x, val), s.M, s.p)
                    );
                }
                list.addFirst(val);
                //generate the next input
                input = g.e.eval(
                    State.of(new Env(g.E, g.x, input), s.M, s.p)
                );
            }

        }

        //Transform list to ConsValue
        Value ret = NilValue.NIL;
        while(!list.isEmpty())
            ret = new ConsValue(list.removeFirst(), ret);
        return ret;
    }
}
