package runners;

import worked.Counter;
import worked.Initializator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.pow;

/**
 * Created by Andrey on 13.12.2015.
 */
public class GraphicH3 {
    private static int[] L = {3, 4} ;
    private static double[] eps = {0.55};
    private static double[] delta = { 0., 0.01, 0.02, 0.05, 0.1, 0.2 };
    private static int[] T = {1000};
    private static int M = 50;

    public static void main(String[] args) {
        empiricGraphics();
//        realH3();
    }

    static void realH3(){

                for (double e : eps) {
                    for (double d : delta) {
                        System.out.println("eps= " + e + "delta " + d + " entr " + real3_1(e,d)/3.);
                    }
                }

    }

    public static double real3_1 (double eps, double delta ){

        double res = 0.;
        double p111 = 1./8. *(2.*pow(delta,3.) + pow(delta,2.)*(4.*pow(eps,2.) - 3.) + delta*(2.-8.*pow(eps, 2.)) + 4.*pow(eps, 2.) );
        double p110 =1./8. *(2.*pow(delta,3.) + pow(delta,2.)*(4.*eps*(1.-eps) ) + 2.* delta*pow(2.*eps - 1., 2) + 4.*eps*(1.-eps) );
        double p101 =1./8. *(2.*pow(delta,3.) + pow(delta,2.)*(4.*pow(eps,2.) - 8.*eps + 1.) + delta*(16.*eps-8.*pow(eps, 2.)-6.) + 4.*pow(1.-eps, 2.) );
        double p011 =p110;
        System.out.println(p111+ " " +nLogN(p111));
        System.out.println(p110+ " " +nLogN(p110));
        System.out.println(p101+ " " +nLogN(p101));
        res = 2*(nLogN(p111) + nLogN(p110) +nLogN(p101) +nLogN(p011));
        return res;

    }
    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * StrictMath.log(x) / StrictMath.log(2);
    }


    private static void empiricGraphics() {
        try {
            PrintWriter pw = new PrintWriter("LinearRegressionData.csv");
            for( int l : L) {
                pw.println("L=" + l);
                for (double e : eps) {
                    for (double d : delta) {
                        for(int len: T) {
                            double ent = 0.;
                            for (int i = 0; i < M; i++) {
                                Initializator init = new Initializator(e, d, len);
                                Counter counter = new Counter(init, l);
                                ent+=counter.showEmpiricEntropy(l);
                            }
                            pw.println("T;" + len + ";eps;" + e + "; delta;" + d + ";entropy;" + ent/10.);
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

    public static double real4_2 (double eps, double delta ){
        double res = 0.;
        double p0000 = 1./16. *(pow(delta, 4.)*(4.*pow(eps,2.)-6.*eps + 4.) + pow(delta, 3.)*(-16.*eps*eps+20.*eps - 7.) + delta*delta*(8.*pow(eps,3.) + 20.*pow(eps, 2.) -22.*eps + 4.) + delta*(-16.*pow(eps, 3.) - 8.*eps*eps+ 8.*eps) + 8.*pow(eps,3.));
        double p0001 = 1./16. *(pow(delta, 4.)*(-4.*pow(eps,2.)+4.*eps + 2.) + pow(delta, 3.)*(16.*eps*eps-16.*eps + 1.) + delta*delta*(-8.*pow(eps,3.) - 12.*pow(eps, 2.) +20.*eps - 6.) + delta*(16.*pow(eps, 3.) - 8.*eps*eps - 8.*eps + 4.) - 8.*pow(eps,3.) + 8.*eps*eps);
        double p0010 = 1./16. *(pow(delta, 4.)*(-2.*pow(eps,2.)+2.*eps + 2.) + pow(delta, 3.)*(12.*eps*eps-12.*eps + 1.) + delta*delta*(8.*pow(eps,3.) - 34.*pow(eps, 2.) +26.*eps - 6.) + delta*(-16.*pow(eps, 3.) + 40.*eps*eps - 24.*eps + 4.) + 8.*pow(eps,3.) - 16.*eps*eps + 8.*eps);
        double p0100 = 1./16. *(pow(delta, 4.)*(4.*pow(eps,3.)-12.*pow(eps,2.)+8.*eps + 2.) + pow(delta, 3.)*(-12.*pow(eps,3.)+ 40.*eps*eps-28.*eps + 1.) + delta*delta*(20.*pow(eps,3.) - 60.*pow(eps, 2.) +40.*eps - 6.) + delta*(-20.*pow(eps, 3.) + 48.*eps*eps - 28.*eps + 4.) + 8.*pow(eps,3.) - 16.*eps*eps + 8.*eps);
        double p1000 = 1./16. *(pow(delta, 4.)*(-4.*pow(eps,2.)+4.*eps + 2.) + pow(delta, 3.)*(16.*eps*eps-16.*eps + 1.) + delta*delta*(-8.*pow(eps,3.) - 12.*pow(eps, 2.) +20.*eps - 6.) + delta*(16.*pow(eps, 3.) - 8.*eps*eps - 8.*eps + 4.) - 8.*pow(eps,3.) + 8.*eps*eps);

        double p1100 = 1./16. *(pow(delta, 4.)*(4.*pow(eps,2.)-4.*eps + 4.) + pow(delta, 3.)*( -16.*eps*eps +16.*eps -7.) + delta*delta*(-8.*pow(eps,3.) + 28.*pow(eps, 2.) -20.*eps + 4.) + delta*(16.*pow(eps, 3.) -24.*eps*eps + 8.*eps ) - 8.*pow(eps,3.) + 8.*eps*eps );

        double p0110 = 1./16. *(pow(delta, 4.)*(4.*pow(eps,2.)-4.*eps + 4.) + pow(delta, 3.)*(-16.*eps*eps+16.*eps - 7.) + delta*delta*(8.*pow(eps,3.) +4.*pow(eps, 2.) -12.*eps  + 4.) + delta*(-16.*pow(eps, 3.) + 24.*eps*eps - 8.*eps ) + 8.*pow(eps,3.) - 16.*eps*eps + 8.*eps);
        double p1010 = 1./16. *(pow(delta, 4.)*(4.*pow(eps,2.)-4.*eps + 4.) + pow(delta, 3.)*(-16.*eps*eps+16.*eps - 7.) + delta*delta*(-8.*pow(eps,3.) + 44.*pow(eps, 2.)  - 44.*eps + 12.) + delta*(16.*pow(eps, 3.) -56.*eps*eps + 56.*eps  - 16.) - 8.*pow(eps,3.) + 24.*eps*eps  -24.*eps + 8.);
        res = 2.*( nLogN(p0000) +nLogN(p1000) +nLogN(p0100) +nLogN(p0010) +nLogN(p0001) +nLogN(p1100) +nLogN(p0110) +nLogN(p1010));
        return res;

    }
}
