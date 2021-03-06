package simpl.interpreter;

import simpl.parser.Symbol;
import simpl.parser.ast.Expr;

import java.util.HashSet;
import java.util.LinkedList;

public class FunValue extends Value {

    public final Env E;
    public final Symbol x;
    public final Expr e;

    public FunValue(Env E, Symbol x, Expr e) {
        this.E = E;
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "fun";
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    public void markMemory(Mem M){
        E.markMemory(M);
    }
}
