package socialite.functions;

import java.util.Random;

import socialite.util.Assert;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.iterator.TIntIterator;

public class Rand {
	static Random rand = new Random();

	public static int get(int rangeMin, int rangeMax) {
		assert rangeMin < rangeMax;
		return rangeMin+(rangeMax-rangeMin)*rand.nextInt();
	}
	public static int get(int rangeMax) {
		return get(0, rangeMax);
	}

	public static long get(long rangeMin, long rangeMax) {
		assert rangeMin < rangeMax;
		return rangeMin+(rangeMax-rangeMin)*rand.nextLong();
	}
	public static long get(long rangeMax) {
		return get(0, rangeMax);
	}
	
	public static double get(double rangeMin, double rangeMax) {
		assert rangeMin < rangeMax;
		return rangeMin+(rangeMax-rangeMin)*rand.nextDouble();
	}
	public static double get(double rangeMax) {
		return get(0, rangeMax);
	}
	
	public static float get(float rangeMin, float rangeMax) {
		assert rangeMin < rangeMax;
		return rangeMin+(rangeMax-rangeMin)*rand.nextFloat();
	}
	public static float get(float rangeMax) {
		return get(0, rangeMax);
	}
}
