package labprograms.log;

import labprograms.constant.Constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.io.File.separator;

/**
 * describe:
 * record the information of executing test cases
 *
 * @author phantom
 * @date 2019/04/30
 */
public class RecordLog {

    /**
     * record information of executing test case
     *
     * @param objectName       name of SUT
     * @param seed             seed
     * @param sourceResult     the result of source test case
     * @param followResult     the result of follow-up test case
     * @param MR               MR
     * @param sourceTestCase   the source test case
     * @param followUpTestCase the follow-up test case
     */
    public static void recordLog(String objectName, int seed, int index,
                                 double sourceResult, double followResult,
                                 String MR, Object sourceTestCase,
                                 Object followUpTestCase, String mutantName) {


//        if (objectName.equals("ACMS")){
//            TestCase4ACMS sourcetc = (TestCase4ACMS) sourceTestCase;
//            TestCase4ACMS followtc = (TestCase4ACMS) followUpTestCase;
//        }else if (objectName.equals("CUBS")){
//            TestCase4CUBS soucetc = (TestCase4CUBS) sourceTestCase;
//            TestCase4CUBS followtc = (TestCase4CUBS) followUpTestCase;
//        }else if (objectName.equals("ERS")){
//            TestCase4ERS sourcetc = (TestCase4ERS) sourceTestCase;
//            TestCase4ERS followtc = (TestCase4ERS) followUpTestCase;
//        }else {
//            TestCase4MOS sourcetc = (TestCase4MOS) sourceTestCase;
//            TestCase4MOS followtc = (TestCase4MOS) followUpTestCase;
//        }

        String content = "第" + String.valueOf(seed) + "次实验；的第" + String.valueOf(index) + "个测试用例；"
                + mutantName + "被杀死；" +
                "原始测试用例为：" + sourceTestCase.toString() + "；衍生测试用例为：" +
                followUpTestCase.toString() + "；它们的结果分别为：" + String.valueOf(sourceResult) +
                "和" + String.valueOf(followResult) + "；违反了MR:" + MR;

        String path = Constant.logPath + separator + objectName + separator + "log";
        File file = new File(path);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(file, true));
            printWriter.write(content + "\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
