package socialite.util;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFloat extends Number {
    private static final long serialVersionUID = 42;

    private final AtomicInteger value;

    public AtomicFloat() {
        this(0.0f);
    }

    public AtomicFloat(float value) {
        this.value = new AtomicInteger(ftoi(value));
    }

    public final float get() {
        return itof(value.get());
    }

    public final void set(float newValue) {
        value.set(ftoi(newValue));
    }

    public final void lazySet(float newValue) {
        value.lazySet(ftoi(newValue));
    }

    public final float getAndSet(float newValue) {
        return itof(value.getAndSet(ftoi(newValue)));
    }

    public final boolean compareAndSet(float expect, float update) {
        return value.compareAndSet(ftoi(expect), ftoi(update));
    }

    public final boolean weakCompareAndSet(float expect, float update) {
        return value.weakCompareAndSet(ftoi(expect), ftoi(update));
    }

    public final float getAndIncrement() {
        return getAndAdd(1.0f);
    }

    public final float getAndDecrement() {
        return getAndAdd(-1.0f);
    }

    public final float getAndAdd(float delta) {
        while (true) {
            int icurrent = value.get();
            float current = itof(icurrent);
            float next = current + delta;
            int inext = ftoi(next);
            if(value.compareAndSet(icurrent, inext)) {
                return current;
            }
        }
    }

    public final float incrementAndGet() {
        return addAndGet(1.0f);
    }

    public final float decrementAndGet() {
        return addAndGet(-1.0f);
    }

    public final float addAndGet(float delta) {
        while (true) {
            int icurrent = value.get();
            float current = itof(icurrent);
            float next = current + delta;
            int inext = ftoi(next);
            if(value.compareAndSet(icurrent, inext)) {
                return next;
            }
        }
    }

    public String toString() {
        return Float.toString(get());
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
        return get();
    }

    @Override
    public double doubleValue() {
        return (double) get();
    }

    private static final int ftoi(final float f) {
        return Float.floatToIntBits(f);
    }

    private static final float itof(final int i) {
        return Float.intBitsToFloat(i);
    }
}