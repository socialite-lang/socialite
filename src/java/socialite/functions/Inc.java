package socialite.functions;

public class Inc extends AbstractAggregation {
    static Inc inst = new Inc();
    public static Inc get() { return inst; }

    public int apply(int a, int b) { return a+b; }
    public long apply(long a, long b) { return a+b; }
    public float apply(float a, float b) { return a+b; }
    public double apply(double a, double b) { return a+b; }
}

