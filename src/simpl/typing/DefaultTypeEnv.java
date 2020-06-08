package simpl.typing;

import simpl.interpreter.lib.*;
import simpl.parser.Symbol;
import java.util.HashSet;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        E = TypeEnv.empty;
        E = TypeEnv.of(E, Symbol.symbol("fst"), Fst.fst.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("snd"), Snd.snd.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("hd"), Hd.hd.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("tl"), Tl.tl.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("gc"), UnitType.UNIT);
        E = TypeEnv.of(E, Symbol.symbol("print"), Print.print.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("toStream"), ToStream.toStream.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("pred"), Pred.pred.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("succ"), Succ.succ.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("iszero"), IsZero.iszero.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("sum"), Sum.sum.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("max"), Min.min.typecheck(TypeEnv.empty).t);
        E = TypeEnv.of(E, Symbol.symbol("min"), Max.max.typecheck(TypeEnv.empty).t);
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
