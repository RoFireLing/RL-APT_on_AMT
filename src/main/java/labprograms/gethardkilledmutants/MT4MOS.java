package labprograms.gethardkilledmutants;

import labprograms.MOS.sourceCode.MSR;
import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import labprograms.testCase.TestCase4MOS;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.io.File.separator;

/**
 * describe:
 * using traditional MT to choose the stubborn mutants
 * <p>
 * 注意：MSR的各个属性的顺序与mr中的不一致，其正确的属性是：
 * 1，number of crews
 * 2, number of pilots
 * 3, number of chilfren
 * 4, number of flowers
 * 5, model
 * 6, number of first class meals
 * 7, number of business class meals
 * 8, number of economic class meals
 *
 * @author phantom
 * @date 2019/05/05
 */
public class MT4MOS {


    public static void main(String[] args) throws IllegalAccessException, IOException, InvocationTargetException {
        MT4MOS mos = new MT4MOS();
        mos.getKilledInfo();
    }

    public void getKilledInfo() throws IOException, InvocationTargetException, IllegalAccessException {
        MutantsSet mutantsSet = new MutantsSet("MOS");
        Map<String, Mutant> mutantMap = mutantsSet.getMutants();
        String methodName = new Methods4Testing("MOS").getMethodName();

        for (Map.Entry<String, Mutant> entry : mutantMap.entrySet()) {
            //逐个遍历每一个变异体
            List<Integer> indexs = new ArrayList<>();
            int counter = 0;

            Mutant mutant = entry.getValue();
            Object mutantInstance = null;
            Method mutantMethod = null;
            MSR sourceResult = null;
            MSR followUpResult = null;
            Class mutantClazz = null;
            try {
                mutantClazz = Class.forName(mutant.getFullName());
                Constructor mutantConstructor = mutantClazz.getConstructor();
                mutantInstance = mutantConstructor.newInstance();
                mutantMethod = mutantClazz.getMethod(methodName, String.class, String.class, int.class,
                        String.class, int.class, int.class, int.class);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            String path = Constant.mrPath4MOS;

            File file = new File(path);
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String testframesAndMr = "";
            // the number of line (begin form 1)
            int lineNumber = 0;

            while ((testframesAndMr = bufferedReader.readLine()) != null) {
                lineNumber++;
                String sourceTestFrame = testframesAndMr.split(";")[0];
                String followUpTestFrame = testframesAndMr.split(";")[1];
                String MR = "";
                for (int i = 2; i < testframesAndMr.split(";").length; i++) {
                    MR += testframesAndMr.split(";")[i] + ";";
                }
                MR = MR.substring(0, MR.length() - 1);

                // delete the medel change in the MR
                String[] choice = MR.split(";");
                MR = choice[0] + ";" + choice[1] + ";" + choice[2] + ";" + choice[3] + ";"
                        + choice[5] + ";" + choice[6] + ";" + choice[7];

                TestCase4MOS sourceTestCase = generateTestCase(sourceTestFrame);
                TestCase4MOS followUpTestCase = generateTestCase(followUpTestFrame);


                sourceResult = (MSR) mutantMethod.invoke(mutantInstance, sourceTestCase.getAircraftmodel(),
                        sourceTestCase.getChangeinthenumberofcrewmembers(), sourceTestCase.getNewnumberofcrewmembers(),
                        sourceTestCase.getChangeinthenumberofpilots(), sourceTestCase.getNewnumberofpilots(),
                        sourceTestCase.getNumberofchildpassengers(), sourceTestCase.getNumberofrequestedbundlesofflowers());

                followUpResult = (MSR) mutantMethod.invoke(mutantInstance, followUpTestCase.getAircraftmodel(),
                        followUpTestCase.getChangeinthenumberofcrewmembers(), followUpTestCase.getNewnumberofcrewmembers(),
                        followUpTestCase.getChangeinthenumberofpilots(), followUpTestCase.getNewnumberofpilots(),
                        followUpTestCase.getNumberofchildpassengers(), followUpTestCase.getNumberofrequestedbundlesofflowers());

                String resultRelation = "";


                if (sourceResult.numberOfMealsForCrewMembers == followUpResult.numberOfMealsForCrewMembers) {
                    resultRelation += "do not change;";
                } else if (sourceResult.numberOfMealsForCrewMembers > followUpResult.numberOfMealsForCrewMembers) {
                    resultRelation += "decrease;";
                } else {
                    resultRelation += "increase;";
                }

                if (sourceResult.numberOfMealsForPilots == followUpResult.numberOfMealsForPilots) {
                    resultRelation += "do not change;";
                } else if (sourceResult.numberOfMealsForPilots > followUpResult.numberOfMealsForPilots) {
                    resultRelation += "decrease;";
                } else {
                    resultRelation += "increase;";
                }

                if (sourceResult.numberOfChildMeals == followUpResult.numberOfChildMeals) {
                    resultRelation += "do not change;";
                } else if (sourceResult.numberOfChildMeals > followUpResult.numberOfChildMeals) {
                    resultRelation += "decrease;";
                } else {
                    resultRelation += "increase;";
                }

                if (sourceResult.numberOfBundlesOfFlowers == followUpResult.numberOfBundlesOfFlowers) {
                    resultRelation += "do not change;";
                } else if (sourceResult.numberOfBundlesOfFlowers > followUpResult.numberOfBundlesOfFlowers) {
                    resultRelation += "decrease;";
                } else {
                    resultRelation += "increase;";
                }

                if (sourceResult.numberOfFirstClassMeals == followUpResult.numberOfFirstClassMeals) {
                    resultRelation += "do not change;";
                } else if (sourceResult.numberOfFirstClassMeals > followUpResult.numberOfFirstClassMeals) {
                    resultRelation += "decrease;";
                } else {
                    resultRelation += "increase;";
                }

                if (sourceResult.numberOfBusinessClassMeals == followUpResult.numberOfBusinessClassMeals) {
                    resultRelation += "do not change;";
                } else if (sourceResult.numberOfBusinessClassMeals > followUpResult.numberOfBusinessClassMeals) {
                    resultRelation += "decrease;";
                } else {
                    resultRelation += "increase;";
                }

                if (sourceResult.numberOfEconomicClassMeals == followUpResult.numberOfEconomicClassMeals) {
                    resultRelation += "do not change";
                } else if (sourceResult.numberOfEconomicClassMeals > followUpResult.numberOfEconomicClassMeals) {
                    resultRelation += "decrease";
                } else {
                    resultRelation += "increase";
                }

                if (!MR.equals(resultRelation)) {
                    counter++;
                    indexs.add(lineNumber);
                }

            }// We went through each mutant
            writeInfo(entry.getKey(), String.valueOf(counter), indexs);
        }
    }

    /**
     * generate concrete test cases based on the test frame
     *
     * @param testFrame that is used to generate test cases
     * @return a test case
     */
    public TestCase4MOS generateTestCase(String testFrame) {
        String aircraftmodel = "";
        String changeinthenumberofcrewmembers = "";
        int newnumberofcrewmembers = 0;
        String changeinthenumberofpilots = "";
        int newnumberofpilots = 0;
        int numberofchildpassengers = 0;
        int numberofrequestedbundlesofflowers = 0;
        String[] AIRCRAFTMEDEL = {"747200", "747300", "747400", "000200", "000300"};
        String[] CHANGEINTHENUMBEROFCREWMEMBERS = {"y", "n"};
        String[] CHANGEINTHENNUMBEROFPILOTS = {"y", "n"};

        //delate the braces
        testFrame = testFrame.replace("{", "").replace("}", "");
        String[] choices = testFrame.split(",");

        switch (choices[0]) {
            case "I-1a":
                aircraftmodel = AIRCRAFTMEDEL[0];
                break;
            case "I-1b":
                aircraftmodel = AIRCRAFTMEDEL[1];
                break;
            case "I-1c":
                aircraftmodel = AIRCRAFTMEDEL[2];
                break;
            case "I-1d":
                aircraftmodel = AIRCRAFTMEDEL[3];
                break;
            case "I-1e":
                aircraftmodel = AIRCRAFTMEDEL[4];
                break;
            case "*":
                aircraftmodel = AIRCRAFTMEDEL[new Random().nextInt(5)];
                break;
        }

        switch (choices[1]) {
            case "I-2a":
                changeinthenumberofcrewmembers = CHANGEINTHENUMBEROFCREWMEMBERS[0];
                break;
            case "I-2b":
                changeinthenumberofcrewmembers = CHANGEINTHENUMBEROFCREWMEMBERS[1];
                break;
            case "*":
                changeinthenumberofcrewmembers = CHANGEINTHENUMBEROFCREWMEMBERS[new Random().nextInt(2)];
                break;
        }
        int defalutCrew = 0;
        int defalutPlot = 0;
        if (aircraftmodel.equals("747200")) {
            defalutCrew = 10;
            defalutPlot = 2;
        } else if (aircraftmodel.equals("747300")) {
            defalutCrew = 12;
            defalutPlot = 3;
        } else if (aircraftmodel.equals("747400")) {
            defalutCrew = 14;
            defalutPlot = 3;
        } else if (aircraftmodel.equals("000200")) {
            defalutCrew = 13;
            defalutPlot = 2;
        } else if (aircraftmodel.equals("000300")) {
            defalutCrew = 14;
            defalutPlot = 3;
        }

        switch (choices[2]) {
            case "I-3a":
                newnumberofcrewmembers = defalutCrew + new Random().nextInt(5);
                break;
            case "I-3b":
                newnumberofcrewmembers = defalutCrew;
                break;
            case "I-3c":
                newnumberofcrewmembers = defalutCrew - new Random().nextInt(5);
                break;
            case "*":
                newnumberofcrewmembers = new Random().nextInt(15) + 1;
                break;
        }

        switch (choices[3]) {
            case "I-4a":
                changeinthenumberofpilots = CHANGEINTHENNUMBEROFPILOTS[0];
                break;
            case "I-4b":
                changeinthenumberofpilots = CHANGEINTHENNUMBEROFPILOTS[1];
                break;
            case "*":
                changeinthenumberofpilots = CHANGEINTHENNUMBEROFPILOTS[new Random().nextInt(2)];
                break;
        }

        switch (choices[4]) {
            case "I-5a":
                newnumberofpilots = defalutPlot + new Random().nextInt(5);
                break;
            case "I-5b":
                newnumberofpilots = defalutPlot;
                break;
            case "I-5c":
                newnumberofpilots = defalutPlot - 1;
                break;
            case "*":
                newnumberofpilots = new Random().nextInt(8);
                break;
        }

        switch (choices[5]) {
            case "I-6a":
                numberofchildpassengers = new Random().nextInt(20) + 1;
                break;
            case "I-6b":
                numberofchildpassengers = 0;
                break;
            case "*":
                numberofchildpassengers = new Random().nextInt(20);
                break;
        }

        switch (choices[6]) {
            case "I-7a":
                numberofrequestedbundlesofflowers = new Random().nextInt(100) + 1;
                break;
            case "I-7b":
                numberofrequestedbundlesofflowers = 0;
                break;
            case "*":
                numberofrequestedbundlesofflowers = new Random().nextInt(100);
                break;
        }
        TestCase4MOS tc = new TestCase4MOS(aircraftmodel, changeinthenumberofcrewmembers,
                newnumberofcrewmembers, changeinthenumberofpilots, newnumberofpilots,
                numberofchildpassengers, numberofrequestedbundlesofflowers);
        return tc;
    }

    private void writeInfo(String mutantName, String counter, List<Integer> index) {
        String path = Constant.killedmutantinfo + separator + "MOS";
        File file = new File(path);
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter.write(mutantName + ":" + counter + ":" + index.toString() + "\n");
        printWriter.close();
    }
}
