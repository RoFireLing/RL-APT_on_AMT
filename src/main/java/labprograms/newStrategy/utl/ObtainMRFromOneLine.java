package labprograms.newStrategy.utl;

/**
 * @Description: 从一行内容（包括原始测试用例、衍生测试用例和蜕变关系）中抽取蜕变关系
 * @auther phantom
 * @create 2019-09-18 下午3:38
 */
public class ObtainMRFromOneLine {

    /**
     * 抽取蜕变关系的方法
     *
     * @param objectName     　待测程序的名字
     * @param testFrameAndMR 　具体的一行内容
     * @return　ＭＲ
     */
    public static String getMR(String objectName, String testFrameAndMR) {
        String MR = "";
        if (!objectName.equals("MOS")) {
            MR = testFrameAndMR.split(";")[2];
        } else {
            for (int z = 2; z < testFrameAndMR.split(";").length; z++) {
                MR += testFrameAndMR.split(";")[z] + ";";
            }
            MR = MR.substring(0, MR.length() - 1);
            String[] choice = MR.split(";");
            MR = choice[0] + ";" + choice[1] + ";" + choice[2] + ";" + choice[3] + ";"
                    + choice[5] + ";" + choice[6] + ";" + choice[7];
        }
        return MR;
    }
}
