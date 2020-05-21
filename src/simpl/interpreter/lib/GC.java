package simpl.interpreter.lib;

import simpl.interpreter.*;
import simpl.parser.Symbol;

import java.util.HashSet;

public class GC extends UnitValue {
    private static final Symbol sym = Symbol.symbol("mode");

    public static final GC gc = new GC() {
        @Override
        public void takeAction(State s) {
            HashSet<Integer> references = s.E.refSet();
            int size = s.M.size();
            s.M.keySet().removeIf(
                    location -> !references.contains(location)
            );
            System.out.println(references);
            System.out.println((size - s.M.size()) + " cells reclaimed.");
        }
    };
}
