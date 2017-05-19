package worked;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Andrey on 28.09.2015.
 */
public class Counter{
    private int l;
    private Initializator initializator;
    private ArrayList<Integer> decimal;

    public Counter(Initializator init, int l){
        this.l = l;
        this.initializator = init;
        this.decimal = initDecimal(l);
    }


    public Counter(int l) {
        this.l = l;
        this.initializator = new Initializator();
        this.decimal = initDecimal(l);
    }
    public Counter(int l, double eps, double delta) {
        this.l = l;
        this.initializator = new Initializator(eps, delta);
        this.decimal = initDecimal(l);
    }

    public Initializator getInitializator() {
        return initializator;
    }

    public ArrayList<Integer> getDecimal() {
        return decimal;
    }

    public void setDecimal(ArrayList<Integer> decimal) {
        this.decimal = decimal;
    }

    public ArrayList<Integer> initDecimal ( int l){
        ArrayList<String> binaryWords = converterToBinaryWords(initializator.getY(), l);
        decimal =  convertToDecimal(binaryWords);
        return decimal;
    }

    public double showEmpiricEntropy(int l) {
        ArrayList<Long> frequencies = countFrequencies(decimal);
        ArrayList<Double> probabilities = countProbabilities(frequencies, initializator.getT(), l);
        return countEmpiricEntropy(probabilities, l);
    }

    public ArrayList<Integer> bitShift( ArrayList<Integer> decimal){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for ( Integer decim : decimal){
            result.add(decim >> 1);
        }
        return result;
    }

    private double countEmpiricEntropy(ArrayList<Double> probabilities, int l) {
        double entropy = 0.;
        for (Double prob : probabilities) {
            entropy += nLogN(prob);
        }
        return entropy/l;
    }

    private ArrayList<Double> countProbabilities(ArrayList<Long> frequencies, int size, int l) {
        ArrayList<Double> probabilities = new ArrayList<Double>();
        for (Long freq : frequencies) {
            probabilities.add(freq.doubleValue() /(double) (size-l+1));
        }
        return probabilities;
    }

    private ArrayList<Long> countFrequencies(ArrayList<Integer> list) {
        Map<Integer, Long> counts =
                list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        return new ArrayList<Long>(counts.values());
    }

    public ArrayList<String> converterToBinaryWords(int[] x, int l) {

        ArrayList<String> binaryWords = new ArrayList<String>();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i : x){
            stringBuffer.append(i);
        }
        for (int i = 0; i < x.length - l; ++i) {
            binaryWords.add(stringBuffer.substring(i, i+l));
        }
        return binaryWords;
    }

    private ArrayList<Integer> convertToDecimal(ArrayList<String> binaryWords) {
        ArrayList<Integer> decimal = new ArrayList<Integer>();
        for (String binaryWord : binaryWords) {
            decimal.add(Integer.parseInt(binaryWord, 2));
        }
        return decimal;
    }


    public static double nLogN(double x) {
        if (x == 0.)
            return 0;
        return -x * Math.log(x) / Math.log(2);
    }
}
