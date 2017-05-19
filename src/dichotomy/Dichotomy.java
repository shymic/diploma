package dichotomy;

import worked.Counter;
import worked.Initializator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.abs;
import static java.lang.Math.log;
import static java.lang.StrictMath.pow;

/**
 * Created by Andrey on 07.05.2017.
 */
public class Dichotomy {

    public static int[]T = { 1000,   10000, 50000, 100000, 500000 };
    public static double[]EPS = { 0.45};
    public static double[]DELT = {0., 0.00005, 0.0001, 0.0005, 0.001, 0.005, 0.01, 0.05 };



   /* public static double func(double x, double h3, double h4){//через h3h4
        return (1.+x)*log(1.+x)/log(2)+(1.-x)*log(1.-x)/log(2.) - (h3- 4./3. * h4 + 7./3. );
    }*/
    public static double func(double x,double h2, double h3, double h4){//черезh2h3h4
        return 5.*(1.+x)*log(1.+x)/log(2)+5.*(1.-x)*log(1.-x)/log(2.) - (3.*h2+3.*h3 -8.*h4+ 17. );
    }
   public static double func(double x, double h2, double h3){//черезh2h3
        return (1.+x)*log(1.+x)/log(2)+(1.-x)*log(1.-x)/log(2.) - 2.*(h2-h3 + 1. );
    }

    public static double espCounter(double epsilon, double h3, double h4){
        double a, b, c;
        a = -0.9999999;
        b = 0.9999999;
        while (b - a > epsilon){
            c = (a + b) / 2;
            if(func(b, h3, h4) * func(c,h3, h4) < 0)
                a = c;
            else
                b = c;
        }
        return (a + b) / 2 ;
    }
    public static double espCounter(double epsilon, double h2, double h3, double h4){
        double a, b, c;
        a = -0.9999999;
        b = 0.9999999;
        while (b - a > epsilon){
            c = (a + b) / 2;
            if(func(b,h2, h3, h4) * func(c,h2,h3, h4) < 0)
                a = c;
            else
                b = c;
        }
        return (a + b) / 2 ;
    }

    public static double countDelta(double eps, double h3, double h4){//через h3h4
        return (3.*h3-2.*h4-1)/(3*eps*log((1.+eps)/(1.-eps))/log(2.));

    }
    public static double countDelta(double eps,double h2, double h3, double h4){//через h3h4
        return (h2+h3-h4-1)/(2.5*eps*log((1.+eps)/(1.-eps))/log(2.));
    }



   /* public static double countDelta(double eps, double h2, double h3){//черезh2h3
        return (2.*h2-h3-1.)/(2.*eps*log((1.+eps)/(1.-eps))/log(2.));

    }*/

    public static void main(String[] args) {
        loopMethod();
        //one();

    }

    private static void one(){
        double e =0.00000001;
        double h4, h3, h2 ,x, delta, f, eps=-0.15, d=0.25;
        double h4o=0, h3o=0, h2o=0;

            Initializator init = new Initializator(eps, d, 10000000);
            Counter cnt = new Counter(init, 4);
            h4 = cnt.showEmpiricEntropy(4) * 4;
            cnt.setDecimal(cnt.bitShift(cnt.getDecimal()));
            h3 = cnt.showEmpiricEntropy(3) * 3;
            cnt.setDecimal(cnt.bitShift(cnt.getDecimal()));
            h2 = cnt.showEmpiricEntropy(2) * 2;


            double h2r = h2o/100, h3r=h3o/100, h4r=h4o/100;

                        System.out.println("h2 " + h2 + " h2_ " + h2(eps, d));
                        System.out.println("h3 " + h3 + " h3_ " + h3(eps, d));
                        System.out.println("h4 " + h4 + " h4_ " + h4(eps, d));
                        x = espCounter(e, h2r, h3r);
                        f=func(x, h2r, h3r);
                        delta=countDelta(x,h2r,h3r);
                        System.out.println("eps " + x);
        System.out.println("detla " + delta);


    }

    private static void loopMethod() {
        double e =0.00000001;
        double h4, h3, h2 ,x, delta, f;
        try {
            PrintWriter pw = new PrintWriter(new File("DICHOTOM.csv"));

        for (int i : T) {
            for (double v : EPS) {
                for (double d : DELT) {
                    x=0.;
                    delta=0.;
                   // pw.println("T= " + i + " eps " + v + " delta " + d );
                    Initializator init = new Initializator(v,d,i);
                    Counter cnt = new Counter(init, 4);
                    h4 = cnt.showEmpiricEntropy(4)*4;
                    cnt.setDecimal(cnt.bitShift(cnt.getDecimal()));
                    h3 = cnt.showEmpiricEntropy(3)*3;
                    cnt.setDecimal(cnt.bitShift(cnt.getDecimal()));
                    h2 = cnt.showEmpiricEntropy(2)*2;

                    System.out.println("h2 " + h2);
                    System.out.println("h3 " + h3);
                    System.out.println("h4 " + h4);
                    System.out.println("eps " + espCounter(e, h3, h4));
                    x = espCounter(e, h2, h3,h4);
                    f=func(x, h2, h3);
                    delta=countDelta(x,h2,h3,h4);
                    pw.println("T; " + i + "; eps ;" + v + "; delta; " + d +";h2;" + h2  +";h3;" + h3 + ";h4;" + h4 + ";eps_0;" + x+ ";delta;" + delta  + "; DELTA; " + abs(delta - d));
                    System.out.println("eps_0 " + x);
                }
            }
        }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    private static double h2(double e, double d ){
        return -0.5*(1.+e)* log(1.+e)/log(2.)-0.5*(1.-e)* log(1.-e)/log(2.) + 2. + 2*d*e*log((1.+e)/(1.-e))/log(2.);
    }
    private static double h3(double e, double d ){
        return -(1.+e)* log(1.+e)/log(2.)-(1.-e)* log(1.-e)/log(2.) + 3. + 2*d*e*log((1.+e)/(1.-e))/log(2.);
    }
    private static double h4(double e, double d ){
        return -1.5*(1.+e)* log(1.+e)/log(2.)-1.5*(1.-e)* log(1.-e)/log(2.) + 4. + 1.5*d*e*log((1.+e)/(1.-e))/log(2.);
    }

}
