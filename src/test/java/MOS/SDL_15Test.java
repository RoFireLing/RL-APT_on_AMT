package MOS;

import junit.framework.TestCase;
import labprograms.MOS.sourceCode.MSR;
import labprograms.constant.Constant;
import labprograms.testCase.TestCase4MOS;
import labprograms.util.WriteTestingResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SDL_15Test extends TestCase {
    labprograms.MOS.sourceCode.MealOrderingSystem source = new labprograms.MOS.sourceCode.MealOrderingSystem();
    WriteTestingResult writeTestingResult = new WriteTestingResult();
    private List<TestCase4MOS> testcases;

    @Test
    public void testSDL_15() {
        String mutantName = "SDL_15";
        testcases = new ArrayList<>();
        createTestCases();
        int count = 0;
        for (TestCase4MOS tc : testcases) {
            MSR sourceResult = source.generateMSR(tc.getAircraftmodel(), tc.getChangeinthenumberofcrewmembers(), tc.getNewnumberofcrewmembers(), tc.getChangeinthenumberofpilots(), tc.getNewnumberofpilots(), tc.getNumberofchildpassengers(), tc.getNumberofrequestedbundlesofflowers());
            labprograms.MOS.mutants.SDL_15.MealOrderingSystem mutant = new labprograms.MOS.mutants.SDL_15.MealOrderingSystem();
            MSR mutantResult = mutant.generateMSR(tc.getAircraftmodel(), tc.getChangeinthenumberofcrewmembers(), tc.getNewnumberofcrewmembers(), tc.getChangeinthenumberofpilots(), tc.getNewnumberofpilots(), tc.getNumberofchildpassengers(), tc.getNumberofrequestedbundlesofflowers());
            if (sourceResult.numberOfBundlesOfFlowers == mutantResult.numberOfBundlesOfFlowers && sourceResult.numberOfBusinessClassMeals == mutantResult.numberOfBusinessClassMeals && sourceResult.numberOfChildMeals == mutantResult.numberOfChildMeals && sourceResult.numberOfEconomicClassMeals == mutantResult.numberOfEconomicClassMeals && sourceResult.numberOfFirstClassMeals == mutantResult.numberOfFirstClassMeals && sourceResult.numberOfMealsForCrewMembers == mutantResult.numberOfMealsForCrewMembers && sourceResult.numberOfMealsForPilots == mutantResult.numberOfMealsForPilots) {
                continue;
            } else {
                count++;
            }
        }
        writeTestingResult.write("MOS", mutantName, " ", String.valueOf(count));
    }

    private void createTestCases() {
        Constant constant = new Constant();
        Random random = new Random(0);
        String[] models = {"747200", "747300", "747400", "000200", "000300"};
        String[] changenumbers = {"y", "n"};
        String[] changpilots = {"y", "n"};
        for (int i = 0; i < constant.number; i++) {
            String aircraftmodel = models[random.nextInt(4)];
            String changeinthenumberofcrewmembers = changenumbers[random.nextInt(2)];
            int newnumberofcrewmembers = random.nextInt(20);
            String changeinthenumberofpilots = changpilots[random.nextInt(2)];
            int newnumberofpilots = random.nextInt(5);
            int numberofchildpassengers = random.nextInt(15);
            int numberofrequestedbundlesofflowers = random.nextInt(500);
            TestCase4MOS tc = new TestCase4MOS(aircraftmodel, changeinthenumberofcrewmembers, newnumberofcrewmembers,
                    changeinthenumberofpilots, newnumberofpilots, numberofchildpassengers, numberofrequestedbundlesofflowers);
            testcases.add(tc);
        }
    }
}