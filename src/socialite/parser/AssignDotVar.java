package socialite.parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.stringtemplate.v4.ST;

import socialite.codegen.CodeGen;
import socialite.util.InternalException;

public class AssignDotVar extends AssignOp {
    private static final long serialVersionUID = 1L;

    Variable root;
    public AssignDotVar() { }
	public AssignDotVar(Variable dotVar, Variable _root) throws InternalException {	
		super(dotVar, dotVar);
		root = _root;
	}
	
	public void getRhsTypes(Collection<Class> types) throws InternalException {
		if (!root.hasType())
			types.add(NoType.class);
		
		Variable dotVar=(Variable)arg1;
		
		String varName = dotVar.name; 
		Class klass=root.type;
		
		int prefix=root.name.length();
		int len = varName.length();
		
		try {
			while (prefix < len) {
				int endIdx=varName.indexOf('.', prefix+1);
				if (endIdx==-1) endIdx = varName.length();
				String fieldName = varName.substring(prefix+1, endIdx);
				
				Field f = klass.getField(fieldName);
				klass = f.getType();
				
				prefix = endIdx;
			}
			types.add(klass);
		} catch (Exception e) {
			types.add(NoType.class);
		}		
	}
	
	public ST codegen() {
		return CodeGen.stmts();
	}
	public String toString() {
		return "";
	}
}
