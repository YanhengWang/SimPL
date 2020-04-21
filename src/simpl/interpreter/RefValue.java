package simpl.interpreter;

public class RefValue extends Value {

    public final int p;

    public RefValue(int p) {
        this.p = p;
    }

    public String toString() {
        return "ref@" + p;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof RefValue){
            RefValue v = (RefValue) other;
            return p == v.p;
        }
        return false;
    }
}
