package socialite.functions;

/**
 * Created by jiwon on 4/13/15.
 */
public class AdjScore {
    public static float compute(float score, int boardCount, float max) {
        float multFactor = (float)(Math.log(boardCount)/Math.log(30.0));
        if (multFactor > max) { multFactor = max; }
        return score*multFactor;
    }
}
