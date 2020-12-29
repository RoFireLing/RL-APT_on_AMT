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
 *
 * @author phantom
 * @date 2019/05/14
 */
public class DMTwithRAPTandPB {

    private static final int[] pun4ACMS = {79, 73, 67, 60, 96, 84, 29, 23};
    private static final int[] pun4CUBS = {50, 5, 43};
    private static final int[] pun4ERS = {91, 89, 88, 126, 62, 39, 39, 51, 55, 52, 35, 53};
    private static final int[] pun4MOS = {390, 298, 403, 261, 403, 217, 226, 84, 132, 21};


    /**
     * the test profile of RAPT
     */
    private double[] RAPT;

    private double RAPT_epsilon = 0.05;

    private double RAPT_delta;

    /**
     * the factor of reward
     */
    private int[] rew;

    /**
     * the factor of punishment
     */
    private int[] bou;

    private int[] pun;

    public static void main(String[] args) {
        DMTwithRAPTandPB dmTwithRAPTandPB = new DMTwithRAPTandPB();
//        String[] names = {"CUBS", "ERS", "MOS"};
        String[] names = {"ERS"};
        for (int i = 0; i < 20; i++) {
            for (String name : names) {
                dmTwithRAPTandPB.RAPTwithPBSelectMR(name);
            }
        }
    }

    public void setRAPT_delta(double RAPT_delta) {
        this.RAPT_delta = RAPT_delta;
    }

    public void setBou_RAPT(String objectName, int[] punArray) {
        if (objectName.equals("ACMS")) {
            rew = new int[8];
            pun = new int[8];
            bou = new int[8];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        } else if (objectName.equals("CUBS")) {
            rew = new int[3];
            pun = new int[3];
            bou = new int[3];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        } else if (objectName.equals("ERS")) {
            rew = new int[12];
            pun = new int[12];
            bou = new int[12];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        } else {
            rew = new int[10];
            pun = new int[10];
            bou = new int[10];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        }

    }

    /**
     * initialize the test profile of RAPT
     *
     * @param numberOfPartitions the number of partitions
     */
    private void initializeRAPT(int numberOfPartitions) {
        RAPT = new double[numberOfPartitions];
        for (int i = 0; i < RAPT.length; i++) {
            RAPT[i] = 1.0 / numberOfPartitions;
            pun[i] = 0;
        }
    }

    /**
     * get a index of partition
     * Note that the first number of partitions is 0
     *
     * @return the index
     */
    private int nextPartition4RAPT() {
        boolean flag = false;
        int partitionindex = 0;
        for (int i = 0; i < rew.length; i++) {
            if (rew[i] > 0) {
                flag = true;
                partitionindex = i;
                break;
            }
        }
        if (flag) {
            return partitionindex;
        } else {
            int index = -1;
            double randomNumber = new Random().nextDouble();
            double sum = 0;
            do {
                index++;
                sum += RAPT[index];
            } while (randomNumber >= sum && index < RAPT.length - 1);
            return index;
        }
    }

