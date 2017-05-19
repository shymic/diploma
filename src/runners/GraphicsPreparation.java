package runners;

import worked.Counter;
import worked.Initializator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * Created by Andrey on 17.05.2017
 */
public class GraphicsPreparation {
    private static int[] L = {2,3,4} ;
    private static double[] eps = {0.25};
    private static double[] delta = { 0.3, 0.1, 0.05, 0. };
    private static int[] T = {100, 1000, 10000, 100000, 1000000};
    private static int M = 100;

    private static double h2(double e, double d ){
        return -0.5*(1.+e)* log(1.+e)/log(2.)-0.5*(1.-e)* log(1.-e)/log(2.) + 2. + 2*d*e*log((1.+e)/(1.-e))/log(2.);
    }
    private static double h3(double e, double d ){
        return -(1.+e)* log(1.+e)/log(2.)-(1.-e)* log(1.-e)/log(2.) + 3. + 2*d*e*log((1.+e)/(1.-e))/log(2.);
    }
    private static double h4(double e, double d ){
        return -1.5*(1.+e)* log(1.+e)/log(2.)-1.5*(1.-e)* log(1.-e)/log(2.) + 4. + 1.5*d*e*log((1.+e)/(1.-e))/log(2.);
    }

    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * StrictMath.log(x) / StrictMath.log(2);
    }

    public static double real2 (double eps, double delta ){

        double res = 0.;
        double p00 = (  pow(delta,2.)*(eps*0.25) - delta*(0.5*eps) + 0.25*(1.+eps) );

        double p10 =(  -pow(delta,2.)*(eps*0.25) + delta*(0.5*eps) + 0.25*(1.-eps) );

        res = 2*(nLogN(p00) + +nLogN(p10) );
        return res;

    }


    public static double real3 (double eps, double delta ){

        double res = 0.;
        double p111 = 1./8. *(  pow(delta,2.)*(pow(eps,2.) + 2*eps) - delta*(2.*pow(eps, 2.)+4.*eps) + pow((1.+eps), 2.) );
        double p110 =1./8. *( pow(delta,2.)*(-pow(eps, 2.) ) + 2.* delta*pow(eps, 2.) + 1.-pow(eps,2.) );
        double p101 =1./8. *(  pow(delta,2.)*(pow(eps,2.) - 2*eps) - delta*(2.*pow(eps, 2.)-4.*eps) + pow((1.-eps), 2.) );
        double p011 =p110;
        res = 2*(nLogN(p111) + nLogN(p110) +nLogN(p101) +nLogN(p011));
        return res;

    }

    public static double real4 (double eps, double delta ){
        double res = 0.;
        double p0000 = 1./16. *(pow(delta, 4.)*pow(eps,2.) + pow(delta, 3.)*(-4.*eps*eps) + delta*delta*(pow(eps,3.) + 8.*pow(eps, 2.) +3.*eps ) + delta*(-2.*pow(eps, 3.) - 8.*eps*eps-6.*eps) + pow(eps,3.) + 3.*eps*eps + 3.*eps +1.);
        double p0001 = 1./16. *(pow(delta, 4.)*(-pow(eps,2.)) + pow(delta, 3.)*(4.*eps*eps) + delta*delta*(-pow(eps,3.) - 6.*pow(eps, 2.) +eps ) + delta*(2.*pow(eps, 3.) +4.*eps*eps - 2.*eps ) - pow(eps,3.) -eps*eps + eps +1.);
        double p0010 = 1./16. *(pow(delta, 4.)*(-pow(eps,2.)) + pow(delta, 3.)*(4.*eps*eps) + delta*delta*(pow(eps,3.) - 6.*pow(eps, 2.) -eps ) + delta*(-2.*pow(eps, 3.) + 4.*eps*eps + 2.*eps ) + pow(eps,3.) - eps*eps -eps+1.);
        double p0100 = p0010;
        double p1000 = p0001;

        double p1100 = 1./16. *(pow(delta, 4.)*(pow(eps,2.)) + pow(delta, 3.)*( -4.*eps*eps) + delta*delta*(-pow(eps,3.) + 4.*pow(eps, 2.) +eps ) + delta*(2.*pow(eps, 3.) -2.*eps  ) - pow(eps,3.) -eps*eps + eps +1.);

        double p0110 = 1./16. *(pow(delta, 4.)*(pow(eps,2.)) + pow(delta, 3.)*(-4.*eps*eps) + delta*delta*(pow(eps,3.) +4.*pow(eps, 2.) -eps) + delta*(-2.*pow(eps, 3.) + 2.*eps ) + pow(eps,3.) - eps*eps -eps+1.);
        double p1010 = 1./16. *(pow(delta, 4.)*(pow(eps,2.)) + pow(delta, 3.)*(-4.*eps*eps) + delta*delta*(-pow(eps,3.) + 8.*pow(eps, 2.)  -3.*eps) + delta*(2.*pow(eps, 3.) -8.*eps*eps + 6.*eps ) - pow(eps,3.) + 3.*eps*eps  -3.*eps + 1.);
        res = 2.*( nLogN(p0000) +nLogN(p1000) +nLogN(p0100) +nLogN(p0010) +nLogN(p0001) +nLogN(p1100) +nLogN(p0110) +nLogN(p1010));
        return res;

    }

    public static void main(String[] args) {
        System.out.println(real2(0.35, 0.1)/2);
        System.out.println(real2(0.35, 0.3)/2);
        System.out.println(real2(0.35, 0.5)/2);
        System.out.println(h2(0.35, 0.1)/2);
        System.out.println(h2(0.35, 0.3)/2);
        System.out.println(h2(0.35, 0.5)/2);
    }


    private static void empiricGraphics() {
        try {
            PrintWriter pw = new PrintWriter("GraphicsData.csv");
            PrintWriter pw2 = new PrintWriter("GraphicsData2.csv");
            double empEnthropy=0.;
            for( int l : L) {
                pw.println("L=" + l);
                for (double e : eps) {
                    for (double d : delta) {
                        for(int len: T) {
                            empEnthropy=0.;
                            double ent = 0.;
                            for (int i = 0; i < M; i++) {
                                Initializator init = new Initializator(e, d, len);
                                Counter counter = new Counter(init, l);
                                empEnthropy=counter.showEmpiricEntropy(l);
                                ent+=empEnthropy;
                                pw2.println("L;" + l + ";T;" + len + ";eps;" + e + "; delta;" + d + ";entropy;" + empEnthropy);
                            }
                            pw.println("L;" + l + ";T;" + len + ";eps;" + e + "; delta;" + d + ";entropy;" + ent/M);
                        }
                    }
                }
            }
            pw.flush();
            pw.close();
            pw2.flush();
            pw2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
