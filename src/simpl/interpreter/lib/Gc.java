package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;

public class Gc extends UnitValue {
    public static final Gc gc = new Gc() {
        @Override
        public void takeAction(State s) {
            // Mark all memory cells, starting from root list.
            for(Value value: State.rootList)
                value.markMemory(s.M);

            // Mark all memory cells, starting from the current environment
            s.E.markMemory(s.M);

            // Reclaim the marked cells
            System.out.println(s.M.reclaim() + " cells reclaimed");
        }
    };
}
