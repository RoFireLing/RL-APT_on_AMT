package ACMS;

import junit.framework.TestCase;
import labprograms.constant.Constant;
import labprograms.testCase.TestCase4ACMS;
import labprograms.util.WriteTestingResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SDL_10Test extends TestCase {
    labprograms.ACMS.sourceCode.AirlinesBaggageBillingService source = new labprograms.ACMS.sourceCode.AirlinesBaggageBillingService();
    WriteTestingResult writeTestingResult = new WriteTestingResult();
    private List<TestCase4ACMS> testcases;

    @Test
    public void testSDL_10() {
        String mutantName = "SDL_10";
        testcases = new ArrayList<>();
        createTestCases();
        int count = 0;
        for (TestCase4ACMS tc : testcases) {
            double sourceResult = source.feeCalculation(tc.getAirClass(), tc.getArea(),
                    tc.isStudent(), tc.getLuggage(), tc.getEconomicfee());
            labprograms.ACMS.mutants.SDL_10.AirlinesBaggageBillingService mutant = new labprograms.ACMS.mutants.SDL_10.AirlinesBaggageBillingService();
            double mutantResult = mutant.feeCalculation(tc.getAirClass(), tc.getArea(),
                    tc.isStudent(), tc.getLuggage(), tc.getEconomicfee());
            if (sourceResult == mutantResult) {
                continue;
            } else {
                count++;
            }
        }
        writeTestingResult.write("ACMS", mutantName, " ", String.valueOf(count));
    }

    private void createTestCases() {
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
            double economicfee = random.nextDouble() * 3000 + 500;
            TestCase4ACMS tc = new TestCase4ACMS(airClass, area, isStudent, luggage, economicfee);
            testcases.add(tc);
        }
    }
}