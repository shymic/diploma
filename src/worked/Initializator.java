package worked;

import java.util.Random;

/**
 * Created by Andrey on 28.09.2015.
 */
public class Initializator {
    private static Random rand = new Random();

    private final double[] pi = {0.5, 0.5};
    private final double teta = 0.5;

    private double eps;
    private double[][] p = new double[2][2];
    private double delta;
    private int T;


    private int[] x;
    private int[] y;
    private int[] gamma;
    private int[] m;

    public Initializator(double eps, double delta) {
        this.eps = eps;
        double[][] p = {{0.5*(1.+eps), 0.5*(1 - eps)}, { 0.5*(1 - eps), 0.5*(1.+eps)}};
        this.p = p;
        this.delta = delta;
        this.T = 100000;

        x = Markov(pi, p, T);
        m = binomial(teta, T);
        gamma = binomial(delta, T);
        y = func(T);
    }

    public Initializator(double eps, double delta, int _T) {
        this.eps = eps;
        double[][] p = {{0.5*(1.+eps), 0.5*(1 - eps)}, { 0.5*(1 - eps), 0.5*(1.+eps)}};
        this.p = p;
        this.delta = delta;
        this.T = _T;


        x = Markov(pi, p, T);
        m = binomial(teta, T);
        gamma = binomial(delta, T);
        y = func(T);
    }

    public Initializator() {

        eps = 0.12;
        p = new double[][]{{0.5*(1.+eps), 0.5*(1 - eps)}, { 0.5*(1 - eps), 0.5*(1.+eps)}};
        delta = 0.01;
        T = 100000;

        x = Markov(pi, p, T);
        m = binomial(teta, T);
        gamma = binomial(delta, T);
        y = func(T);
    }


    public int[] getY() {
        return y;
    }

    public int getT() {
        return T;
    }

    int[] binomial(double p, int T) {
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

    int[] Markov(double[] pi, double[][] probabilities, int size) {
        int[] mark = new int[size];
        mark[0] = (int) get(pi);
        for (int i = 1; i < size; ++i) {
            mark[i] = (int) get(probabilities[mark[i - 1]]);
        }
        return mark;
    }

    int get(double[] pi) {
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

    public int[] func(int T) {
        int[] y = new int[T];
        for (int i = 0; i < T; ++i) {
            int ind = 0;
            for (int j = 0; j < i; ++j)
                ind += gamma[j];
            y[i] = ((1 - gamma[i]) * x[i] + gamma[i] * m[ind]);
        }
        return y;
    }
}
