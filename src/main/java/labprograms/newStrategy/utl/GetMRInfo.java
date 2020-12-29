package labprograms.newStrategy.utl;

import labprograms.constant.Constant;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 获取蜕变关系
 * @auther phantom
 * @create 2019-09-18 下午3:29
 */
public class GetMRInfo {


    /**
     * 获取ＭＲ的方法
     *
     * @param objectName 　待测程序的名字
     * @return　Ｍａｐ，键是行号，值是具体的内容
     */
    public static Map<String, String> getMRinfo(String objectName) {
        String path = "";
        if (objectName.equals("ACMS")) {
            path = Constant.mrPath4ACMS;
        } else if (objectName.equals("CUBS")) {
            path = Constant.mrPath4CUBS;
        } else if (objectName.equals("ERS")) {
            path = Constant.mrPath4ERS;
        } else {
            path = Constant.mrPath4MOS;
        }

        BufferedReader bufferedReader = null;
        Map<String, String> mrInfo = new HashMap<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String tempStr = "";
            int tempInteger = 1;
            while ((tempStr = bufferedReader.readLine()) != null) {
                mrInfo.put(String.valueOf(tempInteger), tempStr);
                tempInteger++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mrInfo;
    }


}
