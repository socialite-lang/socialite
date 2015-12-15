package socialite.util.concurrent;

import org.stringtemplate.v4.STGroupFile;
import socialite.codegen.Compiler;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class PrimitiveSkipListMapCodeGen {
    static String skiplistMapGroupFile = "PrimitiveSkipListMap.stg";
    static STGroup group;
    static {
        group = new STGroupFile(PrimitiveSkipListMapCodeGen.class.getResource(skiplistMapGroupFile), "UTF-8", '<', '>');
        group.load();
    }
    String type;
    String Type;
    ST template;
    public PrimitiveSkipListMapCodeGen(Class _type) {
        type = _type.getSimpleName();
        Type = type.substring(0, 1).toUpperCase()+type.substring(1);
        template = group.getInstanceOf("skipListMap");
    }

    public String name() {
        return "Concurrent"+Type+"SkipListMap";
    }
    public String generate() {
        template.add("type", type);
        template.add("Type", Type);
        return template.render();
    }

    public static void main(String[] args) {
        Compiler c = new Compiler(true);
        Class[] types = new Class[] {int.class, long.class, float.class, double.class};
        for (Class type:types) {
            PrimitiveSkipListMapCodeGen gen = new PrimitiveSkipListMapCodeGen(type);
            boolean success = c.compile(gen.name(), gen.generate());
            if (!success) {
                System.out.println("Compilation error for "+gen.name());
                System.out.println(c.getErrorMsg());
            }
        }
    }
}