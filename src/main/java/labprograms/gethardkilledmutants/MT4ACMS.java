package labprograms.gethardkilledmutants;

import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import labprograms.testCase.TestCase4ACMS;

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
 * @date 2019/04/30
 */
public class MT4ACMS {

    public static void main(String[] args) throws IllegalAccessException, IOException, InvocationTargetException {
        MT4ACMS acms = new MT4ACMS();
        acms.getKilledInfo();
    }

    public void getKilledInfo() throws IOException, InvocationTargetException, IllegalAccessException {
        MutantsSet mutantsSet = new MutantsSet("ACMS");
        Map<String, Mutant> mutantMap = mutantsSet.getMutants();
        String methodName = new Methods4Testing("ACMS").getMethodName();

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
                mutantMethod = mutantClazz.getMethod(methodName, int.class, int.class,
                        boolean.class, double.class, double.class);
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

            String path = Constant.mrPath4ACMS;

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
                TestCase4ACMS sourceTestCase = generateTestCase(sourceTestFrame);
                TestCase4ACMS followUpTestCase = generateTestCase(followUpTestFrame);

                sourceResult = (double) mutantMethod.invoke(mutantInstance,
                        sourceTestCase.getAirClass(), sourceTestCase.getArea(),
                        sourceTestCase.isStudent(), sourceTestCase.getLuggage(),
                        sourceTestCase.getEconomicfee());

                followUpResult = (double) mutantMethod.invoke(mutantInstance,
                        followUpTestCase.getAirClass(), followUpTestCase.getArea(),
                        followUpTestCase.isStudent(), followUpTestCase.getLuggage(),
                        followUpTestCase.getEconomicfee());

                if (MR.equals("The output will not change") && sourceResult != followUpResult) {
                    counter++;
                    indexs.add(lineNumber);
                }

                if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
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
    public TestCase4ACMS generateTestCase(String testFrame) {
        int airClass = 0;
        int area = 0;
        boolean isStudent = true;
        double luggage = 0;
        double economicfee = 0;
        //delate the braces
        testFrame = testFrame.replace("{", "").replace("}", "");
        String[] choices = testFrame.split(",");
        // instantiate the choices
        switch (choices[0]) {
            case "I-1a":
                airClass = 0;
                break;
            case "I-1b":
                airClass = 1;
                break;
            case "I-1c":
                airClass = 2;
                break;
            case "I-1d":
                airClass = 3;
                break;
        }

        switch (choices[1]) {
            case "I-2a":
                area = 0;
                break;
            case "I-2b":
                area = 1;
                break;
        }

        switch (choices[2]) {
            case "I-3a":
                isStudent = false;
                break;
            case "I-3b":
                isStudent = true;
                airClass = 2;
                break;
        }
        int benchmark = 0;
        if (airClass == 0)
            benchmark = 40;
        else if (airClass == 1)
            benchmark = 30;
        else if (airClass == 2 && isStudent == true)
            benchmark = 30;
        else if (airClass == 2 && isStudent == false)
            benchmark = 20;
        else
            benchmark = 0;

        switch (choices[3]) {
            case "I-4a":
                int temp = new Random().nextInt(80);
                while (temp > benchmark) {
                    temp = new Random().nextInt(80);
                }
                luggage = temp;
                break;
            case "I-4b":
                int tempb = new Random().nextInt(80);
                while (tempb <= benchmark) {
                    tempb = new Random().nextInt(80);
                }
                luggage = tempb;
                break;
        }

        switch (choices[4]) {
            case "I-5a":
                economicfee = 0;
                break;
            case "I-5b":
                economicfee = new Random().nextDouble() * 3000;
                break;
        }
        TestCase4ACMS tc = new TestCase4ACMS(airClass, area, isStudent, luggage, economicfee);
        return tc;
    }

    private void writeInfo(String mutantName, String counter, List<Integer> index) {
        String path = Constant.killedmutantinfo + separator + "ACMS";
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
