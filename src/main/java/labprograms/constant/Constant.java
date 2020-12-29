package labprograms.constant;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/04/19
 */
public class Constant {

    /**
     * this is the number of generating test cases
     */
    public static final int number = 10000;

    public static final String acmssource = "labprograms.ACMS.sourceCode.AirlinesBaggageBillingService";

    public static final String cubssource = "labprograms.CUBS.sourceCode.BillCalculation";

    public static final String testingresultdir = System.getProperty("user.dir") +
            separator + "results";
    public static final String partitionPath = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "partition";
    /**
     * the number of repeating times for testing
     */
    public static final int repeatNumber = 30;
    /**
     * the path of the file that includes the test frames and corresponding MRs for ACMS
     */
    public static final String mrPath4ACMS = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr" + separator + "ACMS";
    /**
     * the path of the dir that includes log of each object
     */
    public static final String logPath = System.getProperty("user.dir") + separator + "log";
    public static final String resultPath = System.getProperty("user.dir") + separator + "result";
    public static final String killedmutantinfo = System.getProperty("user.dir") +
            separator + "src" + separator + "main" + separator + "java"
            + separator + "labprograms" + separator + "gethardkilledmutants";
    /**
     * the path of the file that includes the test frames and corresponding MRs for CUBS
     */
    public static final String mrPath4CUBS = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr" + separator + "CUBS";
    public static final String mrDirPath = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr";
    /**
     * the path of the file that includes the test frames and corresponding MRs for ERS
     */
    public static final String mrPath4ERS = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr" + separator + "ERS";
    /**
     * the path of the file that includes the test frames and corresponding MRs for MT4MOS
     */
    public static final String mrPath4MOS = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr" + separator + "MOS";
    public static final String gethardkilledmutantsPath = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "gethardkilledmutants";
    public static final int numberofMr4ACMS = 735;
    public static final int numberofMr4CUBS = 142;
    public static final int numberofMr4ERS = 1130;
    public static final int numberofMr4MOS = 3512;
    public static final int K4ARTSUM = 10;
    public String mrPath = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr";
    public String staticMRPath = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr" + separator + "staticMR";

    public static int getPartitionNumber(String objectName) {
        if (objectName.equals("ACMS"))
            return 8;
        else if (objectName.equals("CUBS"))
            return 3;
        else if (objectName.equals("ERS"))
            return 12;
        else
            return 10;
    }


    /**
     * 返回不同测试对象的变异体数目
     *
     * @param objectName 测试对象的名字：ACMS CUBS ERS MOS
     * @return 变异体的数目
     */
    public static int getMutantsNumber(String objectName) {
        if (objectName.equals("ACMS"))
            return 2;
        else if (objectName.equals("CUBS"))
            return 1;
        else if (objectName.equals("ERS"))
            return 1;
        else
            return 1;
    }


    public static int getNUmberOfMR(String objectName) {
        if (objectName.equals("ACMS"))
            return 735;
        else if (objectName.equals("CUBS"))
            return 142;
        else if (objectName.equals("ERS"))
            return 1130;
        else
            return 3512;
    }

    public static int getNumber() {
        return number;
    }

    public static String getAcmssource() {
        return acmssource;
    }

    public static String getCubssource() {
        return cubssource;
    }

    public static String getTestingresultdir() {
        return testingresultdir;
    }

    public static String getPartitionPath() {
        return partitionPath;
    }

    public static int getRepeatNumber() {
        return repeatNumber;
    }

    public static String getMrPath4ACMS() {
        return mrPath4ACMS;
    }

    public static String getLogPath() {
        return logPath;
    }

    public static String getResultPath() {
        return resultPath;
    }

    public static String getKilledmutantinfo() {
        return killedmutantinfo;
    }

    public static String getMrPath4CUBS() {
        return mrPath4CUBS;
    }

    public static String getMrDirPath() {
        return mrDirPath;
    }

    public static String getMrPath4ERS() {
        return mrPath4ERS;
    }

    public static String getMrPath4MOS() {
        return mrPath4MOS;
    }

    public static String getGethardkilledmutantsPath() {
        return gethardkilledmutantsPath;
    }

    public static int getNumberofMr4ACMS() {
        return numberofMr4ACMS;
    }

    public static int getNumberofMr4CUBS() {
        return numberofMr4CUBS;
    }

    public static int getNumberofMr4ERS() {
        return numberofMr4ERS;
    }

    public static int getNumberofMr4MOS() {
        return numberofMr4MOS;
    }

    public static int getK4ARTSUM() {
        return K4ARTSUM;
    }

    public String getMrPath() {
        return mrPath;
    }

    public String getStaticMRPath() {
        return staticMRPath;
    }
}
