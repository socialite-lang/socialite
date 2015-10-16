package socialite.tables;

import java.util.List;

public interface TableInst {
    public int id();
    public boolean isEmpty();
    public void init(List<Object> args);
    public void clear();
    public String name();
    public int totalAllocSize(); // total allocated memory.
}