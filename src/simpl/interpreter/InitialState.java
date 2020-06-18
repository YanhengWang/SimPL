package simpl.interpreter;

import simpl.interpreter.lib.*;
import simpl.parser.Symbol;

public class InitialState extends State {

    public InitialState() {
        super(initialEnv(), new Mem(), new Int(0));
        State.rootList.clear();
    }

    private static Env initialEnv() {
        Env ret = Env.empty;
        State s = new State(Env.empty, null, null);    //used in this function only
        try {
            ret = new Env(ret, Symbol.symbol("fst"), Fst.fst.eval(s));
            ret = new Env(ret, Symbol.symbol("snd"), Snd.snd.eval(s));
            ret = new Env(ret, Symbol.symbol("hd"), Hd.hd.eval(s));
            ret = new Env(ret, Symbol.symbol("tl"), Tl.tl.eval(s));
            ret = new Env(ret, Symbol.symbol("gc"), Gc.gc);
            ret = new Env(ret, Symbol.symbol("print"), Print.print.eval(s));
            ret = new Env(ret, Symbol.symbol("toStream"), ToStream.toStream.eval(s));
            ret = new Env(ret, Symbol.symbol("pred"), Pred.pred.eval(s));
            ret = new Env(ret, Symbol.symbol("succ"), Succ.succ.eval(s));
            ret = new Env(ret, Symbol.symbol("iszero"), IsZero.iszero.eval(s));
            ret = new Env(ret, Symbol.symbol("sum"), Sum.sum.eval(s));
            ret = new Env(ret, Symbol.symbol("max"), Max.max.eval(s));
            ret = new Env(ret, Symbol.symbol("min"), Min.min.eval(s));
        } catch(RuntimeError e){
            //impossible!
        }
        return ret;
    }
}
