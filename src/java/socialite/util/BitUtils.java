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

    public static int getIntBitMask(int from, int to) {
        int mask = 0;
        to = Math.min(to, 32);
        for (int i = from; i < to; i++) {
            mask = mask | (1 << i);
        }
        return mask;
    }

    public static long getLongBitMask(int from, int to) {
        long mask = 0;
        to = Math.min(to, 64);
        for (int i = from; i < to; i++) {
            mask = mask | (1L << i);
        }
        return mask;
    }

    public static void main(String[] args) {
        /*
        System.out.println(Integer.toBinaryString(getIntBitMask(0, 1)));
        System.out.println(Integer.toBinaryString(getIntBitMask(0, 2)));
        System.out.println(Integer.toBinaryString(getIntBitMask(0, 3)));

        System.out.println();

        System.out.println(Integer.toBinaryString(getIntBitMask(1, 3)));
        System.out.println(Integer.toBinaryString(getIntBitMask(1, 5)));
        System.out.println(Integer.toBinaryString(getIntBitMask(1, 6)));

        System.out.println();

        System.out.println(Integer.toBinaryString(getIntBitMask(4, 6)));
        System.out.println(Integer.toBinaryString(getIntBitMask(4, 7)));
        System.out.println(Integer.toBinaryString(getIntBitMask(4, 15)));

        System.out.println();
        */

        System.out.println(Long.toBinaryString(getLongBitMask(1, 2)));
        System.out.println();

        System.out.println(Long.toBinaryString(getLongBitMask(5, 10)));


        System.out.println(Long.toBinaryString(getLongBitMask(5, 64)));
        return;
    }
}
