package socialite.tables;

public interface TupleWriteStream {
    public boolean write(Object ...args);
    public boolean write(Tuple t);
    public void flush();
    public void close();
}
