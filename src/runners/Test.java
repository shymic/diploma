package runners; /**
 * Created by Andrey on 21.04.2015.
 */
import static java.lang.Math.*;
public class Test {
    public static void main(String[] args) {
        double[] eps = {0.1, 0.3};
        double[] delta = {0.5, 0.33, 0.25, 0.1, 0.05, 0.03, 0.01 ,0.005, 0.0005};
        for (double ep : eps) {
            for (double v : delta) {
                System.out.println("delta:     " + v + "      eps:       " + ep + "      assimptotic:      " + assimptotic4_5(ep, v) + "        real:       " + real4_1(ep, v) );
            }
            System.out.println();
        }
    }
    public static double assimptotic( double eps, double delta){
        return ( -0.5*( log(0.5*(1.-eps))/log(2.) + delta* (2.*log(eps/2.)/log(2.) + (2.*eps +1.)/(1.-eps) -2.*log((1-eps)/2.)) +
                delta*delta *((4.*eps + 2.)/eps - log(eps/2.)/log(2.) - (eps*eps - 0.5*eps)/((1.-eps)*(1.-eps))+ log((1.-eps)/2.)/log(2.) -
                        (4.*eps + 2.)/(1.-eps) )));
    }

    public static double assimptotic2( double eps, double delta){
        return -2.*(0.5*eps * log(eps)/log(2.) + 0.5*(1.-eps) * log(1.-eps)/log(2.) -0.5  + delta *
                (0.5*(1.-2.*eps)*log(eps)/log(2.) - 0.5 *(1.-2.*eps)*log(1.-eps)/log(2.) ));
    }

    public static double assimptotic3( double eps, double delta){
        return -(2.* (eps * log(eps)/log(2.) + (1-eps)*log(1.-eps)/log(2.) -0.5  +
                delta*((1.-2.*eps)*log(eps)/log(2.) +
                        (-1.+2.*eps)*log(1.-eps)/log(2.) +
                        (4.*eps*eps - 4.* eps + 1)/(2.*log(2.)))
        )
        );
    }

    public static double assimptotic4_2( double eps, double delta){
        return -(2.* (1.5*eps * log(eps)/log(2.) + 1.5*(1-eps)*log(1.-eps)/log(2.) -0.5  +
                delta*(1.5*(1.-2.*eps)*log(eps)/log(2.) +
                        1.5*(-1.+2.*eps)*log(1.-eps)/log(2.) +
                        (1.-2.*eps)*(1.-2.*eps)*(1.-2.*eps)/(2.*log(2.)))
        )
        );
    }

    public static double assimptotic4_3( double eps, double delta){
        return -(2.* (1.5*eps * log(eps)/log(2.) + 1.5*(1-eps)*log(1.-eps)/log(2.) -0.5  +
                delta*((1.-eps)*log(eps)/log(2.) +
                        (-1.75+4.*eps)*log(1.-eps)/log(2.) +
                        (-2.*eps*eps*eps-eps)/(2.*log(2.)) - eps - 0.25)
        )
        );
    }

    public static double assimptotic4_4( double eps, double delta) {
        return -(2. * (1.5 * eps * log(eps) / log(2.) + 1.5 * (1 - eps) * log(1. - eps) / log(2.) - 0.5 +
                delta * ((1. - 3. * eps) * log(eps) / log(2.) +
                        (-1.75 + 3. * eps) * log(1. - eps) / log(2.) + 0.25 + (1. - 2. * eps) / (4. * log(2.)))
        )
        );
    }

    public static double assimptotic4_5( double eps, double delta){
        return -(2.* (1.5*eps * log(eps)/log(2.) + 1.5*(1-eps)*log(1.-eps)/log(2.) -0.5  +
                delta*((2.-4.*eps)*log(eps)/log(2.) +
                        (-1.25+3.*eps)*log(1.-eps)/log(2.) - 0.25 + 1./(4.*log(2.)))
        )
        );
    }

    public static double assimptotic5_2( double eps, double delta){
        return -(2.* (1.5*eps * log(eps)/log(2.) + 1.5*(1-eps)*log(1.-eps)/log(2.) -0.5  +
                delta*((1.-2.*eps)*(1.-2.*eps)*log(eps)/log(2.) +
                        1.5*(-1.+2.*eps)*log(1.-eps)/log(2.) +
                        1.5*(4.*eps*eps - 4.* eps + 1)/(2.*log(2.)))
        )
        );
    }

    public static double assimptotic4( double eps, double delta){
        return -(2.* (-0.5 + 1.5*(1.-eps)*log(1.-eps)/log(2.) + 1.5*eps*log(eps)/log(2.) +
                delta * ((20.*pow(eps,3.) -12.*pow(eps, 2.) -6.*eps - 1.)/(4.*log(2.)) +
                        (4.*eps*eps*eps -5.5*eps*eps - 1.5*eps +1.5)*log(eps)/log(2.) +
                        (-eps*eps*eps + 4.5*eps*eps - 0.5*eps -1.5) * log(1.-eps)/log(2.) +
                        (-eps*eps*eps + 0.5*eps*eps + 1.5*eps -3.))));
    }



