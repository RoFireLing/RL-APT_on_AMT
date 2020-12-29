package labprograms.strategies.util;

import java.util.ArrayList;
import java.util.List;

/**
 * describe:
 * a data set which record all test cases belonging a same partition
 *
 * @author phantom
 * @date 2019/05/14
 */
public class Bin {
    List<String> testcases;

    public Bin() {
        testcases = new ArrayList<>();
    }

    public void add(String testcase) {
        testcases.add(testcase);
    }

    public String getTestCase(int index) {
        return testcases.get(index);
    }

    public int size() {
        return testcases.size();
    }

}
