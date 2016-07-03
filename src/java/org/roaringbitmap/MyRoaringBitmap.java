package org.roaringbitmap;

import socialite.tables.TableInst;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyRoaringBitmap extends RoaringBitmap {

    public static MyRoaringBitmap bitmapOf(int... _vals) {
        MyRoaringBitmap map = new MyRoaringBitmap();
        int[] vals = _vals;
        for (int v:vals) {
            map.add(v);
        }
        return map;
    }
    public static RoaringBitmap or(RoaringBitmap x1, RoaringBitmap x2) {
        return RoaringBitmap.or(x1, x2);
    }
    public static RoaringBitmap and(RoaringBitmap ...bitmaps) {
        if (bitmaps.length < 2) {
            throw new AssertionError("Requires more than 2 bitmaps");
        }
        RoaringBitmap ans = and(bitmaps[0], bitmaps[1]);
        for (int i=2; i<bitmaps.length; i++) {
            RoaringBitmap b = bitmaps[i];
            ans.and(bitmaps[i]);
        }
        return ans;
    }

    public MyRoaringBitmap() {
        super();
    }
}