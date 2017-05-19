package series;

import worked.Initializator;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.pow;

/**
 * Created by Shymko_A on 19.05.2017.
 */
public class SeriesTester {
    public static void main(String[] args) {
       // int[] y = {0,1,0,1,0,0,1,1,0,0,1,1,0,0,0,1,1,1,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0};

        Initializator init = new Initializator(0.15, 0., 10);
        int[] y = init.getY();
        System.out.println (h(y,0.15));
        SeriesInitializator s= new SeriesInitializator();
        s.initSeries(y);
        System.out.println(h0(s, 0.15));

    }

    public static double h0 (SeriesInitializator s, double eps){
        int sum =0;
        int T = s.getSeries().size();
        for (Integer i : s.getSeries()) { sum+=i;    }
        double p = pow(2.,-T)*pow((1+eps),T-sum-1)*pow(1-eps,sum);
        return -p*log(p)/log(2);
    }
    public static double h(int[] y, double eps){
        return 0.5*log(0.5)/log(2.) - (y.length-1)*(1-eps)*log(1-eps)/log(2);
    }


}
