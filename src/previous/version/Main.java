package previous.version;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Acer on 14.12.2014.
 */
public class Main {
    public static int[] x;
    public static int T = 1000000;
    public static int[] m;
    public static int[] gamma;
    public static int[] y;
    public static double[] pi = {0.5, 0.5};
    public static double eps = 0.35;
    public static double[][] p = {{eps, 1 - eps}, {1 - eps, eps}};
    public static double teta = 0.5;
    public static double delta = 0.5;
    public static Random rand = new Random();
    public static int L = 20;

    static int[] binomial(double p, int T) {
        int[] x = new int[T];
        double tmp = rand.nextDouble();
        for (int i = 0; i < T; i++) {
            if (tmp < p)
                x[i] = 1;
            else x[i] = 0;
            tmp = rand.nextDouble();
        }
        return x;
    }

    static int[] Markov(double[] pi, double[][] probabilities, int size) {
        int[] mark = new int[size];
        mark[0] = get(pi);
        for (int i = 1; i < size; ++i) {
            mark[i] = get(probabilities[mark[i - 1]]);
        }
        return mark;
    }

    static int get(double[] pi) {
        Random rnd = new Random();
        double probability = rnd.nextDouble();
        double sum = 0;
        for (int i = 0; i < pi.length - 1; ++i) {
            sum += pi[i];
            if (probability <= sum) {
                return i;
            }
        }
        return pi.length - 1;
    }

    public static int[] func(int T) {
        int[] y = new int[T];
        for (int i = 0; i < T; ++i) {
            int ind = 0;
            for (int j = 0; j < i; ++j)
                ind += gamma[j];
            y[i] = (1 - gamma[i]) * x[i] + gamma[i] * m[ind];
        }
        return y;
    }


    public static long[] convert(int[] y, int numOfNumber) {
        long[] res = new long[y.length - numOfNumber + 1];
        String s = "";
        for (int i = 0; i < y.length - numOfNumber + 1; i++) {
            s = "";
            for (int j = i; j < i + numOfNumber; j++) {
                s += y[j];
            }
            res[i] = Long.parseLong(s, 2);
        }
        return res;
    }