    /**
     * adjust the test profile for RAPT testing
     *
     * @param
     * @param isKilledMutant
     */
    private void adjustRAPT(int formersourcePartitionIndex,
                            int formerfollowUpPartitionIndex,
                            boolean isKilledMutant) {
        double old_i = RAPT[formersourcePartitionIndex];
        double old_f = RAPT[formerfollowUpPartitionIndex];

        if (formersourcePartitionIndex == formerfollowUpPartitionIndex) {
            if (isKilledMutant) {
                double sum = 0;
                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex) {
                        RAPT[i] -= (1 + Math.log(rew[formersourcePartitionIndex]))
                                * RAPT_epsilon / (RAPT.length - 1);
                        if (RAPT[i] < 0) {
                            RAPT[i] = 0;
                        }
                    }
                    sum += RAPT[i];
                }
                RAPT[formersourcePartitionIndex] = 1 - sum;
            } else {
                for (int i = 0; i < RAPT.length; i++) {
                    if (i == formersourcePartitionIndex) {
                        if (old_i >= RAPT_delta) {
                            RAPT[i] -= RAPT_delta;
                        }
                        if (old_i < RAPT_delta || bou[i] == pun[i]) {
                            RAPT[i] = 0;
                        }
                    } else {
                        if (old_i >= RAPT_delta) {
                            RAPT[i] += RAPT_delta / (RAPT.length - 1);
                        }
                        if (old_i < RAPT_delta || bou[i] == pun[i]) {
                            RAPT[i] += old_i / (RAPT.length - 1);
                        }
                    }
                }
            }
        } else { // not belong to the same partition
            if (isKilledMutant) {
                double sum = 0;
                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex && i != formerfollowUpPartitionIndex) {
                        if (RAPT[i] > (RAPT_epsilon * (1 + Math.log(rew[i])) / (RAPT.length - 2))) {
                            RAPT[i] -= RAPT_epsilon * (1 + Math.log(rew[i])) / (RAPT.length - 2);
                        } else {
                            RAPT[i] = 0;
                        }
                    }
                    sum += RAPT[i];
                }
                RAPT[formersourcePartitionIndex] = old_i + ((1 - sum) - old_i - old_f) / 2;
                RAPT[formerfollowUpPartitionIndex] = old_f + ((1 - sum) - old_f - old_i) / 2;
            } else { // not reveal a mutant
                if (old_i > RAPT_delta) {
                    RAPT[formersourcePartitionIndex] = old_i - RAPT_delta;
                }
                if (old_i <= RAPT_delta || pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]) {
                    RAPT[formersourcePartitionIndex] = 0;
                    if (pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]) {
                        pun[formersourcePartitionIndex] = 0;
                    }
                }
                if (old_f > RAPT_delta) {
                    RAPT[formerfollowUpPartitionIndex] = old_f - RAPT_delta;
                }
                if (old_f <= RAPT_delta || pun[formerfollowUpPartitionIndex] == bou[formerfollowUpPartitionIndex]) {
                    RAPT[formerfollowUpPartitionIndex] = 0;
                    if (pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]) {
                        pun[formersourcePartitionIndex] = 0;
                    }
                }

                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex && i != formerfollowUpPartitionIndex) {
                        RAPT[i] += ((old_i - RAPT[formersourcePartitionIndex]) +
                                (old_f - RAPT[formerfollowUpPartitionIndex])) / (RAPT.length - 2);
                    }
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


