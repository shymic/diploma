package test_assimptotics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static java.lang.Math.log;
import static java.lang.Math.pow;

/**
 * Created by Shymko_A on 14.12.2016.
 */
public class teat {
    private static double e = 0.35;
    private static double delta0=0.01;
    private static double step=0.01;
    private static double deltaLast=0.52;
    public static void main(String[] args) {
        //double e=0.12;
        double delta=0.05;
        System.out.println(real1010(e, delta)+real0000(e, delta)+real0001(e, delta)+real0010(e, delta)+real0100(e, delta)
                + real1000(e, delta)+real0011(e, delta)+real0110(e, delta));
        System.out.println(count1010(e, delta)+count0000(e, delta)+count0001(e, delta)+count0010(e, delta)+count0100(e, delta)+
                count1000(e, delta)+count0011(e, delta)+count0110(e, delta));
       System.out.println("0000");
        System.out.println(real0000(e, delta));
        System.out.println(count0000(e, delta));
        System.out.println("0001");
        System.out.println(real0001(e, delta));
        System.out.println(count0001(e, delta));
        System.out.println("0010");
        System.out.println(real0010(e, delta));
        System.out.println(count0010(e, delta));
        System.out.println("0100");
        System.out.println(real0100(e, delta));
        System.out.println(count0100(e, delta));
        System.out.println("1000");
        System.out.println(real1000(e, delta));
        System.out.println(count1000(e, delta));
        System.out.println("0011");
        System.out.println(real0011(e, delta));
        System.out.println(count0011(e, delta));
        System.out.println("0110");
        System.out.println(real0110(e, delta));
        System.out.println(count0110(e, delta));
        System.out.println("1010");
        System.out.println(real1010(e, delta));
        System.out.println(count1010(e, delta));

        System.out.println(assimptotic4(e, delta));
        System.out.println(real2(e, delta));
        assimptGraphics();

    }


