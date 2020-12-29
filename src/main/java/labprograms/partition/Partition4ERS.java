package labprograms.partition;

import labprograms.constant.Constant;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 * partition1 | I-1a;I-3a;*
 * partition2 | I-1a;I-3b;*
 * partition3 | I-1a;I-3c;*
 * partition4 | I-1a;I-3d;*
 * partition5 | I-1b;I-3a;*
 * partition6 | I-1b;I-3b;*
 * partition7 | I-1b;I-3c;*
 * partition8 | I-1b;I-3d;*
 * partition9 | I-1c;I-3a;*
 * partition10 | I-1c;I-3b;*
 * partition11 | I-1c;I-3c;*
 * partition12 | I-1c;I-3d;*
 *
 * @author phantom
 * @date 2019/04/28
 */
public class Partition4ERS {

    public static void main(String[] args) {
        Partition4ERS ers = new Partition4ERS();
        ers.partition();
    }

    public void partition() {
        String path = new Constant().getMrPath() + separator + "ERS";

        File file = new File(path);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String tempStr = "";
            int count = 0;
            while ((tempStr = bufferedReader.readLine()) != null) {
                count++;
                if (tempStr.contains("*")) {
                    tempStr = tempStr.replace("*", "#");
                }
                String sourceTestFrame = tempStr.split(";")[0];

                if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "0" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "1" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3c")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "2" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3d")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "3" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "4" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "5" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3c")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "6" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3d")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "7" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "8" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "9" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3c")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "10" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3d")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ERS" + separator
                            + "11" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else {
                    System.out.println("第" + String.valueOf(count) + "行出错");
                }
            }
//            System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
