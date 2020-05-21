package simpl.interpreter;

public abstract class Value {
    public abstract boolean equals(Object other);
    public void markMemory(Mem M){
        return;    //default: do nothing
    }
}
