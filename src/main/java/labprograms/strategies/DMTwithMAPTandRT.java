package labprograms.strategies;

import labprograms.MOS.sourceCode.MSR;
import labprograms.constant.Constant;
import labprograms.gethardkilledmutants.MT4ACMS;
import labprograms.gethardkilledmutants.MT4CUBS;
import labprograms.gethardkilledmutants.MT4ERS;
import labprograms.gethardkilledmutants.MT4MOS;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.UsedMutantsSet;
import labprograms.strategies.util.Control;
import labprograms.strategies.util.TestCasesOfPartition;
import labprograms.testCase.TestCase4ACMS;
import labprograms.testCase.TestCase4CUBS;
import labprograms.testCase.TestCase4ERS;
import labprograms.testCase.TestCase4MOS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * describe:
 * test case selection: MAPT
 * MR selection: RT
 *
 * @author phantom
 * @date 2019/05/14
 */
public class DMTwithMAPTandRT {

    /**
     * the test profile of MAPT
     */
    private double[][] MAPT;

    private double MAPT_gamma = 0.1;

    private double MAPT_tau = 0.1;

    public static void main(String[] args) {
        DMTwithMAPTandRT dmTwithMAPTandRT = new DMTwithMAPTandRT();
        DMTwithRAPTandRT dmTwithRAPTandRT = new DMTwithRAPTandRT();
//        String[] names = {"ACMS", "CUBS", "ERS", "MOS"};
        String[] names = {"ACMS"};
        for (int i = 0; i < 100; i++) {
            for (String name : names) {
                dmTwithMAPTandRT.MAPTwithRandomlySelectMR(name);
                dmTwithRAPTandRT.RAPTwithRandomlySelectMR(name);
            }
        }

    }

    /**
     * initialize the test profile of RAPT
     *
     * @param numberOfPartitions the number of partitions
     */
    private void initializeMAPT(int numberOfPartitions) {
        MAPT = new double[numberOfPartitions][numberOfPartitions];
        for (int i = 0; i < numberOfPartitions; i++) {
            for (int j = 0; j < numberOfPartitions; j++) {
                MAPT[i][j] = 1.0 / numberOfPartitions;
            }
        }
    }

    /**
     * get a index of partition
     * Note that the first number of partitions is 0
     *
     * @return the index
     */
    private int nextPartition4MAPT(int formerPartitionNumber) {
        double[] tempArray = new double[MAPT.length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = MAPT[formerPartitionNumber][i];
        }
        int index = -1;
        double randomNumber = new Random().nextDouble();
        double sum = 0;
        do {
            index++;
            sum += tempArray[index];
        } while (randomNumber >= sum && index < tempArray.length - 1);
        return index;
    }

    /**
     * adjust the test profile for MAPT testing
     *
     * @param formerSourcePartitionIndex
     * @param formerfollowUpPartitionIndex
     * @param isKilledMutans
     */
    private void adjustMAPT(int formerSourcePartitionIndex,
                            int formerfollowUpPartitionIndex,
                            boolean isKilledMutans) {
        //the source test case and follow-up test case belong to the same partition
        double old_i = MAPT[formerSourcePartitionIndex][formerSourcePartitionIndex];
        double old_f = MAPT[formerSourcePartitionIndex][formerfollowUpPartitionIndex];


        if (formerSourcePartitionIndex == formerfollowUpPartitionIndex) {
            // the test case killed a mutant
            if (isKilledMutans) { //same partition and killed a mutant
                double sum = 0;
                double threshold = MAPT_gamma * old_i / (MAPT.length - 1);
                for (int i = 0; i < MAPT.length; i++) {
                    if (i != formerSourcePartitionIndex) {
                        if (MAPT[formerSourcePartitionIndex][i] > threshold) {
                            MAPT[formerSourcePartitionIndex][i] -= threshold;
                        }
                    }
                    sum += MAPT[formerSourcePartitionIndex][i];
                }
                MAPT[formerSourcePartitionIndex][formerSourcePartitionIndex] = 1 - sum;
            } else { // same partition and do not kill a mutant
                double threshod = MAPT_tau * (1 - old_i) / (MAPT.length - 1);
                for (int i = 0; i < MAPT.length; i++) {
                    if (i != formerSourcePartitionIndex) {
                        if (MAPT[formerSourcePartitionIndex][formerSourcePartitionIndex] > threshod) {
                            MAPT[formerSourcePartitionIndex][i] +=
                                    MAPT_tau * MAPT[formerSourcePartitionIndex][i] / (MAPT.length - 1);
                        }
                    } else {
                        if (MAPT[formerSourcePartitionIndex][formerSourcePartitionIndex] > threshod) {
                            MAPT[i][i] -= MAPT_tau * (1 - MAPT[i][i]) / (MAPT.length - 1);
                        }
                    }
                }
            }
        } else { //source test case and follow-up test case do not belong to same partition
            // the test case do not kill a mutant
            if (isKilledMutans) {
                double sum = 0;
                double threshold = MAPT_gamma * (old_i + old_f) / (MAPT.length - 2);

                for (int i = 0; i < MAPT.length; i++) {
                    if (i != formerSourcePartitionIndex && i != formerfollowUpPartitionIndex) {
                        if (MAPT[formerSourcePartitionIndex][i] > threshold) {
                            MAPT[formerSourcePartitionIndex][i] -= threshold;
                        }
                    }
                    sum += MAPT[formerSourcePartitionIndex][i];
                }

                MAPT[formerSourcePartitionIndex][formerSourcePartitionIndex] = old_i +
                        ((1 - sum) - old_i - old_f) / 2;
                MAPT[formerSourcePartitionIndex][formerfollowUpPartitionIndex] = old_f +
                        ((1 - sum) - old_i - old_f) / 2;
            } else { // source test case and follow-up test case are not belonging to the same partition and do not reveal a mutant

                double threshold = (MAPT_tau * (1 - old_i - old_f)) / (MAPT.length - 2);
                for (int i = 0; i < MAPT.length; i++) {
                    if (i != formerSourcePartitionIndex && i != formerfollowUpPartitionIndex) {
                        if (old_i > threshold || old_f > threshold) {
                            MAPT[formerSourcePartitionIndex][i] += MAPT_tau *
                                    MAPT[formerSourcePartitionIndex][i] / (MAPT.length - 2);
                        }
                    }
                }
                if (old_i > threshold) {
                    MAPT[formerSourcePartitionIndex][formerSourcePartitionIndex] -= threshold;
                }
                if (old_f > threshold) {
                    MAPT[formerSourcePartitionIndex][formerfollowUpPartitionIndex] -= threshold;
                }

            }
        }
    }

