package socialite.functions;

import socialite.type.Utf8;

/**
 * Created by jiwon on 4/1/15.
 */
public class Pinsig {
  public static Utf8 toSig(String sigstr) {
    int len = sigstr.length();
    byte[] sig = new byte[len/2];
    for (int i=0; i<len; i+=2) {
      sig[i / 2] = (byte) ((Character.digit(sigstr.charAt(i), 16) << 4)
          + Character.digit(sigstr.charAt(i+1), 16));
    }
    return new Utf8(sig, true);
  }
}
