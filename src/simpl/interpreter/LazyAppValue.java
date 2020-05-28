package simpl.interpreter;

import simpl.parser.ast.Expr;

public class LazyAppValue extends Value {

    public final Env binding;
    public final Expr e;

    public LazyAppValue(Env binding, Expr e) {
        this.binding = binding;
        this.e = e;
    }

    public String toString() {
        return "fun_lazy";
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

}