//    public void RAPTwithPBSelectMR(String objectName){
//
//        TestCasesOfPartition testCasesOfPartition = new TestCasesOfPartition(objectName);
//
//        //record all the time of selecting test cases for detecting the first fault
//        List<Long> firstSelectTestCaseArray = new ArrayList<>();
//
//        //record all the time of generate test cases for detecting the first fault
//        List<Long> firstgenerateTestCaseArray = new ArrayList<>();
//
//        //record all the time of execute test cases for detecting the first fault
//        List<Long> firstExecuteTestCaseArray = new ArrayList<>();
//
//        //record all the time of selecting test cases for detecting all faults
//        List<Long> allSelectTestCaseArray = new ArrayList<>();
//
//        //record all the time of generate test cases for detecting all faults
//        List<Long> allgenerateTestCaseArray = new ArrayList<>();
//
//        //record all the time of execute test cases for detecting allfaults
//        List<Long> allExecuteTestCaseArray = new ArrayList<>();
//
//        List<Integer> FmeasureArray = new ArrayList<>();
//
//        List<Integer> TmeasureArray = new ArrayList<>();
//
//        //实例化control类
//        Control control = new Control(objectName);
//
//        int numberOfMr = 0;
//        String path = "";
//        if (objectName.equals("ACMS")){
//            numberOfMr = Constant.numberofMr4ACMS;
//            path = Constant.mrPath4ACMS;
//        } else if (objectName.equals("CUBS")){
//            numberOfMr = Constant.numberofMr4CUBS;
//            path = Constant.mrPath4CUBS;
//        }else if (objectName.equals("ERS")){
//            numberOfMr = Constant.numberofMr4ERS;
//            path = Constant.mrPath4ERS;
//        }else {
//            numberOfMr = Constant.numberofMr4MOS;
//            path = Constant.mrPath4MOS;
//        }
//
//
//        BufferedReader bufferedReader = null;
//
//        Map<String,String> mrInfo = new HashMap<>();
//        try {
//            bufferedReader = new BufferedReader(new FileReader(path));
//            String tempStr = "";
//            int tempInteger = 1;
//            while((tempStr = bufferedReader.readLine()) != null){
//                mrInfo.put(String.valueOf(tempInteger), tempStr);
//                tempInteger++;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < Constant.repeatNumber; i++) {
//            setParameters4RAPT(objectName);
//            System.out.println("DMT4" + objectName + "使用RAPT+PB:" + "执行第"+ String.valueOf(i + 1) + "次测试：" );
//            //初始化测试剖面
//            initializeRAPT(Constant.getPartitionNumber(objectName));
//
//            UsedMutantsSet mutantsSet = new UsedMutantsSet(objectName);
//            Map<String, Mutant> mutantMap = mutantsSet.getMutants();
//            // the list that includes the killed mutants
//            Set<String> killedMutants = new HashSet<>();
//
//            String methodName = new Methods4Testing(objectName).getMethodName();
//            // the counter
//            int counter = 0;
//
//            // the selecting time of killing the first mutant
//            long firstSelectingTime = 0;
//            // the generating time of killing the first mutant
//            long firstGeneratingTime = 0;
//            // the executing time of killing the first muatant
//            long firstExecutingTime = 0;
//
//            // the selecting time of killing all mutants
//            long allSelectingTime = 0;
//            // the generating time of killing all mutants
//            long allGeneratingTime = 0;
//            // the executing time of killing all mutants
//            long allExecutingTime = 0;
//
//            // the F-measure
//            int Fmeasure = 0;
//            // the T-measure
//            int Tmeasure = 0;
//
//            for (int j = 0; j < 10000; j++) {
//                //get a test case
//                String testframesAndMr = "";
//                // the number of executing test case add 1
//                counter++;
//                String sourceTestFrame = "";
//                String followTestFrame = "";
//                String MR = "";
//
//                /**choose a partition and a source test case*/
//                long startSelectTestCase = System.nanoTime();
//                // choose a partition according to the test profile
//                int partitionIndex = nextPartition4RAPT();
//
//                //select a test case
//                testframesAndMr = testCasesOfPartition.
//                        getSourceFollowAndMR(partitionIndex);
//                long endSelectTestCase = System.nanoTime();
//                if (killedMutants.size() == 0){
//                    firstSelectingTime += (endSelectTestCase - startSelectTestCase);
//                }
//                allSelectingTime += (endSelectTestCase - startSelectTestCase);
//                /**get source test case, follow up test case, and MR*/
//                sourceTestFrame = testframesAndMr.split(";")[0];
//                //generate new source and follow test frames and MR
//                testframesAndMr = control.PBMRGetMR(partitionIndex,sourceTestFrame);
//                followTestFrame = testframesAndMr.split(";")[1];
//                if (!objectName.equals("MOS")){
//                    MR = testframesAndMr.split(";")[2];
//                }else {
//                    String tempStr = "";
//                    for (int z = 2; z < testframesAndMr.split(";").length; z++) {
//                        tempStr += testframesAndMr.split(";")[z] + ";";
//                    }
//                    MR = tempStr;
//                    MR = MR.substring(0, MR.length() - 1);
//
//                    String[] choice = MR.split(";");
//                    MR = choice[0] +";" + choice[1]+";" + choice[2]+";" + choice[3]+";"
//                            + choice[5]+";" + choice[6]+";" + choice[7];
//                }
//
//                int partitionIndexOffollowTestCase = control.
//                        judgeThePartitionOfFollowTestFrame(objectName, followTestFrame);
//                boolean isKilledMutants = false;
//                for (Map.Entry<String, Mutant> entry : mutantMap.entrySet()){
//
//                    if (killedMutants.contains(entry.getKey())){
//                        continue;
//                    }
//                    Mutant mutant = entry.getValue();
//                    Object mutantInstance = null;
//                    Method mutantMethod = null;
//                    Class mutantClazz = null;
//                    try {
//                        mutantClazz = Class.forName(mutant.getFullName());
//                        Constructor mutantConstructor = mutantClazz.getConstructor();
//                        mutantInstance = mutantConstructor.newInstance();
//                        if (objectName.equals("ACMS")){
//                            double sourceResult = 0;
//                            double followUpResult = 0;
//                            mutantMethod = mutantClazz.getMethod(methodName, int.class, int.class,
//                                    boolean.class, double.class, double.class);
//                            // generate source test case and follow-up test case
//                            long startGenerateTestCase = System.nanoTime();
//                            Object stc = getTestCase(objectName,sourceTestFrame);
//                            Object ftc = getTestCase(objectName,followTestFrame);
//                            TestCase4ACMS sourceTestCase = (TestCase4ACMS) stc;
//                            TestCase4ACMS followUpTestCase = (TestCase4ACMS) ftc;
//                            long endGenerateTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//                            }
//                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//
//
//                            //execute source test case and follow-up test case on all mutants
//                            long startExecuteTestCase = System.nanoTime();
//                            sourceResult = (double) mutantMethod.invoke(mutantInstance,
//                                    sourceTestCase.getAirClass(),sourceTestCase.getArea(),
//                                    sourceTestCase.isStudent(),sourceTestCase.getLuggage(),
//                                    sourceTestCase.getEconomicfee());
//
//                            followUpResult = (double) mutantMethod.invoke(mutantInstance,
//                                    followUpTestCase.getAirClass(),followUpTestCase.getArea(),
//                                    followUpTestCase.isStudent(),followUpTestCase.getLuggage(),
//                                    followUpTestCase.getEconomicfee());
//                            long endExecuteTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//                            }
//                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//
//                            if (MR.equals("The output will not change") && sourceResult != followUpResult){
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//                            if (MR.equals("The output will increase") && sourceResult >= followUpResult){
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//                        } else if (objectName.equals("CUBS")){
//                            double sourceResult = 0;
//                            double followUpResult = 0;
//                            mutantMethod = mutantClazz.getMethod(methodName, String.class, int.class,
//                                    int.class, int.class);
//                            long startGenerateTestCase = System.nanoTime();
//                            Object stc = getTestCase(objectName,sourceTestFrame);
//                            Object ftc = getTestCase(objectName,followTestFrame);
//                            TestCase4CUBS sourceTestCase = (TestCase4CUBS) stc;
//                            TestCase4CUBS followUpTestCase = (TestCase4CUBS) ftc;
//                            long endGenerateTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//                            }
//                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//
//
//                            //execute source test case and follow-up test case on all mutants
//                            long startExecuteTestCase = System.nanoTime();
//                            sourceResult = (double) mutantMethod.invoke(mutantInstance,
//                                    sourceTestCase.getPlanType(),
//                                    sourceTestCase.getPlanFee(), sourceTestCase.getTalkTime(),sourceTestCase.getFlow());
//
//                            followUpResult = (double) mutantMethod.invoke(mutantInstance,
//                                    followUpTestCase.getPlanType(),
//                                    followUpTestCase.getPlanFee(), followUpTestCase.getTalkTime(),followUpTestCase.getFlow());
//                            long endExecuteTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//                            }
//                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//                            if (MR.equals("The output will not change") && sourceResult != followUpResult){
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//                            if (MR.equals("The output will increase") && sourceResult >= followUpResult){
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//                            if (MR.equals("The output will decrease") && sourceResult <= followUpResult){
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//
//                        }else if (objectName.equals("ERS")){
//                            double sourceResult = 0;
//                            double followUpResult = 0;
//                            mutantMethod = mutantClazz.getMethod(methodName, String.class, double.class,
//                                    double.class, double.class, double.class);
//                            long startGenerateTestCase = System.nanoTime();
//                            Object stc = getTestCase(objectName,sourceTestFrame);
//                            Object ftc = getTestCase(objectName,followTestFrame);
//                            TestCase4ERS sourceTestCase = (TestCase4ERS) stc;
//                            TestCase4ERS followUpTestCase = (TestCase4ERS) ftc;
//                            long endGenerateTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//                            }
//                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//
//
//                            //execute source test case and follow-up test case on all mutants
//                            long startExecuteTestCase = System.nanoTime();
//                            sourceResult = (double) mutantMethod.invoke(mutantInstance,sourceTestCase.getStafflevel(),
//                                    sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
//                                    sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());
//
//                            followUpResult = (double) mutantMethod.invoke(mutantInstance,followUpTestCase.getStafflevel(),
//                                    sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
//                                    sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());
//                            long endExecuteTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//                            }
//                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//                            if (MR.equals("The output will not change") && sourceResult != followUpResult) {
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//                            if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//
//                        }else {
//                            MSR sourceResult = null;
//                            MSR followUpResult = null;
//                            mutantMethod = mutantClazz.getMethod(methodName, String.class, String.class, int.class,
//                                    String.class,int.class,int.class,int.class);
//
//                            long startGenerateTestCase = System.nanoTime();
//                            Object stc = getTestCase(objectName,sourceTestFrame);
//                            Object ftc = getTestCase(objectName,followTestFrame);
//                            TestCase4MOS sourceTestCase = (TestCase4MOS) stc;
//                            TestCase4MOS followUpTestCase = (TestCase4MOS) ftc;
//                            long endGenerateTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//                            }
//                            allGeneratingTime += (endGenerateTestCase - startGenerateTestCase);
//
//
//                            //execute source test case and follow-up test case on all mutants
//                            long startExecuteTestCase = System.nanoTime();
//                            sourceResult = (MSR) mutantMethod.invoke(mutantInstance,sourceTestCase.getAircraftmodel(),
//                                    sourceTestCase.getChangeinthenumberofcrewmembers(),sourceTestCase.getNewnumberofcrewmembers(),
//                                    sourceTestCase.getChangeinthenumberofpilots(),sourceTestCase.getNewnumberofpilots(),
//                                    sourceTestCase.getNumberofchildpassengers(),sourceTestCase.getNumberofrequestedbundlesofflowers());
//
//                            followUpResult = (MSR) mutantMethod.invoke(mutantInstance,followUpTestCase.getAircraftmodel(),
//                                    followUpTestCase.getChangeinthenumberofcrewmembers(),followUpTestCase.getNewnumberofcrewmembers(),
//                                    followUpTestCase.getChangeinthenumberofpilots(),followUpTestCase.getNewnumberofpilots(),
//                                    followUpTestCase.getNumberofchildpassengers(),followUpTestCase.getNumberofrequestedbundlesofflowers());
//                            long endExecuteTestCase = System.nanoTime();
//                            if (killedMutants.size() == 0){
//                                firstExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//                            }
//                            allExecutingTime += (endExecuteTestCase - startExecuteTestCase);
//
//
//                            String resultRelation = "";
//
//
//                            if (sourceResult.numberOfMealsForCrewMembers == followUpResult.numberOfMealsForCrewMembers){
//                                resultRelation += "do not change;";
//                            }else if (sourceResult.numberOfMealsForCrewMembers > followUpResult.numberOfMealsForCrewMembers){
//                                resultRelation += "decrease;";
//                            }else {
//                                resultRelation += "increase;";
//                            }
//
//                            if (sourceResult.numberOfMealsForPilots == followUpResult.numberOfMealsForPilots){
//                                resultRelation += "do not change;";
//                            }else if (sourceResult.numberOfMealsForPilots > followUpResult.numberOfMealsForPilots){
//                                resultRelation += "decrease;";
//                            }else {
//                                resultRelation += "increase;";
//                            }
//
//                            if (sourceResult.numberOfChildMeals == followUpResult.numberOfChildMeals){
//                                resultRelation += "do not change;";
//                            }else if (sourceResult.numberOfChildMeals > followUpResult.numberOfChildMeals){
//                                resultRelation += "decrease;";
//                            }else {
//                                resultRelation += "increase;";
//                            }
//
//                            if (sourceResult.numberOfBundlesOfFlowers == followUpResult.numberOfBundlesOfFlowers){
//                                resultRelation += "do not change;";
//                            }else if (sourceResult.numberOfBundlesOfFlowers > followUpResult.numberOfBundlesOfFlowers){
//                                resultRelation += "decrease;";
//                            }else {
//                                resultRelation += "increase;";
//                            }
//
//                            if (sourceResult.numberOfFirstClassMeals == followUpResult.numberOfFirstClassMeals){
//                                resultRelation += "do not change;";
//                            }else if (sourceResult.numberOfFirstClassMeals > followUpResult.numberOfFirstClassMeals){
//                                resultRelation += "decrease;";
//                            }else {
//                                resultRelation += "increase;";
//                            }
//
//                            if (sourceResult.numberOfBusinessClassMeals == followUpResult.numberOfBusinessClassMeals){
//                                resultRelation += "do not change;";
//                            }else if (sourceResult.numberOfBusinessClassMeals > followUpResult.numberOfBusinessClassMeals){
//                                resultRelation += "decrease;";
//                            }else {
//                                resultRelation += "increase;";
//                            }
//
//                            if (sourceResult.numberOfEconomicClassMeals == followUpResult.numberOfEconomicClassMeals){
//                                resultRelation += "do not change";
//                            }else if (sourceResult.numberOfEconomicClassMeals > followUpResult.numberOfEconomicClassMeals){
//                                resultRelation += "decrease";
//                            }else {
//                                resultRelation += "increase";
//                            }
//
//                            if (!MR.equals(resultRelation)){
//                                isKilledMutants = true;
//                                if (killedMutants.size() == 0){
//                                    Fmeasure = counter;
//                                }
//                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1){
//                                    Tmeasure = counter;
//                                }
//                                killedMutants.add(entry.getKey());
//                            }
//
//
//                        }
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    } catch (InstantiationException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                }//mutants
//                adjustRAPT(partitionIndex,partitionIndexOffollowTestCase, isKilledMutants);
//                if (killedMutants.size() == Constant.getMutantsNumber(objectName)){
//                    break;
//                }
//            }
//            FmeasureArray.add(Fmeasure);
//            TmeasureArray.add(Tmeasure);
//
//
//            //add each time to array
//            firstSelectTestCaseArray.add(firstSelectingTime);
//            firstgenerateTestCaseArray.add(firstGeneratingTime);
//            firstExecuteTestCaseArray.add(firstExecutingTime);
//            allSelectTestCaseArray.add(allSelectingTime);
//            allgenerateTestCaseArray.add(allGeneratingTime);
//            allExecuteTestCaseArray.add(allExecutingTime);
//        }
//
//        RecordResult.recordResult("DMTwithRAPT_PB4" + objectName, FmeasureArray, TmeasureArray,
//                firstSelectTestCaseArray, firstgenerateTestCaseArray,firstExecuteTestCaseArray,
//                allSelectTestCaseArray,allgenerateTestCaseArray,allExecuteTestCaseArray,
//                getAveragemeasure(FmeasureArray), getAveragemeasure(TmeasureArray),
//                getAverageTime(firstSelectTestCaseArray), getAverageTime(firstgenerateTestCaseArray),
//                getAverageTime(firstExecuteTestCaseArray),getAverageTime(allSelectTestCaseArray),
//                getAverageTime(allgenerateTestCaseArray), getAverageTime(allExecuteTestCaseArray));
//    }

    private void setParameters4RAPT(String objectName) {
        if (objectName.equals("ACMS")) {
            setRAPT_delta(0.01718312324929972);
            setBou_RAPT(objectName, pun4ACMS);
        } else if (objectName.equals("CUBS")) {
            setRAPT_delta(0.013619282726462319);
            setBou_RAPT(objectName, pun4CUBS);
        } else if (objectName.equals("ERS")) {
            setRAPT_delta(0.019839685749552596);
            setBou_RAPT(objectName, pun4ERS);
        } else {
            setRAPT_delta(0.009424821887743086);
            setBou_RAPT(objectName, pun4MOS);
        }
    }

    public void RAPTwithPBSelectMR(String objectName) {

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
            setParameters4RAPT(objectName);
            System.out.println("DMT4" + objectName + "使用RAPT+PB:" + "执行第" + String.valueOf(i + 1) + "次测试：");
            //初始化测试剖面
            initializeRAPT(Constant.getPartitionNumber(objectName));

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
                int partitionIndex = nextPartition4RAPT();

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
                testframesAndMr = control.PBMRGetMR(partitionIndex, sourceTestFrame);
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
                adjustRAPT(partitionIndex, partitionIndexOffollowTestCase, isKilledMutants);
                if (killedMutants.size() == Constant.getMutantsNumber(objectName)) {
                    break;
                }
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
