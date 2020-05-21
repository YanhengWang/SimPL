package simpl.typing;

import simpl.interpreter.lib.*;
import simpl.interpreter.pcf.*;
import simpl.parser.Symbol;

import java.util.HashSet;

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
            E = TypeEnv.of(E, Symbol.symbol("gc"), UnitType.UNIT);
        }catch(TypeError e){
            System.out.println("Impossible");
        }
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }

    @Override
    public HashSet<TypeVar> typeVariables(){
        return E.typeVariables();
    }
}
