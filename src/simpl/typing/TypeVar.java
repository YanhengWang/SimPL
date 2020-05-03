package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private final boolean equalityType;
    public final Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("X" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        if(this == t)
            return Substitution.IDENTITY;
        if(t.contains(this))
            throw new TypeCircularityError();
        return new Substitution.Replace(this, t);
    }

    @Override
    public boolean contains(TypeVar tv) {
        return tv == this;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return (a == this) ? t : this;
    }

    public String toString() {
        return "" + name;
    }
}
