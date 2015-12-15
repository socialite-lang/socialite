package socialite.functions;

/**
 * Created by jiwon on 3/31/15.
 */
public class PageRank {
    public static int propagate(int wedOrNot, int followerCount) {
        if (wedOrNot != 2 && followerCount > 500000) {
            return 0;
        }
        if (wedOrNot == 1) {
            return 0;
        }
        return 1;
    }
}
