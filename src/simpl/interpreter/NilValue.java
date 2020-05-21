package simpl.interpreter;

import java.util.HashSet;
import java.util.LinkedList;

public class NilValue extends Value {

    public static final NilValue NIL = new NilValue();

    private NilValue() {
    }

    public String toString() { return "nil"; }

    @Override
    public boolean equals(Object other) {
        return other instanceof NilValue;
    }

    @Override
    public HashSet<Integer> refSet(){
        return new HashSet<>();
    }
}
