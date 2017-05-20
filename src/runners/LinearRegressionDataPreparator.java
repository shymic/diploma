package runners;

import worked.Counter;
import worked.Initializator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Andrey on 02.05.2016.
 */
public class LinearRegressionDataPreparator {

    private static int[] L = {3, 4};
    private static double[] eps = {0.15, 0.55};
    private static double[] delta = {0., 0.005, 0.01,  0.4, 0.5};
    private static int[] T = {10000};
    private static int M = 100;

    public static void main(String[] args) {
        empiricGraphics();
    }

    private static void empiricGraphics() {
        try {
            PrintWriter pw = new PrintWriter("LinearRegressionData2.csv");
            ArrayList<Double> entH3 = new ArrayList<Double>();
            ArrayList<Double> entH4 = new ArrayList<Double>();
            double entr = 0.;
            for (int l : L) {
                pw.println("L=" + l);
                for (double e : eps) {
                    for (double d : delta) {
                        for (int len : T) {
                            for (int i = 0; i < M; i++) {
                                Initializator init = new Initializator(e, d, len);
                                Counter counter = new Counter(init, l);
                                entr = counter.showEmpiricEntropy(l);
                                if ( l == 3){
                                    entH3.add(entr);
                                }else { entH4.add(entr);}
                                pw.println("T;" + len + ";eps;" + e + "; delta;" + d + ";entropy;" + entr);

                            }
                        }
                    }
                }
            }


            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
