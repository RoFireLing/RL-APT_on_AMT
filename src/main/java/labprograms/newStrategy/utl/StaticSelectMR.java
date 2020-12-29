package labprograms.newStrategy.utl;

import labprograms.constant.Constant;

import java.io.*;
import java.util.*;

import static java.io.File.separator;

/**
 * @Description: 静态地选择ＭＲ，遵循以下原则：
 * 　　　　　　　　１，根据原始测试用例与衍生测试用例的不同将蜕变关系进行排序
 * 　　　　　　　　２，原始测试用例与衍生测试用例差异相同的蜕变关系组成一个集合，并从该集合中随机选取一个
 * 　　　　　　　　３，如果蜕变关系的数目大于６，则自顶向下选择６个蜕变关系；反之，所有的蜕变关系作为使用的蜕变关系
 * @auther phantom
 * @create 2019-09-23 下午7:50
 */
public class StaticSelectMR {

    public static void main(String[] args) {
        StaticSelectMR staticSelectMR = new StaticSelectMR();
        staticSelectMR.getStaticMR("MOS");
    }

    public void getStaticMR(String objectName) {
        String path = Constant.mrDirPath + separator + objectName;
        //获得ＭＲ
        List<String> MRs = readFile(path);

        //对ＭＲ进行排序
        Map<String, List<String>> sortedMRs = sortMRs(MRs);

        //每个集合只取一个ＭＲ
        Map<String, String> sortedOneMRs = selectedMRs(sortedMRs);

        //获取６个ＭＲ
        List<String> sixMRs = getSixMRs(sortedOneMRs);

        //输出结果
        printResult(sixMRs);

        //将结果写入ｓｔａｔｉｃＭＲ目录下
        String targetDir = Constant.getMrDirPath() + separator + "staticMR" + separator + objectName;
        writeResult(sixMRs, targetDir);
    }

    private void writeResult(List<String> MRs, String path) {
        for (int i = 0; i < MRs.size(); i++) {
            String dir = path + separator + MRs.get(i);
            File file = new File(dir);
            file.mkdir();
        }
    }

    /**
     * 输出最终的结果
     *
     * @param result
     */
    private void printResult(List<String> result) {
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + "; \n");
        }
    }

    /**
     * 经过前两步的处理之后，得到最终的几个蜕变关系
     *
     * @param MRs
     * @return
     */
    private List<String> getSixMRs(Map<String, String> MRs) {
        List<Integer> deffiences = new ArrayList<>();
        for (Map.Entry<String, String> entry : MRs.entrySet()) {
            deffiences.add(Integer.parseInt(entry.getKey()));
        }
        Integer[] temp = new Integer[deffiences.size()];
        for (int i = 0; i < deffiences.size(); i++) {
            temp[i] = deffiences.get(i);
        }

        //从大到小排序
        Comparator<Integer> c = new Mycompare();
        Arrays.sort(temp, c);

        //截取６个目标
        List<String> result = new ArrayList<>();
        Integer[] finalIndexs = getSixMRs(temp);
        for (int i = 0; i < finalIndexs.length; i++) {
            result.add(MRs.get(String.valueOf(finalIndexs[i])));
        }
        return result;
    }

    /**
     * 截取６个ＭＲｓ
     *
     * @param MRs
     * @return
     */
    private Integer[] getSixMRs(Integer[] MRs) {
        if (MRs.length <= 6) {
            return MRs;
        } else {
            Integer[] tempArray = new Integer[6];
            for (int i = 0; i < 6; i++) {
                tempArray[i] = MRs[i];
            }
            return tempArray;
        }
    }

    /**
     * 将ｓｏｒｔｅｄ　MRs中距离一样的随机选取一个
     *
     * @param MRs
     * @return
     */
    private Map<String, String> selectedMRs(Map<String, List<String>> MRs) {
        Map<String, String> selectedMRs = new HashMap<>();
        //遍历ＭＲｓ
        for (Map.Entry<String, List<String>> entry : MRs.entrySet()) {
            List<String> tempList = entry.getValue();
            String str = tempList.get(new Random().nextInt(tempList.size()));
            selectedMRs.put(entry.getKey(), str);
        }
        return selectedMRs;
    }

    /***
     * 根据路径读取对应的文件，获取ＭＲ的信息
     * @param path　待测对象的ＭＲ路径
     * @return　ＭＲｓ
     */
    private List<String> readFile(String path) {
        List<String> testframes = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件不存在！");
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                testframes.add(str);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testframes;
    }

    /**
     * 对测试对象的蜕变关系进行排序
     *
     * @param MRs
     * @return
     */
    private Map<String, List<String>> sortMRs(List<String> MRs) {
        Map<String, List<String>> sortedMRs = new LinkedHashMap<>();
        String source = "";
        String sourceChoices[] = null;
        String follow = "";
        String followChoices[] = null;
        for (int i = 0; i < MRs.size(); i++) {
            source = (String) MRs.get(i).split(";")[0].
                    subSequence(1, MRs.get(i).split(";")[0].length() - 1);
            sourceChoices = source.split(",");
            follow = (String) MRs.get(i).split(";")[1].
                    subSequence(1, MRs.get(i).split(";")[1].length() - 1);
            followChoices = follow.split(",");

            //统计原始测试用例与衍生测试用例的差距
            int counter = 0;
            int numberOfChoices = sourceChoices.length;
            if (sourceChoices.length < followChoices.length) {
                numberOfChoices = followChoices.length;
            }

            for (int j = 0; j < numberOfChoices; j++) {
                if (sourceChoices[j] != null && followChoices[j] != null) {
                    if (sourceChoices[j].equals(followChoices[j])) {
                        continue;
                    } else {
                        counter++;
                    }
                } else {
                    counter++;
                    continue;
                }
            }
            if (!sortedMRs.containsKey(String.valueOf(counter))) {
                List<String> tempList = new ArrayList<>();
                tempList.add(MRs.get(i));
                sortedMRs.put(String.valueOf(counter), tempList);
            } else {
                List<String> tempList = sortedMRs.get(String.valueOf(counter));
                tempList.add(MRs.get(i));
                sortedMRs.put(String.valueOf(counter), tempList);
            }
        }
        return sortedMRs;
    }

}
