package socialite.util;

/**
 * Bit manipulation utilities
 * See http://graphics.stanford.edu/~seander/bithacks.html
 */
public class BitUtils {
    public static boolean isPowerOf2(int v) {
        return (v != 0) && ((v & (v - 1)) == 0);
    }
    public static int nextHighestPowerOf2(int v) {
        v--;
        v |= v >> 1; v |= v >> 2;
        v |= v >> 4; v |= v >> 8;
        v |= v >> 16;
        v++;
        return v;
    }

    public static int highestBitPos(int v) {
        int pos = 0;
        while (true) {
            if (v == 0) {
                break;
            }
            v >>= 1;
            pos++;
        }
        return pos;
    }
}
