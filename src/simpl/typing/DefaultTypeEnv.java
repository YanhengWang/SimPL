package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        E = TypeEnv.empty;
        // TODO: The default setting should include predefined library functions
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
