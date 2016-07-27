package socialite.functions;

import socialite.type.Utf8;
public final class Sum extends AbstractAggregation {
    static Sum inst = new Sum();
    public static Sum get() { return inst; }

    public int apply(int a, int b) { return a+b; }
    public long apply(long a, long b) { return a+b; }
    public float apply(float a, float b) { return a+b; }
    public double apply(double a, double b) { return a+b; }
    public String apply(String a, String b) { return a+b; }
}
