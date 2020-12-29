package labprograms.testCase;

import labprograms.constant.Constant;
import labprograms.util.WriteTestCases;

import java.util.*;


/**
 * describe:
 * the test suite that conclude specified test cases
 * the index of test case is from 0 to n
 *
 * @author phantom
 * @date 2019/04/17
 */
public class TestSuite4ACMS {

    /**
     * the map that is convenient to transform to json
     */
    private Map<String, Object> testSuite;

    /**
     * the list that is convenient to take out a test case
     */
    private List<TestCase4ACMS> testcases;

    public TestSuite4ACMS() {
        testSuite = new HashMap<>();
        testcases = new ArrayList<>();
    }

    public static void main(String[] args) {
        TestSuite4ACMS testSuite4ACMS = new TestSuite4ACMS();
        testSuite4ACMS.createTestSuite();
    }

    public Map<String, Object> getTestSuite() {
        return testSuite;
    }

    public List<TestCase4ACMS> getTestcases() {
        return testcases;
    }

    /**
     * write test cases to json file
     */
    public void createTestSuite() {
        Constant constant = new Constant();
        Random random = new Random(0);
        Boolean[] ISSTUDENT = {true, false};
        for (int i = 0; i < constant.number; i++) {
            boolean isStudent = ISSTUDENT[random.nextInt(2)];
            int airClass = 0;
            if (isStudent) {
                airClass = 2;
            } else {
                airClass = random.nextInt(4);
            }
            int area = random.nextInt(2);

            double luggage = random.nextDouble() * 80;
            double economicfee = random.nextDouble() * 5000 + 500;
            TestCase4ACMS tc = new TestCase4ACMS(airClass, area, isStudent, luggage, economicfee);
            testSuite.put(String.valueOf(i), tc);
            testcases.add(tc);
        }
        WriteTestCases writeTestCases = new WriteTestCases();
        writeTestCases.writeTestSuiteToJson("ACMS", getTestSuite());
    }

    public List<TestCase4ACMS> getTestCases() {
        List<Object> temp_list = new GetTestSuite().getTestSuite("ACMS");
        for (int i = 0; i < temp_list.size(); i++) {
            TestCase4ACMS tc = (TestCase4ACMS) temp_list.get(i);
            testcases.add(tc);
        }
        return testcases;
    }

}
