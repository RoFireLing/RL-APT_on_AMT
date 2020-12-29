package labprograms.gethardkilledmutants;

import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import labprograms.testCase.TestCase4CUBS;
import labprograms.testCase.TestSuite4CUBS;
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
public class RT4CUBS {
    public static void main(String[] args) {
        RT4CUBS rt = new RT4CUBS();
        rt.randomTesting();
    }

    public void randomTesting() {
        System.out.println("开始测试CUBS");
        Constant constant = new Constant();
        MutantsSet mutantsSet = new MutantsSet("CUBS");
        Map<String, Mutant> mutants = mutantsSet.getMutants();

        List<TestCase4CUBS> testCases = new TestSuite4CUBS().getTestCases();

        WriteTestingResult writeTestingResult = new WriteTestingResult();

        Methods4Testing methods4Testing = new Methods4Testing("CUBS");
        String methodName = methods4Testing.getMethodName();

        Object sourceInstance = null;
        Method sourceMethod = null;

        try {
            Class sourceClazz = Class.forName(constant.cubssource);
            Constructor sourceConstructor = sourceClazz.getConstructor(null);
            sourceInstance = sourceConstructor.newInstance();
            sourceMethod = sourceClazz.getMethod(methodName, String.class, int.class,
                    int.class, int.class);
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
            int count = 0;
            boolean flag = true;
            // the index of test cases that killed concurrent mutant
            String killedindex = "";

            for (int i = 0; i < testCases.size(); i++) {
                TestCase4CUBS tc = testCases.get(i);
                Object sourceResult = null;
                Object mutantResult = null;
                try {
                    sourceResult = sourceMethod.invoke(sourceInstance, tc.getPlanType(),
                            tc.getPlanFee(), tc.getTalkTime(), tc.getFlow());
                    mutantResult = mutantMethod.invoke(mutantInstance, tc.getPlanType(),
                            tc.getPlanFee(), tc.getTalkTime(), tc.getFlow());
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
            writeTestingResult.write("CUBS", mutantName,
                    killedindex, String.valueOf(count));
        }
    }

}
