package simpl.interpreter;

public class IntValue extends Value {

    public final int n;

    public IntValue(int n) {
        this.n = n;
    }

    public String toString() {
        return "" + n;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof IntValue){
            IntValue v = (IntValue) other;
            return n == v.n;
        }
        return false;
    }
}
