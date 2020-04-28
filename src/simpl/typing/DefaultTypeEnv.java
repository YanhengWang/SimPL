package simpl.typing;

import simpl.interpreter.lib.Fst;
import simpl.interpreter.lib.Hd;
import simpl.interpreter.lib.Snd;
import simpl.interpreter.lib.Tl;
import simpl.interpreter.pcf.IsZero;
import simpl.interpreter.pcf.Pred;
import simpl.interpreter.pcf.Succ;
import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        E = TypeEnv.empty;
        try {
            E = TypeEnv.of(E, Symbol.symbol("fst"), Fst.fst.e.typecheck(TypeEnv.empty).t);
            E = TypeEnv.of(E, Symbol.symbol("snd"), Snd.snd.e.typecheck(TypeEnv.empty).t);
            E = TypeEnv.of(E, Symbol.symbol("hd"), Hd.hd.e.typecheck(TypeEnv.empty).t);
            E = TypeEnv.of(E, Symbol.symbol("tl"), Tl.tl.e.typecheck(TypeEnv.empty).t);
            E = TypeEnv.of(E, Symbol.symbol("pred"), Pred.pred.e.typecheck(TypeEnv.empty).t);
            E = TypeEnv.of(E, Symbol.symbol("succ"), Succ.succ.e.typecheck(TypeEnv.empty).t);
            E = TypeEnv.of(E, Symbol.symbol("iszero"), IsZero.iszero.e.typecheck(TypeEnv.empty).t);
        }catch(TypeError e){
            System.out.println("Impossible");
        }
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
