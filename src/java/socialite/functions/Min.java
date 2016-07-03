package socialite.functions;

import socialite.type.Utf8;

public class Min extends AbstractAggregation {
    static Min inst = new Min();
    public static Min get() { return inst; }

    public int apply(int a, int b) { return Math.min(a, b); }
    public long apply(long a, long b) { return Math.min(a, b); }
    public float apply(float a, float b) { return Math.min(a, b); }
    public double apply(double a, double b) { return Math.min(a, b); }
}
