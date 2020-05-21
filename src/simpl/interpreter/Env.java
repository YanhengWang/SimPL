package simpl.interpreter;

import simpl.parser.Symbol;

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
        for(Env env=this; env!=null; env=env.E){
            if(y == env.x)
                return env.v;
        }
        return null;
    }

    public void markMemory(Mem M){
        for(Env env=this; env!=empty && env!=null; env=env.E)
            env.v.markMemory(M);
    }
}
