package labprograms.newStrategy.utl;

import java.util.Comparator;

/**
 * @Description:
 * @auther phantom
 * @create 2019-09-23 下午9:42
 */
public class Mycompare implements Comparator<Integer> {


    @Override
    public int compare(Integer o1, Integer o2) {
        if (o1 > o2)
            return -1;
        if (o1 < o2)
            return 1;
        return 0;
    }
}
