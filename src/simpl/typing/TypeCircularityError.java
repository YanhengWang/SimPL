package simpl.typing;

public class TypeCircularityError extends TypeError {

    private static final long serialVersionUID = -5845539927612802390L;

    public TypeCircularityError(Type t1, Type t2) {
        super("Circular reference between " + t1 + " and " + t2);
    }
}
