package labprograms.partition;

import labprograms.constant.Constant;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 * partition1 | I-1a;I-3a;*
 * partition2 | I-1a;I-3b;*
 * partition3 | I-1a;I-3c;*
 * partition4 | I-1a;I-3d;* (不存在)
 * partition5 | I-1b;I-3a;*
 * partition6 | I-1b;I-3b;*
 * partition7 | I-1b;I-3c;*
 * partition8 | I-1b;I-3d;*（不存在）
 * partition9 | I-1c;I-3a;*
 * partition10 | I-1c;I-3b;*
 * partition11 | I-1c;I-3c;*
 * partition12 | I-1c;I-3d;*（不存在）
 *
 * @author phantom
 * @date 2019/04/28
 */
public class Partition4MOS {
    public static void main(String[] args) {
        Partition4MOS mos = new Partition4MOS();
        mos.partition();
    }

    public void partition() {
        String path = new Constant().getMrPath() + separator + "MOS";

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
                if (tempStr.contains("->")) {
                    tempStr = tempStr.replace("->", "#");
                }
                String sourceTestFrame = tempStr.split(";")[0];

                if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "0" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "1" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "2" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "3" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "4" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "5" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "6" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "7" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1e") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "8" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    if (tempfile.mkdir()) {
                        System.out.println(tempPath + "创建成功");
                    } else {
                        System.out.println(tempPath + "创建不成功");
                    }
                } else if (sourceTestFrame.contains("I-1e") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "MOS" + separator
                            + "9" + separator + tempStr;
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
            System.out.println(count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
