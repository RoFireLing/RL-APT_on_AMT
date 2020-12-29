package labprograms.gethardkilledmutants;

import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import labprograms.testCase.TestCase4ACMS;
import labprograms.testCase.TestSuite4ACMS;
import labprograms.util.WriteTestingResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * describe:
 * randomly generate test cases to execute mutants, which can get the mutants
 * that at least need 20 test case to be killed
 *
 * @author phantom
 * @date 2019/04/17
 */
public class RT4ACMS {

    public static void main(String[] args) {
        RT4ACMS rt = new RT4ACMS();
        rt.randomTesting();
    }

    public void randomTesting() {
        System.out.println("开始测试ACMS");
        Constant constant = new Constant();
        MutantsSet mutantsSet = new MutantsSet("ACMS");
        Map<String, Mutant> mutants = mutantsSet.getMutants();

        List<TestCase4ACMS> testCases = new TestSuite4ACMS().getTestCases();

        WriteTestingResult writeTestingResult = new WriteTestingResult();

        Methods4Testing methods4Testing = new Methods4Testing("ACMS");
        String methodName = methods4Testing.getMethodName();

        Object sourceInstance = null;
        Method sourceMethod = null;

        try {
            Class sourceClazz = Class.forName(constant.acmssource);
            Constructor sourceConstructor = sourceClazz.getConstructor(null);
            sourceInstance = sourceConstructor.newInstance();
            sourceMethod = sourceClazz.getMethod(methodName, int.class, int.class,
                    boolean.class, double.class, double.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Mutant> entry : mutants.entrySet()) {
            String mutantName = entry.getKey();

            System.out.println("开始测试：" + mutantName);

            Mutant mutant = entry.getValue();
            Object mutantInstance = null;
            Method mutantMethod = null;

            try {
                Class mutantClazz = Class.forName(mutant.getFullName());
                Constructor mutantConstructor = mutantClazz.getConstructor(null);
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
            int count = 0;
            boolean flag = true;
            // the index of test cases that killed concurrent mutant
            String killedindex = "";

            for (int i = 0; i < testCases.size(); i++) {
                TestCase4ACMS tc = testCases.get(i);
                Object sourceResult = null;
                Object mutantResult = null;
                try {
                    sourceResult = sourceMethod.invoke(sourceInstance, tc.getAirClass(), tc.getArea(),
                            tc.isStudent(), tc.getLuggage(), tc.getEconomicfee());
                    mutantResult = mutantMethod.invoke(mutantInstance, tc.getAirClass(), tc.getArea(),
                            tc.isStudent(), tc.getLuggage(), tc.getEconomicfee());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (sourceResult.equals(mutantResult)) {
                    continue;
                } else {
                    killedindex += String.valueOf(i + 1) + ";";
                    count++;
                }
            }
            writeTestingResult.write("ACMS", mutantName,
                    killedindex, String.valueOf(count));
        }
    }
}
