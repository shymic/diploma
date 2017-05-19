package runners;

import worked.Counter;
import worked.Initializator;

/**
 * Created by Andrey on 28.09.2015.
 */
public class Runner {
    private static int L = 5;
    private static double eps = 0.12;
    private static double delta = 0.01;


    public static void main(String[] args) {

        Counter counter = new Counter(L, eps, delta);
        for (int i = L; i > 4; --i) {
            System.out.println(counter.showEmpiricEntropy(i)*i);
            counter.setDecimal(counter.bitShift(counter.getDecimal()));
        }
    }
}
