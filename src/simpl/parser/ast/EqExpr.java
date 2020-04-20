package simpl.parser.ast;

import simpl.typing.ListType;
import simpl.typing.PairType;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type t1 = l.typecheck(E).t;
        Type t2 = r.typecheck(E).t;

        if(t1.isEqualityType() && t2.isEqualityType() && t1.toString().equals(t2.toString())){
            return TypeResult.of(Type.BOOL);
        }else{
            throw new TypeError("Incomparable types");
        }
    }
}
