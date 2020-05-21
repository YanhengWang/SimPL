package simpl.interpreter;

import java.util.HashSet;

public class BoolValue extends Value {

    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);

    public final boolean b;

    private BoolValue(boolean b) {
        this.b = b;
    }

    public String toString() {
        return "" + b;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof BoolValue){
            BoolValue bool = (BoolValue) other;
            return b == bool.b;
        }
        return false;
    }

    @Override
    public HashSet<Integer> refSet(){
        return new HashSet<>();
    }
}
