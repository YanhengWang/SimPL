package simpl.parser.ast;

import simpl.interpreter.*;
import simpl.typing.*;

public class Stream extends Expr {
    public Expr seed;
    public Expr gen;

    public Stream(Expr seed, Expr gen) {
        this.seed = seed;
        this.gen = gen;
    }

    public String toString() {
        return "(stream | " + seed + " | " + gen + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //New constraint: {tgen=tseed->tseed}
        TypeResult resultSeed, resultGen;
        Substitution s1, s2, s3, s4;

        resultSeed = seed.typecheck(E);
        s1 = resultSeed.s;

        resultGen = gen.typecheck(TypeEnv.embody(E,s1));
        s2 = resultGen.s;
        s3 = resultGen.t.unify(new ArrowType(resultSeed.t, resultSeed.t));
        s4 = s3.compose(s2.compose(s1));
        return TypeResult.of(
                s4, new StreamType(s4.apply(resultSeed.t))
        );
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new StreamValue(
                seed.eval(s), (FunValue)gen.eval(s)
        );
    }
}
