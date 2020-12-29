package labprograms.strategies.util;

import java.util.List;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/05/14
 */
public class CalculateAverage {

    /**
     * calculate average time
     *
     * @param time the array of all time
     * @return the average time
     */
    public static double getAverageTime(List<Long> time) {
        long temp = 0;
        for (long t : time) {
            temp += t;
        }
        double result = temp / time.size();
        return result;
    }

    public static double getAveragemeasure(List<Integer> time) {
        int temp = 0;
        for (long t : time) {
            temp += t;
        }
        double result = temp / time.size();
        return result;
    }
}
