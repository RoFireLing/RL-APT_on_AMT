package labprograms.gethardkilledmutants;

import labprograms.testCase.TestCase4MOS;
import labprograms.testCase.TestSuite4ACMS;

import java.util.Map;
import java.util.Random;

/**
 * describe:
 * randomly generate test cases to execute mutants, which can get the mutants
 * that at least need 20 test case to be killed
 *
 * @author phantom
 * @date 2019/04/18
 */
public class RT4MOS {
    private static final String[] AIRCRAFTMEDEL = {"747200", "747300", "747400", "000200", "000300"};
    private static final String[] CHANGEINTHENUMBEROFCREWMEMBERS = {"y", "n"};
    private static final String[] CHANGEINTHENNUMBEROFPILOTS = {"y", "n"};

    public Map<String, Object> createTestCases(int numoftestcases) {
        TestSuite4ACMS testSuite4ACMS = new TestSuite4ACMS();
        Random random = new Random(0);
        for (int i = 0; i < numoftestcases; i++) {
            String aircraftmodel = AIRCRAFTMEDEL[random.nextInt(5)];
            String changeinthenumberofcrewmembers = CHANGEINTHENUMBEROFCREWMEMBERS[random.nextInt(2)];
            int newnumberofcrewmembers = random.nextInt(50) + 1;
            String changeinthenumberofpilots = CHANGEINTHENNUMBEROFPILOTS[random.nextInt(2)];
            int newnumberofpilots = random.nextInt(10) + 1;
            int numberofchildpassengers = random.nextInt(50);
            int numberofrequestedbundlesofflowers = random.nextInt(300);
            TestCase4MOS tc = new TestCase4MOS(aircraftmodel, changeinthenumberofcrewmembers,
                    newnumberofcrewmembers, changeinthenumberofpilots, newnumberofpilots, numberofchildpassengers,
                    numberofrequestedbundlesofflowers);
        }
        return testSuite4ACMS.getTestSuite();
    }

}
