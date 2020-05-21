package simpl.interpreter;

import java.util.HashSet;

public abstract class Value {
    public abstract boolean equals(Object other);
    public abstract HashSet<Integer> refSet();
}
