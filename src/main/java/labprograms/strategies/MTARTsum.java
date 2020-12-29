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
 * select source test case: ARTsum
 * select MR: random
 *
 * @author phantom
 * @date 2019/05/13
 */
public class MTARTsum implements Strategy {

    /**
     * include the candidate test cases
     */
    private List<String> candidatesTestCases;

    /**
     * include the test cases that has been executed
     */
    private List<String> executeTestCases;


    private int[] S;

    public static void main(String[] args) {
        MTARTsum mtarTsum = new MTARTsum();
//        String[] names = {"ACMS", "CUBS", "ERS", "MOS"};
        String[] names = {"ACMS"};
        for (int i = 0; i < 500; i++) {
            for (String name : names) {
                mtarTsum.executeTestCase(name, i);
            }
        }
    }

    @Override
    public Object getTestCase(String objectName, String testframe) {
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
     * 每次重复执行测试需要调用该函数，来初始化S
     *
     * @param objectName 被测对象的名字
     */
    private void initializeS(String objectName, String sourceTestFrame) {
        if (objectName.equals("ACMS")) {
            S = new int[17];
            if (sourceTestFrame.contains("I-1a")) {
                S[1] += 1;
            } else if (sourceTestFrame.contains("I-1b")) {
                S[2] += 1;
            } else if (sourceTestFrame.contains("I-1c")) {
                S[3] += 1;
            } else if (sourceTestFrame.contains("I-1d")) {
                S[4] += 1;
            }
            if (sourceTestFrame.contains("I-2a")) {
                S[6] += 1;
            } else if (sourceTestFrame.contains("I-2b")) {
                S[7] += 1;
            }
            if (sourceTestFrame.contains("I-3a")) {
                S[9] += 1;
            } else if (sourceTestFrame.contains("I-3b")) {
                S[10] += 1;
            }
            if (sourceTestFrame.contains("I-4a")) {
                S[12] += 1;
            } else if (sourceTestFrame.contains("I-4b")) {
                S[13] += 1;
            }
            if (sourceTestFrame.contains("I-5a")) {
                S[15] += 1;
            } else if (sourceTestFrame.contains("I-5b")) {
                S[16] += 1;
            }
        } else if (objectName.equals("CUBS")) {
            S = new int[16];
            if (sourceTestFrame.contains("I-1a")) {
                S[1] += 1;
            } else if (sourceTestFrame.contains("I-1b")) {
                S[2] += 1;
            }
            if (sourceTestFrame.contains("I-2a")) {
                S[4] += 1;
            } else if (sourceTestFrame.contains("I-2b")) {
                S[5] += 1;
            } else if (sourceTestFrame.contains("I-2c")) {
                S[6] += 1;
            } else if (sourceTestFrame.contains("I-2d")) {
                S[7] += 1;
            } else if (sourceTestFrame.contains("I-2e")) {
                S[8] += 1;
            } else if (sourceTestFrame.contains("I-2f")) {
                S[9] += 1;
            }
            if (sourceTestFrame.contains("I-3a")) {
                S[11] += 1;
            } else if (sourceTestFrame.contains("I-3b")) {
                S[12] += 1;
            }
            if (sourceTestFrame.contains("I-4a")) {
                S[14] += 1;
            } else if (sourceTestFrame.contains("I-4b")) {
                S[15] += 1;
            }
        } else if (objectName.equals("ERS")) {
            S = new int[19];
            if (sourceTestFrame.contains("I-1a")) {
                S[1] += 1;
            } else if (sourceTestFrame.contains("I-1b")) {
                S[2] += 1;
            } else if (sourceTestFrame.contains("I-1c")) {
                S[3] += 1;
            }
            if (sourceTestFrame.contains("I-2a")) {
                S[5] += 1;
            } else if (sourceTestFrame.contains("I-2b")) {
                S[6] += 1;
            } else if (sourceTestFrame.contains("I-2c")) {
                S[7] += 1;
            }
            if (sourceTestFrame.contains("I-3a")) {
                S[9] += 1;
            } else if (sourceTestFrame.contains("I-3b")) {
                S[10] += 1;
            } else if (sourceTestFrame.contains("I-3c")) {
                S[11] += 1;
            } else if (sourceTestFrame.contains("I-3d")) {
                S[12] += 1;
            }
            if (sourceTestFrame.contains("I-4a")) {
                S[14] += 1;
            } else if (sourceTestFrame.contains("I-4b")) {
                S[15] += 1;
            }
            if (sourceTestFrame.contains("I-5a")) {
                S[17] += 1;
            } else if (sourceTestFrame.contains("I-5b")) {
                S[18] += 1;
            }
        } else {
            S = new int[26];
            if (sourceTestFrame.contains("I-1a")) {
                S[1] += 1;
            } else if (sourceTestFrame.contains("I-1b")) {
                S[2] += 1;
            } else if (sourceTestFrame.contains("I-1c")) {
                S[3] += 1;
            } else if (sourceTestFrame.contains("I-1d")) {
                S[4] += 1;
            } else if (sourceTestFrame.contains("I-1e")) {
                S[5] += 1;
            }
            if (sourceTestFrame.contains("I-2a")) {
                S[7] += 1;
            } else if (sourceTestFrame.contains("I-2b")) {
                S[8] += 1;
            }
            if (sourceTestFrame.contains("I-3a")) {
                S[10] += 1;
            } else if (sourceTestFrame.contains("I-3b")) {
                S[11] += 1;
            } else if (sourceTestFrame.contains("I-3c")) {
                S[12] += 1;
            }
            if (sourceTestFrame.contains("I-4a")) {
                S[14] += 1;
            } else if (sourceTestFrame.contains("I-4b")) {
                S[15] += 1;
            }
            if (sourceTestFrame.contains("I-5a")) {
                S[17] += 1;
            } else if (sourceTestFrame.contains("I-5b")) {
                S[18] += 1;
            } else if (sourceTestFrame.contains("I-5c")) {
                S[19] += 1;
            }
            if (sourceTestFrame.contains("I-6a")) {
                S[21] += 1;
            } else if (sourceTestFrame.contains("I-6b")) {
                S[22] += 1;
            }
            if (sourceTestFrame.contains("I-7a")) {
                S[24] += 1;
            } else if (sourceTestFrame.contains("I-7b")) {
                S[25] += 1;
            }
        }
    }

    private String artGetSourceTestCase(String objectName) {
        String sourceTestCase = "";
        int maxValue = 0;
        int[] sourceChoiceArray;
        int[] maxSourceChoiceArray;
        if (objectName.equals("ACMS")) {
            maxSourceChoiceArray = new int[17];
            for (String sourceTestFrame : candidatesTestCases) {
                String sourceFollowAndMr = sourceTestFrame;
                sourceTestFrame = sourceTestFrame.split(";")[0];
                sourceChoiceArray = new int[17];
                int tempValue = 0;
                if (sourceTestFrame.contains("I-1a")) {
                    sourceChoiceArray[1] = 1;
                    tempValue += (executeTestCases.size() - S[1]);
                } else if (sourceTestFrame.contains("I-1b")) {
                    sourceChoiceArray[2] = 1;
                    tempValue += (executeTestCases.size() - S[2]);
                } else if (sourceTestFrame.contains("I-1c")) {
                    sourceChoiceArray[3] = 1;
                    tempValue += (executeTestCases.size() - S[3]);
                } else if (sourceTestFrame.contains("I-1d")) {
                    sourceChoiceArray[4] = 1;
                    tempValue += (executeTestCases.size() - S[4]);
                }
                if (sourceTestFrame.contains("I-2a")) {
                    sourceChoiceArray[6] = 1;
                    tempValue += (executeTestCases.size() - S[5]);
                } else if (sourceTestFrame.contains("I-2b")) {
                    sourceChoiceArray[7] = 1;
                    tempValue += (executeTestCases.size() - S[7]);
                }
                if (sourceTestFrame.contains("I-3a")) {
                    sourceChoiceArray[9] = 1;
                    tempValue += (executeTestCases.size() - S[9]);
                } else if (sourceTestFrame.contains("I-3b")) {
                    sourceChoiceArray[10] = 1;
                    tempValue += (executeTestCases.size() - S[10]);
                }
                if (sourceTestFrame.contains("I-4a")) {
                    sourceChoiceArray[12] = 1;
                    tempValue += (executeTestCases.size() - S[12]);
                } else if (sourceTestFrame.contains("I-4b")) {
                    sourceChoiceArray[13] = 1;
                    tempValue += (executeTestCases.size() - S[13]);
                }
                if (sourceTestFrame.contains("I-5a")) {
                    sourceChoiceArray[15] = 1;
                    tempValue += (executeTestCases.size() - S[15]);
                } else if (sourceTestFrame.contains("I-5b")) {
                    sourceChoiceArray[16] = 1;
                    tempValue += (executeTestCases.size() - S[16]);
                }
                if (tempValue > maxValue) {
                    maxValue = tempValue;
                    sourceTestCase = sourceFollowAndMr;
                    maxSourceChoiceArray = sourceChoiceArray;
                }
            }
            for (int i = 0; i < S.length; i++) {
                S[i] = S[i] + maxSourceChoiceArray[i];
            }
            executeTestCases.add(sourceTestCase);
            return sourceTestCase;
        } else if (objectName.equals("CUBS")) {
            maxSourceChoiceArray = new int[16];
            for (String sourceTestFrame : candidatesTestCases) {
                String sourceFollowAndMr = sourceTestFrame;
                sourceTestFrame = sourceTestFrame.split(";")[0];
                sourceChoiceArray = new int[16];
                int tempValue = 0;
                if (sourceTestFrame.contains("I-1a")) {
                    sourceChoiceArray[1] = 1;
                    tempValue += (executeTestCases.size() - S[1]);
                } else if (sourceTestFrame.contains("I-1b")) {
                    sourceChoiceArray[2] = 1;
                    tempValue += (executeTestCases.size() - S[2]);
                }
                if (sourceTestFrame.contains("I-2a")) {
                    sourceChoiceArray[4] = 1;
                    tempValue += (executeTestCases.size() - S[4]);
                } else if (sourceTestFrame.contains("I-2b")) {
                    sourceChoiceArray[5] = 1;
                    tempValue += (executeTestCases.size() - S[5]);
                } else if (sourceTestFrame.contains("I-2c")) {
                    sourceChoiceArray[6] = 1;
                    tempValue += (executeTestCases.size() - S[6]);
                } else if (sourceTestFrame.contains("I-2d")) {
                    sourceChoiceArray[7] = 1;
                    tempValue += (executeTestCases.size() - S[7]);
                } else if (sourceTestFrame.contains("I-2e")) {
                    sourceChoiceArray[8] = 1;
                    tempValue += (executeTestCases.size() - S[8]);
                } else if (sourceTestFrame.contains("I-2f")) {
                    sourceChoiceArray[9] = 1;
                    tempValue += (executeTestCases.size() - S[9]);
                }
                if (sourceTestFrame.contains("I-3a")) {
                    sourceChoiceArray[11] = 1;
                    tempValue += (executeTestCases.size() - S[11]);
                } else if (sourceTestFrame.contains("I-3b")) {
                    sourceChoiceArray[12] = 1;
                    tempValue += (executeTestCases.size() - S[12]);
                }
                if (sourceTestFrame.contains("I-4a")) {
                    sourceChoiceArray[14] = 1;
                    tempValue += (executeTestCases.size() - S[14]);
                } else if (sourceTestFrame.contains("I-4b")) {
                    sourceChoiceArray[15] = 1;
                    tempValue += (executeTestCases.size() - S[15]);
                }
                if (tempValue > maxValue) {
                    maxValue = tempValue;
                    sourceTestCase = sourceFollowAndMr;
                    maxSourceChoiceArray = sourceChoiceArray;
                }
            }
            for (int i = 0; i < S.length; i++) {
                S[i] = S[i] + maxSourceChoiceArray[i];
            }
            executeTestCases.add(sourceTestCase);
            return sourceTestCase;
        } else if (objectName.equals("ERS")) {
            maxSourceChoiceArray = new int[19];
            for (String sourceTestFrame : candidatesTestCases) {
                String sourceFollowAndMr = sourceTestFrame;
                sourceTestFrame = sourceTestFrame.split(";")[0];
                sourceChoiceArray = new int[19];
                int tempValue = 0;
                if (sourceTestFrame.contains("I-1a")) {
                    sourceChoiceArray[1] = 1;
                    tempValue += (executeTestCases.size() - S[1]);
                } else if (sourceTestFrame.contains("I-1b")) {
                    sourceChoiceArray[2] = 1;
                    tempValue += (executeTestCases.size() - S[2]);
                } else if (sourceTestFrame.contains("I-1c")) {
                    sourceChoiceArray[3] = 1;
                    tempValue += (executeTestCases.size() - S[3]);
                }
                if (sourceTestFrame.contains("I-2a")) {
                    sourceChoiceArray[5] = 1;
                    tempValue += (executeTestCases.size() - S[5]);
                } else if (sourceTestFrame.contains("I-2b")) {
                    sourceChoiceArray[6] = 1;
                    tempValue += (executeTestCases.size() - S[6]);
                } else if (sourceTestFrame.contains("I-2c")) {
                    sourceChoiceArray[7] = 1;
                    tempValue += (executeTestCases.size() - S[7]);
                }
                if (sourceTestFrame.contains("I-3a")) {
                    sourceChoiceArray[9] = 1;
                    tempValue += (executeTestCases.size() - S[9]);
                } else if (sourceTestFrame.contains("I-3b")) {
                    sourceChoiceArray[10] = 1;
                    tempValue += (executeTestCases.size() - S[10]);
                } else if (sourceTestFrame.contains("I-3c")) {
                    sourceChoiceArray[11] = 1;
                    tempValue += (executeTestCases.size() - S[11]);
                } else if (sourceTestFrame.contains("I-3d")) {
                    sourceChoiceArray[12] = 1;
                    tempValue += (executeTestCases.size() - S[12]);
                }
                if (sourceTestFrame.contains("I-4a")) {
                    sourceChoiceArray[14] = 1;
                    tempValue += (executeTestCases.size() - S[14]);
                } else if (sourceTestFrame.contains("I-4b")) {
                    sourceChoiceArray[15] = 1;
                    tempValue += (executeTestCases.size() - S[15]);
                }
                if (sourceTestFrame.contains("I-5a")) {
                    sourceChoiceArray[17] = 1;
                    tempValue += (executeTestCases.size() - S[17]);
                } else if (sourceTestFrame.contains("I-5b")) {
                    sourceChoiceArray[18] = 1;
                    tempValue += (executeTestCases.size() - S[18]);
                }
                if (tempValue > maxValue) {
                    maxValue = tempValue;
                    sourceTestCase = sourceFollowAndMr;
                    maxSourceChoiceArray = sourceChoiceArray;
                }
            }
            for (int i = 0; i < S.length; i++) {
                S[i] = S[i] + maxSourceChoiceArray[i];
            }
            executeTestCases.add(sourceTestCase);
            return sourceTestCase;
        } else {
            maxSourceChoiceArray = new int[26];
            for (String sourceTestFrame : candidatesTestCases) {
                String sourceFollowAndMr = sourceTestFrame;
                sourceTestFrame = sourceTestFrame.split(";")[0];
                sourceChoiceArray = new int[26];
                int tempValue = 0;
                if (sourceTestFrame.contains("I-1a")) {
                    sourceChoiceArray[1] = 1;
                    tempValue += (executeTestCases.size() - S[1]);
                } else if (sourceTestFrame.contains("I-1b")) {
                    sourceChoiceArray[2] = 1;
                    tempValue += (executeTestCases.size() - S[2]);
                } else if (sourceTestFrame.contains("I-1c")) {
                    sourceChoiceArray[3] = 1;
                    tempValue += (executeTestCases.size() - S[3]);
                } else if (sourceTestFrame.contains("I-1d")) {
                    sourceChoiceArray[4] = 1;
                    tempValue += (executeTestCases.size() - S[4]);
                } else if (sourceTestFrame.contains("I-1e")) {
                    sourceChoiceArray[5] = 1;
                    tempValue += (executeTestCases.size() - S[5]);
                }
                if (sourceTestFrame.contains("I-2a")) {
                    sourceChoiceArray[7] = 1;
                    tempValue += (executeTestCases.size() - S[7]);
                } else if (sourceTestFrame.contains("I-2b")) {
                    sourceChoiceArray[8] = 1;
                    tempValue += (executeTestCases.size() - S[8]);
                }
                if (sourceTestFrame.contains("I-3a")) {
                    sourceChoiceArray[10] = 1;
                    tempValue += (executeTestCases.size() - S[10]);
                } else if (sourceTestFrame.contains("I-3b")) {
                    sourceChoiceArray[11] = 1;
                    tempValue += (executeTestCases.size() - S[11]);
                } else if (sourceTestFrame.contains("I-3c")) {
                    sourceChoiceArray[12] = 1;
                    tempValue += (executeTestCases.size() - S[12]);
                }
                if (sourceTestFrame.contains("I-4a")) {
                    sourceChoiceArray[14] = 1;
                    tempValue += (executeTestCases.size() - S[14]);
                } else if (sourceTestFrame.contains("I-4b")) {
                    sourceChoiceArray[15] = 1;
                    tempValue += (executeTestCases.size() - S[15]);
                }
                if (sourceTestFrame.contains("I-5a")) {
                    sourceChoiceArray[17] = 1;
                    tempValue += (executeTestCases.size() - S[17]);
                } else if (sourceTestFrame.contains("I-5b")) {
                    sourceChoiceArray[18] = 1;
                    tempValue += (executeTestCases.size() - S[18]);
                } else if (sourceTestFrame.contains("I-5c")) {
                    sourceChoiceArray[19] = 1;
                    tempValue += (executeTestCases.size() - S[19]);
                }
                if (sourceTestFrame.contains("I-6a")) {
                    sourceChoiceArray[21] = 1;
                    tempValue += (executeTestCases.size() - S[21]);
                } else if (sourceTestFrame.contains("I-6b")) {
                    sourceChoiceArray[22] = 1;
                    tempValue += (executeTestCases.size() - S[22]);
                }
                if (sourceTestFrame.contains("I-7a")) {
                    sourceChoiceArray[24] = 1;
                    tempValue += (executeTestCases.size() - S[24]);
                } else if (sourceTestFrame.contains("I-7b")) {
                    sourceChoiceArray[25] = 1;
                    tempValue += (executeTestCases.size() - S[25]);
                }
                if (tempValue > maxValue) {
                    maxValue = tempValue;
                    sourceTestCase = sourceFollowAndMr;
                    maxSourceChoiceArray = sourceChoiceArray;
                }

            }
            for (int i = 0; i < S.length; i++) {
                S[i] = S[i] + maxSourceChoiceArray[i];
            }
            executeTestCases.add(sourceTestCase);
            return sourceTestCase;
        }
    }

    @Override
    public void executeTestCase(String objectName, int in) {
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
            System.out.println("MT4" + objectName + "使用ART+RSMR:" + "执行第" +
                    String.valueOf(i + 1) + "次测试：");

            executeTestCases = new ArrayList<>();
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
                counter++;
                long startSelectTestCase = System.nanoTime();
                candidatesTestCases = new ArrayList<>();
                // generate 10 candidate test cases
                for (int k = 0; k < Constant.K4ARTSUM; k++) {
                    candidatesTestCases.add(mrInfo.get(String.valueOf(new Random().nextInt(numberOfMr) + 1)));
                }
                String testframesAndMr = "";
                if (counter == 1) {
                    testframesAndMr = candidatesTestCases.get(new Random().nextInt(Constant.K4ARTSUM));
                    executeTestCases.add(testframesAndMr);
                    initializeS(objectName, testframesAndMr.split(";")[0]);
                } else {
                    testframesAndMr = artGetSourceTestCase(objectName);
                }
                long endSelectTestCase = System.nanoTime();
                if (killedMutants.size() == 0) {
                    firstSelectingTime += (endSelectTestCase - startSelectTestCase);
                }
                allSelectingTime += (endSelectTestCase - startSelectTestCase);
                String MR = "";
                if (!objectName.equals("MOS")) {
                    MR = testframesAndMr.split(";")[2];
                } else {
                    for (int z = 2; z < testframesAndMr.split(";").length; z++) {
                        MR += testframesAndMr.split(";")[z] + ";";
                    }
                    MR = MR.substring(0, MR.length() - 1);
                    // delete the medel change in the MR
                    String[] choice = MR.split(";");
                    MR = choice[0] + ";" + choice[1] + ";" + choice[2] + ";" + choice[3] + ";"
                            + choice[5] + ";" + choice[6] + ";" + choice[7];
                }
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
                            Object stc = getTestCase(objectName, testframesAndMr.split(";")[0]);
                            Object ftc = getTestCase(objectName, testframesAndMr.split(";")[1]);
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
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
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
                            Object stc = getTestCase(objectName, testframesAndMr.split(";")[0]);
                            Object ftc = getTestCase(objectName, testframesAndMr.split(";")[1]);
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
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
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
                            Object stc = getTestCase(objectName, testframesAndMr.split(";")[0]);
                            Object ftc = getTestCase(objectName, testframesAndMr.split(";")[1]);
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
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                                if (killedMutants.size() == 0) {
                                    Fmeasure = counter;
                                }
                                if (killedMutants.size() == Constant.getMutantsNumber(objectName) - 1) {
                                    Tmeasure = counter;
                                }
                                killedMutants.add(entry.getKey());
                            }

                            if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
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
                            Object stc = getTestCase(objectName, testframesAndMr.split(";")[0]);
                            Object ftc = getTestCase(objectName, testframesAndMr.split(";")[1]);
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

    /**
     * calculate average time
     *
     * @param time the array of all time
     * @return the average time
     */
    private double getAverageTime(List<Long> time) {
        long temp = 0;
        for (long t : time) {
            temp += t;
        }
        double result = temp / time.size();
        return result;
    }

    private double getAveragemeasure(List<Integer> time) {
        int temp = 0;
        for (long t : time) {
            temp += t;
        }
        double result = temp / time.size();
        return result;
    }


}
