package simpl.interpreter;

import simpl.parser.Symbol;
import java.util.HashSet;

public class Env {

    private final Env E;
    private final Symbol x;
    private final Value v;

    private Env() {
        E = null;
        x = null;
        v = null;
    }

    public static Env empty = new Env() {
        @Override
        public Value get(Symbol y) {
            return null;
        }
    };

    public Env(Env E, Symbol x, Value v) {
        this.E = E;
        this.x = x;
        this.v = v;
    }

    public Value get(Symbol y) {
        if(y == x)
            return v;
        return E.get(y);
    }

    public HashSet<Integer> refSet(){
        HashSet<Integer> ret = new HashSet<>();
        for(Env env=this; env!=empty && env!=null; env=env.E)
            ret.addAll(env.v.refSet());
        return ret;
    }
}
