package series;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Shymko_A on 19.05.2017.
 */
public class SeriesInitializator {

    private ArrayList<Integer> series;

    public SeriesInitializator() {
        series=new ArrayList<>();
    }

    public SeriesInitializator(ArrayList series) {
        this.series = series;
    }

    public ArrayList<Integer> getSeries() {
        return series;
    }

    public void initSeries(int[] y){
        int counter = 1;
        int [] ser = new int[y.length];
        for (int i = 0; i < y.length; ++i) {
            if (  i == y.length-1 || y[i] == y[i+1] ){
                counter++;
            } else {
                ser[counter]++;
                counter=1;
            }
        }
        ser[--counter]++;
        System.out.println(Arrays.toString(ser));
        for (int index = 0; index < ser.length; index++)
        {
            series.add(ser[index]);
        }
    }
}
