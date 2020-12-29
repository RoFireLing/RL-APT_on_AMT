package labprograms.gethardkilledmutants;

import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import labprograms.testCase.TestCase4CUBS;

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
 *
 * @author phantom
 * @date 2019/05/04
 */
public class MT4CUBS {

    public static void main(String[] args) throws IllegalAccessException, IOException, InvocationTargetException {
        MT4CUBS cubs = new MT4CUBS();
        cubs.getKilledInfo();
    }

    public void getKilledInfo() throws IOException, InvocationTargetException, IllegalAccessException {
        MutantsSet mutantsSet = new MutantsSet("CUBS");
        Map<String, Mutant> mutantMap = mutantsSet.getMutants();
        String methodName = new Methods4Testing("CUBS").getMethodName();

        for (Map.Entry<String, Mutant> entry : mutantMap.entrySet()) {
            //逐个遍历每一个变异体
            List<Integer> indexs = new ArrayList<>();
            int counter = 0;

            Mutant mutant = entry.getValue();
            Object mutantInstance = null;
            Method mutantMethod = null;
            double sourceResult = 0;
            double followUpResult = 0;
            Class mutantClazz = null;
            try {
                mutantClazz = Class.forName(mutant.getFullName());
                Constructor mutantConstructor = mutantClazz.getConstructor();
                mutantInstance = mutantConstructor.newInstance();
                mutantMethod = mutantClazz.getMethod(methodName, String.class, int.class,
                        int.class, int.class);
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
            String path = Constant.mrPath4CUBS;

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
                String MR = testframesAndMr.split(";")[2];
                TestCase4CUBS sourceTestCase = generateTestCase(sourceTestFrame);
                TestCase4CUBS followUpTestCase = generateTestCase(followUpTestFrame);

                sourceResult = (double) mutantMethod.invoke(mutantInstance, sourceTestCase.getPlanType(),
                        sourceTestCase.getPlanFee(), sourceTestCase.getTalkTime(), sourceTestCase.getFlow());

                followUpResult = (double) mutantMethod.invoke(mutantInstance, followUpTestCase.getPlanType(),
                        followUpTestCase.getPlanFee(), followUpTestCase.getTalkTime(), followUpTestCase.getFlow());

                if (MR.equals("The output will not change") && sourceResult != followUpResult) {
                    counter++;
                    indexs.add(lineNumber);
                }

                if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                    counter++;
                    indexs.add(lineNumber);
                }

                if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
                    counter++;
                    indexs.add(lineNumber);
                }
            }// We went through each mutant
            writeInfo(entry.getKey(), String.valueOf(counter), indexs);
        }
    }

    public TestCase4CUBS generateTestCase(String testFrame) {
        String planType = null;
        int planFee = 0;
        int talkTime = 0;
        int flow = 0;

        //delate the braces
        testFrame = testFrame.replace("{", "").replace("}", "");
        String[] choices = testFrame.split(",");

        if (choices[0].equals("I-1a")) {
            planType = "A";
            switch (choices[1]) {
                case "I-2a":
                    planFee = 46;
                    break;
                case "I-2b":
                    planFee = 96;
                    break;
                case "I-2e":
                    planFee = 286;
                    break;
                case "I-2f":
                    planFee = 886;
                    break;
            }
        }

        if (choices[0].equals("I-1b")) {
            planType = "B";
            switch (choices[1]) {
                case "I-2a":
                    planFee = 46;
                    break;
                case "I-2b":
                    planFee = 96;
                    break;
                case "I-2c":
                    planFee = 126;
                    break;
                case "I-2d":
                    planFee = 186;
                    break;
            }
        }
        int talkTimeBench = 0;
        int flowBench = 0;
        if (planType.equals("A") && planFee == 46) {
            talkTimeBench = 50;
            flowBench = 150;
        } else if (planType.equals("A") && planFee == 96) {
            talkTimeBench = 96;
            flowBench = 240;
        } else if (planType.equals("A") && planFee == 286) {
            talkTimeBench = 286;
            flowBench = 900;
        } else if (planType.equals("A") && planFee == 886) {
            talkTimeBench = 3000;
            flowBench = 3000;
        } else if (planType.equals("B") && planFee == 46) {
            talkTimeBench = 120;
            flowBench = 40;
        } else if (planType.equals("B") && planFee == 96) {
            talkTimeBench = 450;
            flowBench = 80;
        } else if (planType.equals("B") && planFee == 126) {
            talkTimeBench = 680;
            flowBench = 100;
        } else if (planType.equals("B") && planFee == 186) {
            talkTimeBench = 1180;
            flowBench = 150;
        }

        switch (choices[2]) {
            case "I-3a":
                talkTime = talkTimeBench - new Random().nextInt(20);
                break;
            case "I-3b":
                talkTime = talkTimeBench + new Random().nextInt(20);
                break;
        }

        switch (choices[3]) {
            case "I-4a":
                flow = flowBench - new Random().nextInt(20);
                break;
            case "I-4b":
                flow = flowBench + new Random().nextInt(20);
                break;
        }
        TestCase4CUBS tc = new TestCase4CUBS(planType, planFee, talkTime, flow);
        return tc;
    }

    private void writeInfo(String mutantName, String counter, List<Integer> index) {
        String path = Constant.killedmutantinfo + separator + "CUBS";
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
