package runners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.*;

/**
 * Created by Andrey on 12.12.2015.
 */
public class TestH3 {
    private static int[] L = {3,4} ;
    private static double e = 0.35;
    private static double delta0=0.01;
    private static double step=0.01;
    private static double deltaLast=0.52;

    private static double[] delta = {0.004, 0.008, 0.01, 0.015, 0.02, 0.025, 0.03, 0.035, 0.04 };
    private static int T = 10000;
    private static int M = 100;


    public static void main(String[] args) {
        System.out.println(real2_1(0.33,0.));
        System.out.println(real3_1(0.33,0.));

        //assimptGraphics();
        //assimptGraphics2();
    }

    private static void assimptGraphics() {
        try {
            PrintWriter pw = new PrintWriter(new File("resH3.csv"));
            double ass = 0;
            double real = 0;
            double minus = 0;

                for (double d = delta0; d <= deltaLast; d+=step ) {
                    ass = assimptotic_3(e, d);
                    real = real3_1(e,d);
                    minus = real-ass;

                    pw.println( "delta;" + d + ";assimptotic;   " + ass + "    ;RealEntropy3_1;     " + real +
                            "    ;Minus ;     " + minus);
                }

            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }

    public static double ost2(double eps, double delt){
        return (2.*pow((1.-delt),2.)*pow(eps, 2.))*delt/((pow((1.-delt),4)*pow(eps,2.)-1.));
    }

    private static void assimptGraphics2() {
        try {
            PrintWriter pw = new PrintWriter(new File("resH2.csv"));
            double ass = 0;
            double real = 0;
            double minus = 0;
            double ost =0;
            for (double d = delta0; d <= deltaLast; d+=step ) {
                ass = assimptotic_2(e, d);
                real = real2_1(e,d);
                minus = real-ass;
                ost=ost2(e,d*0.5);
                pw.println( "delta;" + d + ";assimptotic;   " + ass + "    ;RealEntropy3_1;     " + real +
                        "    ;Minus ;     " + minus+";OST;"+ost);
            }

            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
    public static double assimptotic_3 (double eps, double delta ){
        double res = 0.;
        res = - ((1.-eps)*log(1.-eps)/log(2.)+(1.+eps)*log(1.+eps)/log(2.) +log(0.125)/log(2.) + 2.*delta*eps*log((1.-eps)/(1.+eps))/log(2.));
        return res;

    }


    public static double real3_1 (double eps, double delta ){

        double res = 0.;
        double p111 = 1./8. *(  pow(delta,2.)*(pow(eps,2.) + 2*eps) - delta*(2.*pow(eps, 2.)+4.*eps) + pow((1.+eps), 2.) );
        double p110 =1./8. *( pow(delta,2.)*(-pow(eps, 2.) ) + 2.* delta*pow(eps, 2.) + 1.-pow(eps,2.) );
        double p101 =1./8. *(  pow(delta,2.)*(pow(eps,2.) - 2*eps) - delta*(2.*pow(eps, 2.)-4.*eps) + pow((1.-eps), 2.) );
        double p011 =p110;
        res = 2*(nLogN(p111) + nLogN(p110) +nLogN(p101) +nLogN(p011));
        return res;

    }


    public static double assimptotic_2 (double eps, double delta ){
        double res = 0.;
        res = - 0.5*((1.-eps)*log(0.25*(1.-eps))/log(2.)+(1.+eps)*log(0.25*(1.+eps))/log(2.)  - 2.*delta*eps*log((1.+eps)/(1.-eps))/log(2.));
        return res;

    }

    public static double assimptotic_22 (double eps, double delta ){
        double res = 0.;
        res = - 0.5*((1.-eps)*log(1.-eps)/log(2.)+(1.+eps)*log(1.+eps)/log(2.) -4*eps - 2.*delta*eps*log((1.+eps)/(1.-eps))/log(2.));
        return res;

    }


    public static double real2_1 (double eps, double delta ){

        double res = 0.;
        double p00 = (  pow(delta,2.)*(eps*0.25) - delta*(0.5*eps) + 0.25*(1.+eps) );

        double p10 =(  -pow(delta,2.)*(eps*0.25) + delta*(0.5*eps) + 0.25*(1.-eps) );

        res = 2*(nLogN(p00) + +nLogN(p10) );
        return res;

    }
    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * StrictMath.log(x) / StrictMath.log(2);
    }
}
