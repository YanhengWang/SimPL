package simpl.typing;

public final class StreamType extends Type {

    public Type t;

    public StreamType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        return false;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof TypeVar)
            return t.unify(this);
        if(t instanceof StreamType){
            StreamType st = (StreamType) t;
            return this.t.unify(st.t);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        return t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new StreamType(this.t.replace(a, t));
    }

    public String toString() {
        return t + " stream";
    }
}
