package socialite.util;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicDouble extends Number {
    private static final long serialVersionUID = 4992180751166673712L;

    private final AtomicLong value;

    public AtomicDouble() {
        this(0.0);
    }

    public AtomicDouble(double _value) {
        this.value = new AtomicLong(l(_value));
    }

    public final double get() {
        return d(value.get());
    }

    public final void set(double newValue) {
        value.set(l(newValue));
    }

    public final void lazySet(double newValue) {
        value.lazySet(l(newValue));
    }

    public final double getAndSet(double newValue) {
        return d(value.getAndSet(l(newValue)));
    }

    public final boolean compareAndSet(double expect, double update) {
        return value.compareAndSet(l(expect), l(update));
    }

    public final boolean weakCompareAndSet(double expect, double update) {
        return value.weakCompareAndSet(l(expect), l(update));
    }

    public final double getAndIncrement() {
        return getAndAdd(1.0);
    }

    public final double getAndDecrement() {
        return getAndAdd(-1.0);
    }

    public final double getAndAdd(double delta) {
        for(;;) {
            long lcurrent = value.get();
            double current = d(lcurrent);
            double next = current + delta;
            long lnext = l(next);
            if(value.compareAndSet(lcurrent, lnext)) {
                return current;
            }
        }
    }

    public final double incrementAndGet() {
        return addAndGet(1.0);
    }

    public final double decrementAndGet() {
        return addAndGet(-1.0);
    }

    public final double addAndGet(double delta) {
        for(;;) {
            long lcurrent = value.get();
            double current = d(lcurrent);
            double next = current + delta;
            long lnext = l(next);
            if(value.compareAndSet(lcurrent, lnext)) {
                return next;
            }
        }
    }

    public String toString() {
        return Double.toString(get());
    }

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return (long) get();
    }

    @Override
    public float floatValue() {
        return (float)get();
    }

    @Override
    public double doubleValue() {
        return get();
    }

    private static final long l(final double d) {
    	return Double.doubleToLongBits(d);
    }

    private static final double d(final long l) {
    	return Double.longBitsToDouble(l);
    }
}