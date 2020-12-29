package ERS;

import junit.framework.TestCase;
import labprograms.constant.Constant;
import labprograms.testCase.TestCase4ERS;
import labprograms.util.WriteTestingResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SDL_27Test extends TestCase {
    labprograms.ERS.sourceCode.ExpenseReimbursementSystem source = new labprograms.ERS.sourceCode.ExpenseReimbursementSystem();
    WriteTestingResult writeTestingResult = new WriteTestingResult();
    private List<TestCase4ERS> testcases;

    @Test
    public void testSDL_27() {
        String mutantName = "SDL_27";
        testcases = new ArrayList<>();
        createTestCases();
        int count = 0;
        for (TestCase4ERS tc : testcases) {
            double sourceResult = source.calculateReimbursementAmount(tc.getStafflevel(), tc.getActualmonthlymileage(), tc.getMonthlysalesamount(), tc.getAirfareamount(), tc.getOtherexpensesamount());
            labprograms.ERS.mutants.SDL_27.ExpenseReimbursementSystem mutant = new labprograms.ERS.mutants.SDL_27.ExpenseReimbursementSystem();
            double mutantResult = mutant.calculateReimbursementAmount(tc.getStafflevel(), tc.getActualmonthlymileage(), tc.getMonthlysalesamount(), tc.getAirfareamount(), tc.getOtherexpensesamount());
            if (sourceResult == mutantResult) {
                continue;
            } else {
                count++;
            }
        }
        writeTestingResult.write("ERS", mutantName, " ", String.valueOf(count));
    }

    private void createTestCases() {
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
            testcases.add(tc);
        }
    }
}