    private Object getTestCase(String objectName, String testframe) {
        if (objectName.equals("ACMS")) {
            TestCase4ACMS tc = new MT4ACMS().generateTestCase(testframe);
            return tc;
        } else if (objectName.equals("CUBS")) {
            TestCase4CUBS tc = new MT4CUBS().generateTestCase(testframe);
            return tc;
        } else if (objectName.equals("ERS")) {
            TestCase4ERS tc = new MT4ERS().generateTestCase(testframe);
            return tc;
        } else {
            TestCase4MOS tc = new MT4MOS().generateTestCase(testframe);
            return tc;
        }
    }

    /**
     * select test case : MAPT
     * select MR: RT
     */
    public void MAPTwithRandomlySelectMR(String objectName) {

        TestCasesOfPartition testCasesOfPartition = new TestCasesOfPartition(objectName);

        //record all the time of selecting test cases for detecting the first fault
        List<Long> firstSelectTestCaseArray = new ArrayList<>();

        //record all the time of generate test cases for detecting the first fault
        List<Long> firstgenerateTestCaseArray = new ArrayList<>();

        //record all the time of execute test cases for detecting the first fault
        List<Long> firstExecuteTestCaseArray = new ArrayList<>();

        //record all the time of selecting test cases for detecting all faults
        List<Long> allSelectTestCaseArray = new ArrayList<>();

        //record all the time of generate test cases for detecting all faults
        List<Long> allgenerateTestCaseArray = new ArrayList<>();

        //record all the time of execute test cases for detecting allfaults
        List<Long> allExecuteTestCaseArray = new ArrayList<>();

        List<Integer> FmeasureArray = new ArrayList<>();

        List<Integer> TmeasureArray = new ArrayList<>();

        //实例化control类
        Control control = new Control(objectName);

        int numberOfMr = 0;
        String path = "";
        if (objectName.equals("ACMS")) {
            numberOfMr = Constant.numberofMr4ACMS;
            path = Constant.mrPath4ACMS;
        } else if (objectName.equals("CUBS")) {
            numberOfMr = Constant.numberofMr4CUBS;
            path = Constant.mrPath4CUBS;
        } else if (objectName.equals("ERS")) {
            numberOfMr = Constant.numberofMr4ERS;
            path = Constant.mrPath4ERS;
        } else {
            numberOfMr = Constant.numberofMr4MOS;
            path = Constant.mrPath4MOS;
        }


        BufferedReader bufferedReader = null;

        Map<String, String> mrInfo = new HashMap<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String tempStr = "";
            int tempInteger = 1;
            while ((tempStr = bufferedReader.readLine()) != null) {
                mrInfo.put(String.valueOf(tempInteger), tempStr);
                tempInteger++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Constant.repeatNumber; i++) {

            System.out.println("DMT4" + objectName + "使用MAPT+RT:" + "执行第" + String.valueOf(i + 1) + "次测试：");
            //初始化测试剖面
            initializeMAPT(Constant.getPartitionNumber(objectName));

            UsedMutantsSet mutantsSet = new UsedMutantsSet(objectName);
            Map<String, Mutant> mutantMap = mutantsSet.getMutants();
            // the list that includes the killed mutants
            Set<String> killedMutants = new HashSet<>();

            String methodName = new Methods4Testing(objectName).getMethodName();
            // the counter
            int counter = 0;

            // the selecting time of killing the first mutant
            long firstSelectingTime = 0;
            // the generating time of killing the first mutant
            long firstGeneratingTime = 0;
            // the executing time of killing the first muatant
            long firstExecutingTime = 0;

            // the selecting time of killing all mutants
            long allSelectingTime = 0;
            // the generating time of killing all mutants
            long allGeneratingTime = 0;
            // the executing time of killing all mutants
            long allExecutingTime = 0;

            // the F-measure
            int Fmeasure = 0;
            // the T-measure
            int Tmeasure = 0;

            for (int j = 0; j < 10000; j++) {
                //get a test case
                String testframesAndMr = "";
                // the number of executing test case add 1
                counter++;
                String sourceTestFrame = "";
                String followTestFrame = "";
                String MR = "";

                /**choose a partition and a source test case*/
                long startSelectTestCase = System.nanoTime();
                // choose a partition according to the test profile
                int partitionIndex = 0;
                if (counter == 1) {
                    partitionIndex = new Random().
                            nextInt(Constant.getPartitionNumber(objectName));
                } else {
                    partitionIndex = nextPartition4MAPT(partitionIndex);
                }

                //select a test case
                testframesAndMr = testCasesOfPartition.
                        getSourceFollowAndMR(partitionIndex);
                long endSelectTestCase = System.nanoTime();
                if (killedMutants.size() == 0) {
                    firstSelectingTime += (endSelectTestCase - startSelectTestCase);
                }
                allSelectingTime += (endSelectTestCase - startSelectTestCase);

                /**get source test case, follow up test case, and MR*/
                sourceTestFrame = testframesAndMr.split(";")[0];
                followTestFrame = testframesAndMr.split(";")[1];
                if (!objectName.equals("MOS")) {
                    MR = testframesAndMr.split(";")[2];
                } else {
                    String tempStr = "";
                    for (int z = 2; z < testframesAndMr.split(";").length; z++) {
                        tempStr += testframesAndMr.split(";")[z] + ";";
                    }
                    MR = tempStr;
                    MR = MR.substring(0, MR.length() - 1);

                    String[] choice = MR.split(";");
                    MR = choice[0] + ";" + choice[1] + ";" + choice[2] + ";" + choice[3] + ";"
                            + choice[5] + ";" + choice[6] + ";" + choice[7];
                }

                int partitionIndexOffollowTestCase = control.
                        judgeThePartitionOfFollowTestFrame(objectName, followTestFrame);
                boolean isKilledMutants = false;
                for (Map.Entry<String, Mutant> entry : mutantMap.entrySet()) {

                    if (killedMutants.contains(entry.getKey())) {
                        continue;
                    }
                    Mutant mutant = entry.getValue();
                    Object mutantInstance = null;
                    Method mutantMethod = null;
                    Class mutantClazz = null;
                    try {
                        mutantClazz = Class.forName(mutant.getFullName());
                        Constructor mutantConstructor = mutantClazz.getConstructor();
                        mutantInstance = mutantConstructor.newInstance();
                        if (objectName.equals("ACMS")) {
                            double sourceResult = 0;
                            double followUpResult = 0;
                            mutantMethod = mutantClazz.getMethod(methodName, int.class, int.class,
                                    boolean.class, double.class, double.class);
                            // generate source test case and follow-up test case
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = getTestCase(objectName, sourceTestFrame);
                            Object ftc = getTestCase(objectName, followTestFrame);
                            TestCase4ACMS sourceTestCase = (TestCase4ACMS) stc;
                            TestCase4ACMS followUpTestCase = (TestCase4ACMS) ftc;
                            long endGenerateTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
                            }
                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);


                            //execute source test case and follow-up test case on all mutants
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (double) mutantMethod.invoke(mutantInstance,
                                    sourceTestCase.getAirClass(), sourceTestCase.getArea(),
                                    sourceTestCase.isStudent(), sourceTestCase.getLuggage(),
                                    sourceTestCase.getEconomicfee());

                            followUpResult = (double) mutantMethod.invoke(mutantInstance,
                                    followUpTestCase.getAirClass(), followUpTestCase.getArea(),
                                    followUpTestCase.isStudent(), followUpTestCase.getLuggage(),
                                    followUpTestCase.getEconomicfee());
                            long endExecuteTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
                            }
                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);

                            if (MR.equals("The output will not change") && sourceResult != followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }
                        } else if (objectName.equals("CUBS")) {
                            double sourceResult = 0;
                            double followUpResult = 0;
                            mutantMethod = mutantClazz.getMethod(methodName, String.class, int.class,
                                    int.class, int.class);
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = getTestCase(objectName, sourceTestFrame);
                            Object ftc = getTestCase(objectName, followTestFrame);
                            TestCase4CUBS sourceTestCase = (TestCase4CUBS) stc;
                            TestCase4CUBS followUpTestCase = (TestCase4CUBS) ftc;
                            long endGenerateTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
                            }
                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);


