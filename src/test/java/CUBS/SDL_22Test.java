package CUBS;

import junit.framework.TestCase;
import labprograms.constant.Constant;
import labprograms.testCase.TestCase4CUBS;
import labprograms.util.WriteTestingResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SDL_22Test extends TestCase {
    labprograms.CUBS.sourceCode.BillCalculation source = new labprograms.CUBS.sourceCode.BillCalculation();
    WriteTestingResult writeTestingResult = new WriteTestingResult();
    private List<TestCase4CUBS> testcases;

    @Test
    public void testSDL_22() {
        String mutantName = "SDL_22";
        testcases = new ArrayList<>();
        createTestCases();
        int count = 0;
        for (TestCase4CUBS tc : testcases) {
            double sourceResult = source.phoneBillCalculation(tc.getPlanType(), tc.getPlanFee(), tc.getTalkTime(), tc.getFlow());
            labprograms.CUBS.mutants.SDL_22.BillCalculation mutant = new labprograms.CUBS.mutants.SDL_22.BillCalculation();
            double mutantResult = mutant.phoneBillCalculation(tc.getPlanType(), tc.getPlanFee(), tc.getTalkTime(), tc.getFlow());
            if (sourceResult == mutantResult) {
                continue;
            } else {
                count++;
            }
        }
        writeTestingResult.write("CUBS", mutantName, " ", String.valueOf(count));
    }

    private void createTestCases() {
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
            testcases.add(tc);
        }
    }
}