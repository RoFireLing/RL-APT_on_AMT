package labprograms.util;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static java.io.File.separator;

/**
 * describe:
 * writing test cases to json file
 *
 * @author phantom
 * @date 2019/04/19
 */
public class WriteTestCases {

    public void writeTestSuiteToJson(String object, Map<String, Object> testSuite) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new JSONObject(testSuite).toJSONString());
        String path = System.getProperty("user.dir") + separator + "src" +
                separator + "main" + separator + "java" + separator + "labprograms"
                + separator + "testpool" + separator + "RT_" + object + "_Test_Suite.json";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.write(stringBuffer.toString());
            printWriter.close();
            System.out.println("write test suite successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
