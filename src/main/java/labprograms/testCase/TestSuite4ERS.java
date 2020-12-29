package labprograms.testCase;

import labprograms.constant.Constant;
import labprograms.util.WriteTestCases;

import java.util.*;

/**
 * describe:
 * this class is responsible to genrerate test cases and write them into a json file
 *
 * @author phantom
 * @date 2019/04/22
 */
public class TestSuite4ERS {
    /**
     * the map that is convenient to transform to json
     */
    private Map<String, Object> testSuite;

    /**
     * the list that is convenient to take out a test case
     */
    private List<TestCase4ERS> testcases;

    public TestSuite4ERS() {
        testSuite = new HashMap<>();
        testcases = new ArrayList<>();
    }

    public static void main(String[] args) {
        TestSuite4ERS suite = new TestSuite4ERS();
        suite.createTestSuite();
    }

    public Map<String, Object> getTestSuite() {
        return testSuite;
    }

    public List<TestCase4ERS> getTestcases() {
        return testcases;
    }

    /**
     * generate test cases and write test cases into a json file
     */
    public void createTestSuite() {
        Constant constant = new Constant();
        Random random = new Random(0);
        String[] levels = {"seniormanager", "manager", "supervisor"};
        for (int i = 0; i < constant.number; i++) {
            String stafflevel = levels[random.nextInt(3)];
            double actualmonthlymileage = random.nextDouble() * 8000;
            double monthlysalesamount = random.nextDouble() * 150000;
            double airfareamount = random.nextDouble() * 10000;
            double otherexpensesamount = random.nextDouble() * 10000;
            TestCase4ERS tc = new TestCase4ERS(stafflevel, actualmonthlymileage,
                    monthlysalesamount, airfareamount, otherexpensesamount);
            testSuite.put(String.valueOf(i), tc);
            testcases.add(tc);
        }
        WriteTestCases writeTestCases = new WriteTestCases();
        writeTestCases.writeTestSuiteToJson("ERS", getTestSuite());
    }

}