                            //execute source test case and follow-up test case on all mutants
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (double) mutantMethod.invoke(mutantInstance,
                                    sourceTestCase.getPlanType(),
                                    sourceTestCase.getPlanFee(), sourceTestCase.getTalkTime(), sourceTestCase.getFlow());

                            followUpResult = (double) mutantMethod.invoke(mutantInstance,
                                    followUpTestCase.getPlanType(),
                                    followUpTestCase.getPlanFee(), followUpTestCase.getTalkTime(), followUpTestCase.getFlow());
                            long endExecuteTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
                            }
                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);
                            if (MR.equals("The output will not change") && sourceResult != followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }


                        } else if (objectName.equals("ERS")) {
                            double sourceResult = 0;
                            double followUpResult = 0;
                            mutantMethod = mutantClazz.getMethod(methodName, String.class, double.class,
                                    double.class, double.class, double.class);
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = getTestCase(objectName, sourceTestFrame);
                            Object ftc = getTestCase(objectName, followTestFrame);
                            TestCase4ERS sourceTestCase = (TestCase4ERS) stc;
                            TestCase4ERS followUpTestCase = (TestCase4ERS) ftc;
                            long endGenerateTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
                            }
                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);


                            //execute source test case and follow-up test case on all mutants
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (double) mutantMethod.invoke(mutantInstance, sourceTestCase.getStafflevel(),
                                    sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
                                    sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());

                            followUpResult = (double) mutantMethod.invoke(mutantInstance, followUpTestCase.getStafflevel(),
                                    sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
                                    sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());
                            long endExecuteTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
                            }
                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);
                            if (MR.equals("The output will not change") && sourceResult != followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }


                        } else {
                            MSR sourceResult = null;
                            MSR followUpResult = null;
                            mutantMethod = mutantClazz.getMethod(methodName, String.class, String.class, int.class,
                                    String.class, int.class, int.class, int.class);

                            long startGenerateTestCase = System.nanoTime();
                            Object stc = getTestCase(objectName, sourceTestFrame);
                            Object ftc = getTestCase(objectName, followTestFrame);
                            TestCase4MOS sourceTestCase = (TestCase4MOS) stc;
                            TestCase4MOS followUpTestCase = (TestCase4MOS) ftc;
                            long endGenerateTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
                            }
                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);


                            //execute source test case and follow-up test case on all mutants
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (MSR) mutantMethod.invoke(mutantInstance, sourceTestCase.getAircraftmodel(),
                                    sourceTestCase.getChangeinthenumberofcrewmembers(), sourceTestCase.getNewnumberofcrewmembers(),
                                    sourceTestCase.getChangeinthenumberofpilots(), sourceTestCase.getNewnumberofpilots(),
                                    sourceTestCase.getNumberofchildpassengers(), sourceTestCase.getNumberofrequestedbundlesofflowers());

                            followUpResult = (MSR) mutantMethod.invoke(mutantInstance, followUpTestCase.getAircraftmodel(),
                                    followUpTestCase.getChangeinthenumberofcrewmembers(), followUpTestCase.getNewnumberofcrewmembers(),
                                    followUpTestCase.getChangeinthenumberofpilots(), followUpTestCase.getNewnumberofpilots(),
                                    followUpTestCase.getNumberofchildpassengers(), followUpTestCase.getNumberofrequestedbundlesofflowers());
                            long endExecuteTestCase = System.nanoTime();
                            if (killedMutants.size() == 0) {
                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
                            }
                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);


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
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }


                        }
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
                }//mutants
                adjustMAPT(partitionIndex, partitionIndexOffollowTestCase, isKilledMutants);
                if (killedMutants.size() == Constant.getMutantsNumber(objectName)) {
                    break;
                }
//                printMAPT();
            }
            FmeasureArray.add(Fmeasure);
            TmeasureArray.add(Tmeasure);


            //add each time to array
            firstSelectTestCaseArray.add(firstSelectingTime);
            firstgenerateTestCaseArray.add(firstGeneratingTime);
            firstExecuteTestCaseArray.add(firstExecutingTime);
            allSelectTestCaseArray.add(allSelectingTime);
            allgenerateTestCaseArray.add(allGeneratingTime);
            allExecuteTestCaseArray.add(allExecutingTime);
        }


    }

}
