package socialite.tables;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public interface NodeBase {
    static final Unsafe unsafe = getUnsafe();
    static Unsafe getUnsafe() {
        try {
            Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singleoneInstanceField.setAccessible(true);
            return (Unsafe) singleoneInstanceField.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Could not find 'theUnsafe' field in the " + Unsafe.class);
        }
    }
}
