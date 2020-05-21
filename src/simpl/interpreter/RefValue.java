package simpl.interpreter;

import java.util.HashSet;

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

    @Override
    public HashSet<Integer> refSet(){
        HashSet<Integer> ret = new HashSet<>();
        ret.add(p);
        return ret;
    }
}