    public static Object[] countFrequencies(long[] x) {
        Map<Long, Integer> mapFreq = new HashMap<Long, Integer>();
        for (int i = 0; i < x.length; i++) {
            if (mapFreq.containsKey(x[i])) {
                int tmp = mapFreq.get(x[i]);
                tmp++;
                mapFreq.remove(x[i]);
                mapFreq.put(x[i], tmp);
            } else {
                mapFreq.put(x[i], 1);
            }
        }
        Collection<Integer> val = mapFreq.values();
        return val.toArray();
    }

    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * Math.log(x) / Math.log(2);
    }

    public static void print(int[] x) {
        for (int i : x) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void print(Object[] x) {
        for (Object i : x) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static double countDoubleEntropy(double delta) {
        double[] p2 = new double[4];
        p2[0] = 0.5 * eps * (1 - delta) * (1 - delta) + teta * delta * (1 - delta) + teta * teta * delta * delta;
        p2[1] = 0.5 * (1 - eps) * (1 - delta) * (1 - delta) + 0.5 * delta * (1 - delta) + teta * (1 - teta) * delta * delta;
        p2[2] = 0.5 * (1 - eps) * (1 - delta) * (1 - delta) + 0.5 * delta * (1 - delta) + teta * (1 - teta) * delta * delta;
        p2[3] = 0.5 * eps * (1 - delta) * (1 - delta) + delta * (1 - delta) * (1 - teta) + (1 - teta) * (1 - teta) * delta * delta;
        double entropy = 0;

        for (int i = 0; i < p2.length; ++i) {
            entropy += nLogN(p2[i]);
        }
        return 0.5 * entropy;
    }

    public static double countOwnEntropy() {

        double entropy = 0;
        for (int i = 0; i < pi.length; ++i) {
            entropy += nLogN(pi[i]);
        }
        return entropy;
    }


    public static void main(String[] args) {
        try {
            PrintWriter pw = new PrintWriter("res3333.csv");
            double[] delts = {0.1, 0.3, 0.5};
            double[] empiricEntropy0 = new double[L];
            double[] empiricEntropy01 = new double[L];
            double[] empiricEntropy03 = new double[L];
            double[] empiricEntropy05 = new double[L];
//            double[] resEmpiricEntropy0 = new double[L];
//            double[] resEmpiricEntropy01 = new double[L];
//            double[] resEmpiricEntropy03 = new double[L];
//            double[] resEmpiricEntropy05 = new double[L];
            //for (int tmp = 0; tmp < 20; tmp++) {
                initSequence(0);
                empiricEntropy0 = countEmpiricEntropy();

                for (int r = 0; r < delts.length; r++) {

                    initSequence(delts[r]);

                    if (delts[r] == 0.1) {
                            empiricEntropy01 = countEmpiricEntropy();
                    }
                    if (delts[r] == 0.3) {
                            empiricEntropy03 = countEmpiricEntropy();
                    }
                    if (delts[r] == 0.5) {
                            empiricEntropy05 = countEmpiricEntropy();
                    }


//                pw.println("H_1; " + countOwnEntropy());
//                pw.println("H_2( delta); " + countDoubleEntropy(delts[r]));
//                pw.println("H_2(0); " + countDoubleEntropy(0));
//                int[] TT = {1000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000};
//                pw.println("T;entropy delta;entropy delta=0;empiric entropy dela;empiric entropy delta =0");
//                for (int i = 0; i < TT.length; ++i) {
//                    T = TT[i];
//                    initSequence(delts[r]);
//
//                    pw.print(T + ";" + countDoubleEntropy(delts[r]) + ";" + countDoubleEntropy(0) +
//                            ";" + countEmpiricEntropy(2) + ";");
//                    initSequence(0);
//                    pw.println(countEmpiricEntropy(2) + ";");
//                }

                }
               /* for (int i = 0; i < L; i++) {
                    resEmpiricEntropy0[i] += empiricEntropy0[i];
                    resEmpiricEntropy01[i] += empiricEntropy01[i];
                    resEmpiricEntropy03[i] += empiricEntropy03[i];
                    resEmpiricEntropy05[i] += empiricEntropy05[i];

                }
*/
            //}
            pw.println("eps;" + eps);
            pw.println("teta;" + teta);
            pw.println("T;" + T);
//            for (int i = 0; i < L; i++) {
//                pw.println(i + ";" + resEmpiricEntropy0[i] / 20 + ";" + resEmpiricEntropy01[i] / 20 + ";"
//                        + resEmpiricEntropy03[i] / 20 + ";" + resEmpiricEntropy05[i] / 20);
//            }
            for (int i = 0; i < L; i++) {
                pw.println(i + ";" + empiricEntropy0[i]  + ";" + empiricEntropy01[i] + ";"
                        + empiricEntropy03[i] + ";" + empiricEntropy05[i] );
            }


            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static double[] countEmpiricEntropy() {
        double[] res = new double[L];
        long[] temp = convert(y, L);
        for (int i = L - 1; i >= 0; --i) {
            res[i] = countEmpiricEntropyForLGramm(temp, i);
            for (int j = 0; j < temp.length; j++) {
                temp[j] = temp[j] >> 1;
            }
        }
        return res;
    }

    private static double countEmpiricEntropyForLGramm(long[] temp, int i) {
        double res = 0.;
        Object[] frequencies = countFrequencies(temp);

        for (int j = 0; j < frequencies.length; ++j) {
            res += nLogN(Double.parseDouble(frequencies[j].toString()) / (double) (T - i + 1));
        }
        return res / (double) i;
    }

    private static void initSequence(double delta) {
        x = Markov(pi, p, T);
        m = binomial(teta, T);
        gamma = binomial(delta, T);
        y = func(T);
    }
}
