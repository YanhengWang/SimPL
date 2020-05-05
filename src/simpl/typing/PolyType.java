package simpl.typing;

import java.util.HashSet;
import java.util.Set;

public class PolyType extends Type{
	private final Type scheme;
	private final TypeEnv env;
	private final HashSet<TypeVar> boundVar;
	private final HashSet<TypeVar> freeVar = new HashSet<TypeVar>();

	public PolyType(Type scheme, TypeEnv env) {
		this.scheme = scheme;
		this.env = env;
		boundVar = env.typeVariables();
		collectFreeVar(scheme);
	}

	private void collectFreeVar(Type t){
		if(t instanceof ArrowType){
			collectFreeVar(((ArrowType) t).t1);
			collectFreeVar(((ArrowType) t).t2);
		}else if(t instanceof ListType){
			collectFreeVar(((ListType) t).t);
		}else if(t instanceof PairType){
			collectFreeVar(((PairType) t).t1);
			collectFreeVar(((PairType) t).t2);
		}else if(t instanceof RefType){
			collectFreeVar(((RefType) t).t);
		}else if(t instanceof TypeVar){
			if(!boundVar.contains(t))
				freeVar.add((TypeVar) t);
		}
	}

	@Override
	public boolean isEqualityType() {
		return false;
	}

	@Override
	public Substitution unify(Type t) throws TypeError {
		return null;
	}

	@Override
	public boolean contains(TypeVar tv) {
		return false;
	}

	@Override
	public Type replace(TypeVar a, Type t) {
		return new PolyType(scheme.replace(a, t), env);
	}

	public Type instantiate(){
		Type ret = scheme;
		for(TypeVar tv: freeVar){
			ret = ret.replace(tv, new TypeVar(tv.isEqualityType()));
		}
		return ret;
	}

	public String toString() {
		return scheme.toString() + "poly with" + freeVar.toString();
	}
}
