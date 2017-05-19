package runners;

import worked.Counter;
import worked.Initializator;

/**
 * Created by Andrey on 19.10.2015.
 */
public class CountMarksRunner {
    private static int L = 4;

    public static void main(String[] args) {
        double res = 0;
        double[] eps = {0.1, 0.3};
        double[] delta = {0.5, 0.33, 0.25, 0.1, 0.05, 0.03, 0.01, 0.005, 0.0005};
        for (double e : eps) {
            for (double d : delta) {
                res = 0;
                for (int i = 0; i < 100; ++i) {
                    Initializator init = new Initializator(e, d, 100000);
                    Counter counter = new Counter(init, L);
                    res += counter.showEmpiricEntropy(L);
                }
                System.out.println( " eps = " + e+ "delta = " + d+ "entropy = " +L*res / 100.);
            }
        }
    }
}
