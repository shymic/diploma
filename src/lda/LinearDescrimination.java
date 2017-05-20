package lda;

import java.io.*;

/**
 * Данные в формате:
 * число строк класса
 * x y class
 */
public class LinearDescrimination {
    private static int n;
    private static int nData;
    private static double[][] class1;
    private static double[][] class2;
    private static double[][] data;
    private static double[][] coefB;
    private static double A;


    public static int getN() {
        return n;
    }

    public static double[][] getClass1() {
        return class1;
    }

    public static double[][] getClass2() {
        return class2;
    }

    public static double[][] getCoefB() {
        return coefB;
    }

    public static double getA() {
        return A;
    }
/**
 * data in format
 * 100
 0.994160515;0.995540898;0
 0.995404141;0.994064799;0
 0.994364744;0.991888402;0
 0.996466022;0.992379601;0
 0.995557754;0.994463478;0
 0.995272534;0.994492969;0
 0.994884985;0.994607152;0
 */

    static void readStudingData() {
        String[] strs = null;
        String str = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("LinearDescriminationData.csv"));
            n = Integer.parseInt(br.readLine());
            class1 = new double[n][2];
            class2 = new double[n][2];
            int y = 0;
            for (int i = 0; i < 2 * n; i++) {
                str = br.readLine();
                strs = str.split(";");
                y = Integer.parseInt(strs[2]);
                if (y == 0) {
                    class1[i%n][0] = Double.parseDouble(strs[0]);
                    class1[i%n][1] = Double.parseDouble(strs[1]);
                } else {
                    class2[i%n][0] = Double.parseDouble(strs[0]);
                    class2[i%n][1] = Double.parseDouble(strs[1]);
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//data in format
    /*
    200
0.996171184;0.993601393
0.994087966;0.994612198
0.994918877;0.994417001
0.994475736;0.992475443
0.995788809;0.995131384
    * */
    static void readResearchingData() {
        String[] strs = null;
        String str = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Data.csv"));
            nData = Integer.parseInt(br.readLine());
            data = new double[nData][2];
            for (int i = 0; i <  nData; i++) {
                str = br.readLine();
                strs = str.split(";");
                data[i][0] = Double.parseDouble(strs[0]);
                data[i][1] = Double.parseDouble(strs[1]);
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static double[][] multiplyMatrix(double[][] A, double[][] B) {
        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }


    public static void print(double[][] matr) {
        int n = matr.length;
        int m = matr[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                System.out.print(matr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static double[][] transponate(double[][]a){
        int n = a.length;
        int m = a[0].length;
        double[][]res = new double[m][n];
        for( int i =0 ; i < n; ++i){
            for ( int j = 0; j < m; ++j){
                res[j][i] = a[i][j];
            }
        }
        return  res;
    }
    public static double[][] aMinusB(double[][]a,double[][]b){
        int n = a.length;
        int m = a[0].length;
        double[][]res = new double[n][m];
        for( int i =0 ; i < n; ++i){
            for ( int j = 0; j < m; ++j){
                res[i][j] = a[i][j]-b[i][j];
            }
        }
        return  res;
    }
    public static double[][] aPlusB(double[][]a,double[][]b){
        int n = a.length;
        int m = a[0].length;
        double[][]res = new double[n][m];
        for( int i =0 ; i < n; ++i){
            for ( int j = 0; j < m; ++j){
                res[i][j] = a[i][j]+b[i][j];
            }
        }
        return  res;
    }
    public static double[][] inverse(double[][]a){
        double det = a[0][0] * a[1][1] - a[0][1]*a[1][0];
        double[][] res = new double[2][2];
        res[0][0] = a[1][1]/det;
        res[0][1]= -a[0][1]/det;
        res[1][0]= -a[1][0]/det;
        res[1][1]= a[0][0]/det;
        return res;
    }
    //значение дискриминантной функции. принимает столбец B
    public static double countD(double[][]b, double x[][]){
        return b[0][0]*x[0][0] + b[1][0]*x[0][1];
    }


    public static double countD(double[][]b, double[] x){
        return b[0][0]*x[0] + b[1][0]*x[1];
    }


    //X  без крышки
    public static double[][] countC(double[][]x){
        double midX =0;
        double midY =0;
        int n = x.length;

        for(int i = 0; i < n; ++i){
            midX+=x[i][0];
            midY+=x[i][1];
        }
        midX/=(double)n;
        midY/=(double)n;
        double[][] res = new double[n][2];
        for(int i = 0; i < n; ++i){
            res[i][0] = x[i][0] - midX;
            res[i][1] = x[i][1] - midY;
        }
        return res;
    }
    //матрица ковариации
    public static double[][] countCOV(double[][]x1, double [][]x2){

        double[][]x1c=countC(x1);
        double[][]x2c=countC(x2);

        double[][] res = aPlusB(multiplyMatrix(transponate(x1c),x1c),multiplyMatrix(transponate(x2c),x2c));
        for (int i = 0; i < res.length; ++i){
            for ( int j = 0; j< res[0].length; ++j){
                res[i][j] /= x1.length + x2.length - 2.;
            }
        }
        return res;
    }
    //вектор матожидания X c крышкой
    public static double[][]countE(double[][] x){
        double midX =0;
        double midY =0;
        int n = x.length;

        for(int i = 0; i < n; ++i){
            midX+=x[i][0];
            midY+=x[i][1];
        }
        midX/=(double)n;
        midY/=(double)n;
        double[][] res = new double[1][2];
        res[0][0]=midX;
        res[0][1]=midY;
        return res;
    }

    public static void countCoef(){
        double[][]cov = countCOV(class1, class2);
        coefB = multiplyMatrix(inverse(cov), transponate(aMinusB(countE(class1), countE(class2))));
    }

    public static void countA(){
       A =(countD(coefB, countE(class1)) + countD(coefB, countE(class2))) / 2.;
    }

    public static void researchData() throws FileNotFoundException {

        PrintWriter pw =new PrintWriter("descriminationRES.csv");
        pw.println("=СУММ(A3:A103)");
        pw.println("=СУММ(A104:A302)");
        for ( int i = 0; i < data.length; ++i){
            if(countD(coefB, data[i]) > A){
                pw.println("1");
            }else pw.println("2");
        }
        pw.flush();
        pw.close();
    }



    public static void run() throws FileNotFoundException {
        readStudingData();
        double[][]cov = countCOV(class1,class2);
        coefB = multiplyMatrix(inverse(cov), transponate(aMinusB(countE(class1), countE(class2))));
        countA();

        readResearchingData();
        researchData();

    }

    public static void main(String[] args) {
//        double[][]a = {{1.,2.},{2.,6}};
//        double[][]b = {{3.,1.},{4.,3.}, {5.,5.}};
//        double[][]cov = countCOV(a, b);
//        print(cov);
//        System.out.println();
//        print(inverse(cov));
//        System.out.println();
//        double[][]coefB = multiplyMatrix(inverse(cov), transponate(aMinusB(countE(a), countE(b))));
//        System.out.println(countD(coefB, countE(a)));
//        System.out.println(countD(coefB, countE(b)));

        try {
            run();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
