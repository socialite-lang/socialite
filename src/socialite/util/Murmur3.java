/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/*
 * Source: https://code.google.com/p/guava-libraries/source/browse/guava/src/com/google/common/hash/Murmur3_32HashFunction.java
 * (Modified to adapt to SociaLite coding conventions)
 *
 */
package socialite.util;

import java.io.Serializable;
public class Murmur3 {
	static final long serialVersionUID = 0L;
	
	private static final int C1 = 0xcc9e2d51;
	private static final int C2 = 0x1b873593;
	
	private static final long C1L = 0xff51afd7ed558ccdL;
	private static final long C2L = 0xc4ceb9fe1a85ec53L;
	
	private static final int seed = 42;//(int)System.currentTimeMillis();
	
	public static int hash32(int input) {
  		int k1 = mixK1_32(input);
  		int h1 = mixH1_32(seed, k1);
  		return fmix_32(h1);
  	}  
	public static long hash64(long input) {
  		long k1 = mixK1_64(input);
  		long h1 = mixH1_64(seed, k1);
  		return fmix_64(h1);
  	}
 
  	static int mixK1_32(int k1) {
  		k1 *= C1;
  		k1 = Integer.rotateLeft(k1, 15);
  		k1 *= C2;
  		return k1;
  	}
  	static int mixH1_32(int h1, int k1) {
  		h1 ^= k1;
  		h1 = Integer.rotateLeft(h1, 13);
  		h1 = h1 * 5 + 0xe6546b64;
  		return h1;
  	}
  	static int fmix_32(int h1) {
  		h1 ^= h1 >>> 16;
  		h1 *= 0x85ebca6b;
  		h1 ^= h1 >>> 13;
  		h1 *= 0xc2b2ae35;
  		h1 ^= h1 >>> 16;
  		return h1;
  	}
	
	static long mixK1_64(long k1) {
  		k1 *= C1L;
  		k1 = Long.rotateLeft(k1, 31);
  		k1 *= C2L;
  		return k1;
  	}
	static long mixH1_64(long h1, long k1) {
  		h1 ^= k1;
  		h1 = Long.rotateLeft(h1, 27);
  		h1 = h1 * 5 + 0x52dce729;
  		return h1;
  	}
	static long fmix_64(long h1) {
  		h1 ^= h1 >>> 33;
  		h1 *= 0xff51afd7ed558ccdL;
  		h1 ^= h1 >>> 33;
  		h1 *= 0xc4ceb9fe1a85ec53L;
  		h1 ^= h1 >>> 33;
  		return h1;
	}	
}
