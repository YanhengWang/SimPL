package simpl.interpreter;

import java.util.HashSet;

public class UnitValue extends Value {

    public static final UnitValue UNIT = new UnitValue();

    public UnitValue() { }

    public String toString() {
        return "unit";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UnitValue;
    }

    public void takeAction(State s){
        return;    //default: do nothing
    }
}
