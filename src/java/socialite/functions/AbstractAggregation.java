package socialite.functions;

import socialite.type.Utf8;

/**
 * Base class for aggregation operation. Used with groupby.
 */
public abstract class AbstractAggregation {
    public int apply(int a, int b) { return 0; }
    public long apply(long a, long b) { return 0; }
    public float apply(float a, float b) { return 0.0f; }
    public double apply(double a, double b) { return 0.0; }
    public String apply(String a, String b) { return null; }
    public Utf8 apply(Utf8 a, Utf8 b) { return null; }
    public Object apply(Object a, Object b) { return null; }
}
