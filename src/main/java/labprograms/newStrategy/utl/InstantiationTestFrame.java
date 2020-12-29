package labprograms.newStrategy.utl;

import labprograms.gethardkilledmutants.MT4ACMS;
import labprograms.gethardkilledmutants.MT4CUBS;
import labprograms.gethardkilledmutants.MT4ERS;
import labprograms.gethardkilledmutants.MT4MOS;
import labprograms.testCase.TestCase4ACMS;
import labprograms.testCase.TestCase4CUBS;
import labprograms.testCase.TestCase4ERS;
import labprograms.testCase.TestCase4MOS;

/**
 * @Description:　将测试帧实例化位测试用例
 * @auther phantom
 * @create 2019-09-18 下午3:16
 */
public class InstantiationTestFrame {

    /**
     * 实例化测试帧的方法
     *
     * @param objectName 　待测对象的名字
     * @param testFrame  　具体的测试帧
     * @return　实例化后的测试用例
     */
    public static Object instantiation(String objectName, String testFrame) {
        if (objectName.equals("ACMS")) {
            TestCase4ACMS tc = new MT4ACMS().generateTestCase(testFrame);
            return tc;
        } else if (objectName.equals("CUBS")) {
            TestCase4CUBS tc = new MT4CUBS().generateTestCase(testFrame);
            return tc;
        } else if (objectName.equals("ERS")) {
            TestCase4ERS tc = new MT4ERS().generateTestCase(testFrame);
            return tc;
        } else {
            TestCase4MOS tc = new MT4MOS().generateTestCase(testFrame);
            return tc;
        }
    }
}