    public static double real (double eps, double delta ){
        double res = 0.;
        double p11 = 0.5 * eps * ( 1. - delta)*( 1. - delta) + 0.5 *delta*(1.-delta) + delta*delta/4.;
        double p10 = 0.5 * (1. - eps) * ( 1. - delta)*( 1. - delta) + 0.5 * delta * ( 1. - delta)+delta*delta/4.;
        double p01 = p10;
        double p00 = 0.5 * eps * ( 1. - delta)*( 1. - delta) +  delta * ( 1. - delta)*0.5 + delta*delta/4.;
        res = nLogN(p11) + nLogN(p00) + nLogN(p10) + nLogN(p01);
        return res;

    }

    public static double real3 (double eps, double delta ){
        double res = 0.;
        double p111 =(1.-3.*delta) * eps*eps/2.+ delta*(eps/2. + (eps*eps+(1.-eps)*(1.-eps))/4.);
        double p110 =(1.-3.*delta) * eps*(1.-eps)/2.+ delta*((1.-eps)/4. + eps/4. + eps*(1.-eps)/2.);
        double p101 =(1.-3.*delta) * (1.-eps)*(1.-eps)/2. +  delta*((1.-eps)/2. + (eps*eps+(1.-eps)*(1.-eps))/4.);
        double p011 =p110;
        res = 2*(nLogN(p111) + nLogN(p110) +nLogN(p101) +nLogN(p011));
        return res;

    }


    public static double real4 (double eps, double delta ){
        double res = 0.;
        double p0000 = (1.-4.*delta)* pow(eps, 3.)/2. + delta*(pow(eps, 2.) /2. + eps*(pow(eps,2.) + pow(1.-eps,2.))/2.);
        double p0001= (1.-4.*delta) * pow(eps, 2.)*(1.-eps)/2.+ delta*(pow(eps,2.)/4. + pow(eps, 2.)*(1.-eps)/2. + eps*(1.-eps)/4. + (1.-eps)*(pow(eps,2.)+pow((1.-eps),2.))/4.);
        double p0010= (1.-4.*delta) * pow((1.-eps), 2.)*eps/2.+ delta*(eps*(1.-eps)/2. + pow((1.-eps), 2.)*eps/2. + pow((1.-eps), 2.)/4. + eps*(pow(eps,2.) + pow(1.-eps,2.))/4.);
        double p0100= (1.-4.*delta) *  pow((1.-eps), 2.)*eps/2.+ delta*(pow((1.-eps),2)/4 + pow((1.-eps), 2.)*eps/2. +(1.-eps)*eps/4. +  eps*(pow(eps,2.) + pow(1.-eps,2.))/4.);
        double p1000= (1.-4.*delta) * pow(eps, 2.)*(1.-eps)/2.+ delta*(eps*(1.-eps)/4. + eps*pow((1.-eps), 2.)/2. + pow(eps, 2.)/4. + (1.-eps)*(pow(eps,2.)+pow((1.-eps),2.))/4.);
        double p1100= (1.-4.*delta) * pow(eps, 2.)*(1.-eps)/2.+ delta*(pow(eps, 2.)*(1.-eps) + eps*(1.-eps)/2. );
        double p0110= (1.-4.*delta) * eps* pow((1.-eps),2.)/2.+ delta*(eps*pow((1.-eps), 2.) + eps*(1.-eps)/2.);
        double p1010= (1.-4.*delta) * pow((1.-eps),3.)/2.+ delta*(pow((1.-eps),2.)/2. +  (1.-eps)*(pow(eps,2.)+pow((1.-eps),2.))/2.);
        res = 2.*( nLogN(p0000) +nLogN(p1000) +nLogN(p0100) +nLogN(p0010) +nLogN(p0001) +nLogN(p1100) +nLogN(p0110) +nLogN(p1010));
        return res;

    }

    public static double real4_1 (double eps, double delta ){
        double res = 0.;
        double p0000 = pow(eps, 3.)/2. + delta*(-pow(eps, 3.) - pow(eps, 2.)/2. + eps/2.);
        double p0001 = pow(eps, 2.)*(1.-eps)/2.+ delta*(pow(eps, 3.) - pow(eps,2.)/2. - eps/2. + 0.25);
        double p0010 = pow((1.-eps), 2.)*eps/2.+ delta*(-pow(eps,3.) + 2.5 * pow(eps, 2.) - 1.5 * eps + 0.25);
        double p0100 = p0010;
        double p1000 = pow(eps, 2.)*(1.-eps)/2.+ delta*(pow(eps, 3.) -0.5*pow(eps, 2.) -0.5*eps + 0.5);
        double p1100 = pow(eps, 2.)*(1.-eps)/2.+ delta*(pow(eps, 3.)-1.5*pow(eps,2.) + 0.5* eps );
        double p0110 = eps* pow((1.-eps),2.)/2.+ delta*(-pow(eps,3.) + 1.5 * pow(eps, 2.) - 0.5*eps);
        double p1010 = pow((1.-eps),3.)/2.+ delta*(pow(eps, 3.) - 3.5*pow(eps, 2.)+3.5*eps - 1. );
        res = 2.*( nLogN(p0000) +nLogN(p1000) +nLogN(p0100) +nLogN(p0010) +nLogN(p0001) +nLogN(p1100) +nLogN(p0110) +nLogN(p1010));
        return res;

    }

    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * Math.log(x) / Math.log(2);
    }


}
