package labprograms.testCase;


import com.alibaba.fastjson.JSON;
import labprograms.constant.Constant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.io.File.separator;


/**
 * describe:
 * this class is responsible to get test cases from json file
 *
 * @author phantom
 * @date 2019/04/19
 */
public class GetTestSuite {

    @Getter
    @Setter
    private List<Object> testSuite;

    public GetTestSuite() {
        testSuite = new ArrayList<>();
    }


    /***
     * get test suite from json file
     * @param object the name of program
     * @return the list that contains test cases
     */
    public List<Object> getTestSuite(String object) {
        Constant constant = new Constant();
        String path = System.getProperty("user.dir") + separator + "src" +
                separator + "main" + separator + "java" + separator + "labprograms"
                + separator + "testpool" + separator + "RT_" + object + "_Test_Suite.json";
        File jsonfile = new File(path);
        if (!jsonfile.exists()) {
            System.out.println("json文件不存在！");
        }
        try {
            String content = FileUtils.readFileToString(jsonfile);
            Map<String, Object> map = (Map<String, Object>) JSON.parse(content);
            for (int i = 0; i < constant.number; i++) {
                String temp_str = map.get(String.valueOf(i)).toString();
                if (object.equals("ACMS")) {
                    TestCase4ACMS tc = JSON.parseObject(temp_str, TestCase4ACMS.class);
                    testSuite.add(tc);
                } else if (object.equals("CUBS")) {
                    TestCase4CUBS tc = JSON.parseObject(temp_str, TestCase4CUBS.class);
                    testSuite.add(tc);
                } else if (object.equals("ERS")) {
                    TestCase4ERS tc = JSON.parseObject(temp_str, TestCase4ERS.class);
                    testSuite.add(tc);
                } else {
                    TestCase4MOS tc = JSON.parseObject(temp_str, TestCase4MOS.class);
                    testSuite.add(tc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testSuite;
    }
}
