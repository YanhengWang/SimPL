package simpl.typing;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        return t1.isEqualityType() && t2.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof TypeVar)
            return t.unify(this);
        if(t instanceof PairType){
            PairType pt = (PairType) t;
            Substitution s1 = t1.unify(pt.t1);
            Type t3 = s1.apply(t2);
            Type t4 = s1.apply(pt.t2);
            Substitution s2 = t3.unify(t4);
            return s2.compose(s1);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new PairType(t1.replace(a, t), t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
