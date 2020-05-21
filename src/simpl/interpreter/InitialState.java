package simpl.interpreter;

import simpl.interpreter.lib.*;
import simpl.interpreter.pcf.IsZero;
import simpl.interpreter.pcf.Pred;
import simpl.interpreter.pcf.Succ;
import simpl.parser.Symbol;

public class InitialState extends State {

    public InitialState() {
        super(initialEnv(), new Mem(), new Int(0));
        State.rootList.clear();
    }

    private static Env initialEnv() {
        Env ret = Env.empty;
        ret = new Env(ret, Symbol.symbol("fst"), Fst.fst);
        ret = new Env(ret, Symbol.symbol("snd"), Snd.snd);
        ret = new Env(ret, Symbol.symbol("hd"), Hd.hd);
        ret = new Env(ret, Symbol.symbol("tl"), Tl.tl);
        ret = new Env(ret, Symbol.symbol("pred"), Pred.pred);
        ret = new Env(ret, Symbol.symbol("succ"), Succ.succ);
        ret = new Env(ret, Symbol.symbol("iszero"), IsZero.iszero);
        ret = new Env(ret, Symbol.symbol("gc"), Gc.gc);
        return ret;
    }
}
