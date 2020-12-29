package labprograms.gethardkilledmutants;

import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import labprograms.testCase.TestCase4ERS;

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
public class MT4ERS {

    public static void main(String[] args) throws IllegalAccessException, IOException, InvocationTargetException {
        MT4ERS ers = new MT4ERS();
        ers.getKilledInfo();
    }

    public void getKilledInfo() throws IOException, InvocationTargetException, IllegalAccessException {
        MutantsSet mutantsSet = new MutantsSet("ERS");
        Map<String, Mutant> mutantMap = mutantsSet.getMutants();
        String methodName = new Methods4Testing("ERS").getMethodName();
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
                mutantMethod = mutantClazz.getMethod(methodName, String.class, double.class,
                        double.class, double.class, double.class);
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

            String path = Constant.mrPath4ERS;

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
                TestCase4ERS sourceTestCase = generateTestCase(sourceTestFrame);
                TestCase4ERS followUpTestCase = generateTestCase(followUpTestFrame);
                sourceResult = (double) mutantMethod.invoke(mutantInstance, sourceTestCase.getStafflevel(),
                        sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
                        sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());

                followUpResult = (double) mutantMethod.invoke(mutantInstance, followUpTestCase.getStafflevel(),
                        sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
                        sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());

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

    public TestCase4ERS generateTestCase(String testFrame) {
        String stafflevel = "";
        double actualmonthlymileage = 0;
        double monthlysalesamount = 0;
        double airfareamount = 0;
        double otherexpensesamount = 0;

        //delate the braces
        testFrame = testFrame.replace("{", "").replace("}", "");
        String[] choices = testFrame.split(",");

        switch (choices[0]) {
            case "I-1a":
                stafflevel = "seniormanager";
                break;
            case "I-1b":
                stafflevel = "manager";
                break;
            case "I-1c":
                stafflevel = "supervisor";
                break;
            case "*":
                stafflevel = new String[]{"seniormanager", "manager", "supervisor"}[new Random().nextInt(3)];
                break;
        }

        switch (choices[1]) {
            case "I-2a":
                actualmonthlymileage = new Random().nextDouble() * 3000;
                break;
            case "I-2b":
                actualmonthlymileage = new Random().nextDouble() * 1000 + 3000;
                break;
            case "I-2c":
                actualmonthlymileage = new Random().nextDouble() * 1000 + 4000;
                break;
            case "*":
                actualmonthlymileage = new Random().nextDouble() * 6000;
                break;
        }


        switch (choices[2]) {
            case "I-3a":
                monthlysalesamount = new Random().nextDouble() * 50000;
                break;
            case "I-3b":
                monthlysalesamount = new Random().nextDouble() * 30000 + 50000;
                break;
            case "I-3c":
                monthlysalesamount = new Random().nextDouble() * 20000 + 80000;
                break;
            case "I-3d":
                monthlysalesamount = new Random().nextDouble() * 10000 + 100000;
                break;
            case "*":
                monthlysalesamount = new Random().nextDouble() * 130000;
                break;
        }

        switch (choices[3]) {
            case "I-4a":
                airfareamount = 0;
                break;
            case "I-4b":
                airfareamount = new Random().nextDouble() * 10000;
                break;
            case "*":
                airfareamount = new Random().nextDouble() * 10000;
                break;
        }

        switch (choices[4]) {
            case "I-5a":
                otherexpensesamount = 0;
                break;
            case "I-5b":
                otherexpensesamount = new Random().nextDouble() * 10000;
                break;
            case "*":
                otherexpensesamount = new Random().nextDouble() * 10000;
                break;
        }
        TestCase4ERS tc = new TestCase4ERS(stafflevel, actualmonthlymileage, monthlysalesamount,
                airfareamount, otherexpensesamount);
        return tc;
    }

    private void writeInfo(String mutantName, String counter, List<Integer> index) {
        String path = Constant.killedmutantinfo + separator + "ERS";
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
