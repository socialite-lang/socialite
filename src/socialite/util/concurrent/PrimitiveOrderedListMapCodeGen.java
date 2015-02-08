package socialite.util.concurrent;

import org.stringtemplate.v4.STGroupFile;
import socialite.codegen.Compiler;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class PrimitiveOrderedListMapCodeGen {
    static String skiplistMapGroupFile = "PrimitiveOrderedListMap.stg";
    static STGroup group;
    static {
        group = new STGroupFile(PrimitiveOrderedListMapCodeGen.class.getResource(skiplistMapGroupFile), "UTF-8", '<', '>');
        group.load();
    }
    String type;
    String Type;
    String TypeFull;
    ST template;
    public PrimitiveOrderedListMapCodeGen(Class _type) {
        type = _type.getSimpleName();
        Type = type.substring(0, 1).toUpperCase()+type.substring(1);
        if (_type.equals(int.class)) {
            TypeFull = "Integer";
        } else if (_type.equals(char.class)) {
            TypeFull = "Character";
        } else { TypeFull = Type; }
        template = group.getInstanceOf("orderedListMap");
    }

    public String name() {
        return "Concurrent"+Type+"OrderedListMap";
    }
    public String generate() {
        template.add("type", type);
        template.add("Type", Type);
        template.add("TypeFull", TypeFull);
        return template.render();
    }

    public static void main(String[] args) {
        Compiler c = new Compiler(true);
        Class[] types = new Class[] {int.class, long.class, float.class, double.class};
        for (Class type:types) {
            PrimitiveOrderedListMapCodeGen gen = new PrimitiveOrderedListMapCodeGen(type);
            boolean success = c.compile(gen.name(), gen.generate());
            if (!success) {
                System.out.println("Compilation error for "+gen.name());
                System.out.println(c.getErrorMsg());
            }
        }
    }
}
