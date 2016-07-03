package socialite.functions;

public class Max extends AbstractAggregation {
    static Max inst = new Max();
    public static Max get() { return inst; }

    public int apply(int a, int b) { return Math.max(a, b); }
    public long apply(long a, long b) { return Math.max(a, b); }
    public float apply(float a, float b) { return Math.max(a, b); }
    public double apply(double a, double b) { return Math.max(a, b); }
}
