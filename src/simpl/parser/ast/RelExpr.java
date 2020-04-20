package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult t1 = l.typecheck(E);
        TypeResult t2 = r.typecheck(E);
        if(t1.t != Type.INT || t2.t != Type.INT)
            throw new TypeError("Relation operands don't have type int");
        return TypeResult.of(Type.BOOL);
    }
}
