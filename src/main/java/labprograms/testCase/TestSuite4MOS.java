package labprograms.testCase;

import labprograms.constant.Constant;
import labprograms.util.WriteTestCases;

import java.util.*;

/**
 * describe:
 * this class is responsible to create test cases and write them into a json file
 *
 * @author phantom
 * @date 2019/04/23
 */
public class TestSuite4MOS {
    /**
     * the map that is convenient to transform to json
     */
    private Map<String, Object> testSuite;

    /**
     * the list that is convenient to take out a test case
     */
    private List<TestCase4MOS> testcases;

    public TestSuite4MOS() {
        testcases = new ArrayList<>();
        testSuite = new HashMap<>();
    }

    public static void main(String[] args) {
        TestSuite4MOS suite = new TestSuite4MOS();
        suite.createTestSuite();
    }

    public Map<String, Object> getTestSuite() {
        return testSuite;
    }

    public List<TestCase4MOS> getTestcases() {
        return testcases;
    }

    /**
     * generate test cases and write test cases into a json file
     */
    public void createTestSuite() {
        Constant constant = new Constant();
        Random random = new Random(0);
        String[] models = {"747200", "747300", "747400", "000200", "000300"};
        String[] changenumbers = {"y", "n"};
        String[] changpilots = {"y", "n"};
        for (int i = 0; i < constant.number; i++) {
            String aircraftmodel = models[random.nextInt(4)];
            String changeinthenumberofcrewmembers = changenumbers[random.nextInt(2)];
            int newnumberofcrewmembers = random.nextInt(30);
            String changeinthenumberofpilots = changpilots[random.nextInt(2)];
            int newnumberofpilots = random.nextInt(15);
            int numberofchildpassengers = random.nextInt(15);
            int numberofrequestedbundlesofflowers = random.nextInt(500);
            TestCase4MOS tc = new TestCase4MOS(aircraftmodel, changeinthenumberofcrewmembers, newnumberofcrewmembers,
                    changeinthenumberofpilots, newnumberofpilots, numberofchildpassengers, numberofrequestedbundlesofflowers);
            testSuite.put(String.valueOf(i), tc);
            testcases.add(tc);
        }
        WriteTestCases writeTestCases = new WriteTestCases();
        writeTestCases.writeTestSuiteToJson("MOS", getTestSuite());
    }

}
