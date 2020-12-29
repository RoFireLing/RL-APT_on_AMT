package labprograms.testCase;

import labprograms.constant.Constant;
import labprograms.util.WriteTestCases;

import java.util.*;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/04/19
 */
public class TestSuite4CUBS {
    /**
     * the map that is convenient to transform to json
     */
    private Map<String, Object> testSuite;

    /**
     * the list that is convenient to take out a test case
     */
    private List<TestCase4CUBS> testcases;

    public TestSuite4CUBS() {
        testSuite = new HashMap<>();
        testcases = new ArrayList<>();
    }

    public static void main(String[] args) {
        TestSuite4CUBS testSuite4CUBS = new TestSuite4CUBS();
        testSuite4CUBS.createTestSuite();
    }

    public Map<String, Object> getTestSuite() {
        return testSuite;
    }

    public List<TestCase4CUBS> getTestcases() {
        return testcases;
    }

    /**
     * write test cases to json file
     */
    public void createTestSuite() {
        Constant constant = new Constant();
        Random random = new Random(0);
        String[] types = {"A", "B", "a", "b"};
        int[] Achoices = {46, 96, 286, 886};
        int[] Bchoices = {46, 96, 126, 186};
        for (int i = 0; i < constant.number; i++) {
            String planType = types[random.nextInt(4)];
            int planFee = 0;
            if (planType == "A" || planType == "a") {
                planFee = Achoices[random.nextInt(4)];
            } else {
                planFee = Bchoices[random.nextInt(4)];
            }
            int talkTime = random.nextInt(4000);
            int flow = random.nextInt(4000);

            TestCase4CUBS tc = new TestCase4CUBS(planType, planFee, talkTime, flow);
            testSuite.put(String.valueOf(i), tc);
        }
        WriteTestCases writeTestCases = new WriteTestCases();
        writeTestCases.writeTestSuiteToJson("CUBS", getTestSuite());
    }

    public List<TestCase4CUBS> getTestCases() {
        List<Object> temp_list = new GetTestSuite().getTestSuite("CUBS");
        for (int i = 0; i < temp_list.size(); i++) {
            TestCase4CUBS tc = (TestCase4CUBS) temp_list.get(i);
            testcases.add(tc);
        }
        return testcases;
    }

}
