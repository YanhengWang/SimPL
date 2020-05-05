package simpl.typing;

public abstract class Substitution {

    public abstract Type apply(Type t);

    public Substitution compose(Substitution inner) {
        if(this == IDENTITY)
            return inner;
        if(inner == IDENTITY)
            return this;
        return new Compose(this, inner);
    }

    public static final class Replace extends Substitution {
        private TypeVar a;
        private Type t;
        public Replace(TypeVar a, Type t) {
            this.a = a;
            this.t = t;
        }
        public Type apply(Type b) {
            if(b == null)
                return null;
            return b.replace(a, t);
        }
    }

    private static final class Compose extends Substitution {
        private Substitution f, g;
        public Compose(Substitution f, Substitution g) {
            this.f = f;
            this.g = g;
        }
        public Type apply(Type t) {
            return f.apply(g.apply(t));
        }
    }

    public static final Substitution IDENTITY = new Substitution() {
        @Override
        public Type apply(Type t) {
            return t;
        }
    };
}
