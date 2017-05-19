package runners;

import worked.Counter;
import worked.Initializator;

/**
 * Created by Andrey on 21.12.2016.
 */
public class CountEmpiricEnthropyRunner {

        private static int L = 5;

        public static void main(String[] args) {
            double res = 0;
            double[] eps = {0.05, 0.12, 0.33};
            double[] delta = { 0.4, 0.25, 0.1, 0.05, 0.03, 0.01, 0.005 };
            for (double e : eps) {
                for (double d : delta) {
                    res = 0;
                    for (int i = 0; i < 100; ++i) {
                      //  Initializator init = new Initializator(e, d, 100000);
                        Counter counter = new Counter( L, e, d);
                        res += counter.showEmpiricEntropy(L);
                    }
                    System.out.println( " eps = " + e+ "  delta = " + d+ "  entropy = " +L*res / 100.);
                    System.out.println("H0=  " + H0(e,d));
                    System.out.println("coef=  "+ (L*res / 100.-H0(e,d))/(d*Math.log((1+e)/(1-e))/Math.log(2.)));
                }
            }
        }


    public static double H0(double eps, double delta){
        return 5.-(1-eps)*Math.log(1-eps)/Math.log(2.)-(1+eps)*Math.log(1+eps)/Math.log(2.);
    }
    }


