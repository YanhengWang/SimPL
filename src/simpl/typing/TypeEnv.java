package simpl.typing;

import simpl.parser.Symbol;

import java.util.HashSet;

public abstract class TypeEnv {
    public abstract Type get(Symbol x);
    public abstract HashSet<TypeVar> typeVariables();

    public static final TypeEnv empty = new TypeEnv() {
        @Override
        public Type get(Symbol x) {
            return null;
        }
        @Override
        public HashSet<TypeVar> typeVariables(){
            return new HashSet<TypeVar>();
        }
    };

    public static TypeEnv of(final TypeEnv E, final Symbol x, final Type t) {
        return new TypeEnv() {
            @Override
            public Type get(Symbol x1) {
                if (x == x1) return t;
                return E.get(x1);
            }
            @Override
            public HashSet<TypeVar> typeVariables(){
                HashSet<TypeVar> set = E.typeVariables();
                if(t instanceof TypeVar)
                    set.add((TypeVar)t);
                return set;
            }
            public String toString() {
                return x + ":" + t + ";" + E;
            }
        };
    }

    public static TypeEnv embody(final TypeEnv E, final Substitution s){
        return new TypeEnv() {
            @Override
            public Type get(Symbol x) {
                return s.apply(E.get(x));
            }
            @Override
            public HashSet<TypeVar> typeVariables(){
                HashSet<TypeVar> set = E.typeVariables();
                HashSet<TypeVar> ret = new HashSet<TypeVar>();
                for(TypeVar tv: set)
                    ret.add((TypeVar)(s.apply(tv)));
                return ret;
            }
            public String toString(){
                return s + "(" + E + ")";
            }
        };
    }
}
