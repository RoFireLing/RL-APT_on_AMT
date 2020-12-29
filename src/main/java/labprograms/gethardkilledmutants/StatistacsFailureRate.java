package labprograms.gethardkilledmutants;

import com.alibaba.fastjson.JSON;
import labprograms.constant.Constant;

import java.io.*;
import java.util.*;

import static java.io.File.separator;

/**
 * describe:该类主要是从所有的故障中选择“难以杀死”的变异体，分两步进行，
 * 　　　　　（１）筛选出符合阈值的变异体
 * 　　　　　（２）从能被同一个测试用例杀死的变异体中随机选取一个
 *
 * @author phantom
 * @date 2019/05/05
 */
public class StatistacsFailureRate {

    public static void main(String[] args) {
        StatistacsFailureRate statistacsFailureRate = new StatistacsFailureRate();
        statistacsFailureRate.getFailureRate("MOS", 0.1);
    }

    public void getFailureRate(String objectName, double failurRate) {
        String path = Constant.gethardkilledmutantsPath + separator + objectName;
        File file = new File(path);
        List<String> names = new ArrayList<>();
        BufferedReader bufferedReader = null;
        int min = 10000;
        String minName = "";

        //存储符合阈值的变异体名字
        Set<String> namesSet = new HashSet<>();
        //储存符合阈值的变异体以及能够杀死他们的测试用例编号
        Map<String, String[]> namesMap = new HashMap<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String temp = "";
            double threshod = 0;

            if (objectName.equals("ACMS"))
                threshod = failurRate * Constant.numberofMr4ACMS * 2;
            else if (objectName.equals("CUBS"))
                threshod = failurRate * Constant.numberofMr4CUBS * 2;
            else if (objectName.equals("ERS"))
                threshod = failurRate * Constant.numberofMr4ERS * 2;
            else
                threshod = failurRate * Constant.numberofMr4MOS * 2;

            while ((temp = bufferedReader.readLine()) != null) {
                String[] content = temp.split(":");
                if (Integer.parseInt(content[1]) * 2 <= threshod && Integer.parseInt(content[1]) != 0) {
                    names.add(content[0]);
                    namesSet.add(content[0]);
                    String indexs = content[2].replace("[", "").
                            replace("]", "");
                    String[] indexsArray = indexs.split(", ");
                    namesMap.put(content[0], indexsArray);
                }

                if (Integer.parseInt(content[1]) <= min && Integer.parseInt(content[1]) != 0) {
                    min = Integer.parseInt(content[1]);
                    minName = content[0];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mutantsFiltrateStep1(objectName, namesSet);
        mutantFiltrateStep2(objectName, namesSet, namesMap);
    }

    /**
     * 实行第一步过滤
     *
     * @param objectName
     * @param set
     */
    private void mutantsFiltrateStep1(String objectName, Set<String> set) {
        String path = Constant.gethardkilledmutantsPath + separator + objectName + "_filtrate_1";
        String str = "";
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            str += iterator.next() + ", ";
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.write(str);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 过滤步骤２
     *
     * @param objectName
     * @param set
     */
    private void mutantFiltrateStep2(String objectName, Set<String> set, Map<String, String[]> namesMap) {
        Set<String> elementsSet = new HashSet<>();
        Iterator<String> iterator = set.iterator();
        Map<String, String[]> finalNames = new HashMap<>();

        while (iterator.hasNext()) {
            String mutantName = iterator.next();
            String[] testcases = namesMap.get(mutantName);
            boolean flag = false;
            for (int i = 0; i < testcases.length; i++) {
                if (!elementsSet.add(testcases[i])) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                finalNames.put(mutantName, testcases);
            }
        }

        String path = Constant.gethardkilledmutantsPath + separator + objectName + "_filtrate_2";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            String content = JSON.toJSONString(finalNames);
            printWriter.write(content);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