    private static void assimptGraphics() {
        try {
            PrintWriter pw = new PrintWriter(new File("resH4.csv"));
            double ass = 0;
            double real = 0;
            double minus = 0;
            double ost = 0;
            for (double d = delta0; d <= deltaLast; d += step) {
                ass = assimptotic4(e, d);
                real = real2(e, d);
                minus = real - ass;
                ost=ost2(e,d);
                pw.println("delta;" + d + ";assimptotic;   " + ass + "    ;RealEntropy3_1;     " + real + "    ;Minus ;     " + minus+";OST;"+ost);
            }

            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static double real(double ep, double de){
        return (-2.*(real0000(ep,de)*log(real0000(ep,de))/log(2.) +
                real0001(ep,de)*log(real0001(ep,de))/log(2.) +
                real0010(ep,de)*log(real0010(ep,de))/log(2.) +
                real0100(ep, de)*log(real0100(ep, de))/log(2.) +
                real1000(ep, de)*log(real1000(ep, de))/log(2.) +
                real0011(ep, de)*log(real0011(ep, de))/log(2.) +
                real0110(ep, de)*log(real0110(ep, de))/log(2.) +
                real1010(ep,de)*log(real1010(ep,de))/log(2.)));
    }

    public static double real2(double ep, double de){
        return (2.*(nLogN(real0000(ep,de))+
                2.*nLogN(real0001(ep,de))+
                2.*nLogN(real0010(ep,de))+
                nLogN(real0011(ep,de))+
                nLogN(real0110(ep,de))+
                nLogN(real1010(ep,de))));
    }

    public static double real0000(double eps, double d){
        return (pow((1.- d), 4.)*pow((1.+ eps), 3.) +
                d*pow((1.- d), 3.)*(2.*pow((1.+eps), 2.) + 2.*(1+eps)*(1+pow(eps,2.))) +
                pow(d,2.)*pow((1.- d), 2.)*(3.*(1.+eps)+2*(1.+pow(eps, 2)) + 1+ pow(eps, 3.))
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count0000(double eps, double d){
        return (pow(d,4.)*pow(eps, 2.) +
                pow(d,3.)*(-4.)*pow(eps, 2.) +
                pow(d,2.)*(pow(eps, 3.) + 8.*pow(eps, 2.) + 3.*eps ) +
                d*(-2.*pow(eps, 3.)-8.*pow(eps, 2.) -6.*eps ) +
                pow(eps, 3.) +3.*pow(eps, 2.) + 3. * eps +1.
        )/16.;
    }

    public static double real0001(double eps, double d){
        return (pow((1.- d), 4.)*(pow((1.+ eps), 2.)*(1.-eps)) +
                d*pow((1.- d), 3.)*(pow((1.+eps), 2.) + (1.+eps)*(1-pow(eps,2.))+(1.-eps)*(1.+pow(eps,2.))+(1.-eps)*(1.+eps)) +
                pow(d,2.)*pow((1.- d), 2.)*(-pow(eps,3.)+eps+6.)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count0001(double eps, double d){
        return (pow(d,4.)*(-pow(eps, 2.)) +
                pow(d,3.)*(4.)*pow(eps, 2.) +
                pow(d,2.)*(-pow(eps, 3.) -6.*pow(eps, 2.) + eps ) +
                d*(2.*pow(eps, 3.)+4.*pow(eps, 2.) -2.*eps ) -
                pow(eps, 3.) -pow(eps, 2.) +  eps +1.
        )/16.;
    }

    public static double real0010(double eps, double d){
        return (pow((1.- d), 4.)*(pow((1.- eps), 2.)*(1.+eps)) +
                d*pow((1.- d), 3.)*(pow((1.-eps), 2.) + (1+eps)*(1+pow(eps,2.))+ (1-eps)*(1-pow(eps,2.)) + (1.-eps)*(1.+eps)) +
                pow(d,2.)*pow((1.- d), 2.)*(pow(eps, 3.) -eps + 6.)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count0010(double eps, double d){
        return (pow(d,4.)*(-pow(eps, 2.)) +
                pow(d,3.)*(4.*pow(eps, 2.)) +
                pow(d,2.)*(pow(eps, 3.) -6.*pow(eps, 2.) - eps ) +
                d*(-2.*pow(eps, 3.)+4.*pow(eps, 2.) +2.*eps ) +
                pow(eps, 3.) -pow(eps, 2.) -eps +1.
        )/16.;
    }

    public static double real0100(double eps, double d){
        return (pow((1.- d), 4.)*(pow((1.- eps), 2.)*(1.+eps)) +
                d*pow((1.- d), 3.)*(pow((1.-eps), 2.) + (1+eps)*(1+pow(eps,2.))+ (1-eps)*(1-pow(eps,2.)) + (1.-eps)*(1.+eps)) +
                pow(d,2.)*pow((1.- d), 2.)*(pow(eps, 3.) -eps + 6.)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count0100(double eps, double d){
        return (pow(d,4.)*(-pow(eps, 2.)) +
                pow(d,3.)*(4.*pow(eps, 2.)) +
                pow(d,2.)*(pow(eps, 3.) -6.*pow(eps, 2.) - eps ) +
                d*(-2.*pow(eps, 3.)+4.*pow(eps, 2.) +2.*eps ) +
                pow(eps, 3.) -pow(eps, 2.) -eps +1.
        )/16.;
    }


    public static double real1000(double eps, double d){
        return (pow((1.- d), 4.)*pow((1.+ eps), 2.)*(1.-eps) +
                d*pow((1.- d), 3.)*(pow((1.+eps), 2.) + (1.-eps)*(1+pow(eps,2.))+ (1.+eps)*(1-pow(eps,2.)) + (1.-eps)*(1.+eps)) +
                pow(d,2.)*pow((1.- d), 2.)*(-pow(eps, 3.) +eps+6.)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count1000(double eps, double d){
        return (pow(d,4.)*(-1.)*pow(eps, 2.) +
                pow(d,3.)*(4.)*pow(eps, 2.) +
                pow(d,2.)*(-pow(eps, 3.) - 6.*pow(eps, 2.) + eps ) +
                d*(2.*pow(eps, 3.)+4.*pow(eps, 2.) -2.*eps ) -
                pow(eps, 3.) -pow(eps, 2.) +  eps +1.
        )/16.;
    }


    public static double real0011(double eps, double d){
        return (pow((1.- d), 4.)*pow((1.+ eps), 2.)*(1.-eps) +
                d*pow((1.- d), 3.)*(2.*(1.-pow(eps, 2.))*(1.+eps) + 2.*(1.-eps)*(1.+eps)) +
                pow(d,2.)*pow((1.- d), 2.)*(-pow(eps, 3.) - 2.*pow(eps, 2.)+eps+6.)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count0011(double eps, double d){
        return (pow(d,4.)*pow(eps, 2.) +
                pow(d,3.)*(-4.)*pow(eps, 2.) +
                pow(d,2.)*(-pow(eps, 3.) +4.*pow(eps, 2.) + eps ) +
                d*(2.*pow(eps, 3.) -2.*eps ) -
                pow(eps, 3.) -pow(eps, 2.) +  eps +1.
        )/16.;
    }

    public static double real0110(double eps, double d){
        return (pow((1.- d), 4.)*pow((1.- eps), 2.)*(1.+eps) +
                d*pow((1.- d), 3.)*(2.*(1.-pow(eps, 2.))*(1.-eps) + 2.*(1.-eps)*(1.+eps)) +
                pow(d,2.)*pow((1.- d), 2.)*(pow(eps, 3.) - 2.*pow(eps, 2.)-eps+6.)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count0110(double eps, double d){
        return (pow(d,4.)*pow(eps, 2.) +
                pow(d,3.)*(-4.)*pow(eps, 2.) +
                pow(d,2.)*(pow(eps, 3.) +4.*pow(eps, 2.) - eps ) +
                d*(-2.*pow(eps, 3.) +2.*eps ) +
                pow(eps, 3.) -pow(eps, 2.) -  eps +1.
        )/16.;
    }

    public static double real1010(double eps, double d){
        return (pow((1.- d), 4.)*pow((1.- eps), 3.) +
                d*pow((1.- d), 3.)*(2.*pow((1.-eps), 2.) + 2.*(1-eps)*(1+pow(eps,2.))) +
                pow(d,2.)*pow((1.- d), 2.)*(-pow(eps, 3.)+2.*pow(eps,2.)-3.*eps+6)
                + 4.*pow(d, 3.)*(1.-d) + pow(d,4.) )/16.;
    }

    public static double count1010(double eps, double d){
        return (pow(d,4.)*pow(eps, 2.) +
                pow(d,3.)*(-4.)*pow(eps, 2.) +
                pow(d,2.)*(-pow(eps, 3.) + 8.*pow(eps, 2.) - 3.*eps ) +
                d*(2.*pow(eps, 3.)-8.*pow(eps, 2.) +6.*eps ) -
                pow(eps, 3.) +3.*pow(eps, 2.) - 3. * eps +1.
        )/16.;
    }


    public static double assimptotic4(double eps, double delt){
        double h4_0=-((pow((1.+eps),3.)/16.)*log(pow((1.+eps),3.)/16.)/log(2.)+
                3.* (pow((1.+eps),2.)*(1.-eps)/16.)*log(pow((1.+eps),2.)*(1.-eps)/16.)/log(2.) +
                3.* (pow((1.-eps),2.)*(1.+eps)/16.)*log(pow((1.-eps),2.)*(1.+eps)/16.)/log(2.)+
                (pow((1.-eps),3)/16.)*log(pow((1.-eps),3)/16.)/log(2.));

        return 2.*(h4_0 + delt*1.5 * eps *log((1.+eps)/(1-eps))
        );
    }

    public static double ost2(double eps, double delt){
        return (-2.*pow((1.-delt),2.)*pow(eps, 2.))/(pow((1.-delt),4)*pow(eps,2.)-1.);
    }


    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * StrictMath.log(x) / StrictMath.log(2);
    }


}
