package labprograms.partition;

import labprograms.constant.Constant;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 * partition1 : I-1a;I-3a
 * partition2 : I-1a;I-3b
 * partition3 : I-1b;I-3a
 * partition4 : I-1b;I-3b (该分区不存在)
 *
 * @author phantom
 * @date 2019/04/28
 */
public class Partition4CUBS {

    public static void main(String[] args) {
        Partition4CUBS cubs = new Partition4CUBS();
        cubs.partition();
    }

    public void partition() {
        String path = new Constant().getMrPath() + separator + "CUBS";

        File file = new File(path);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String tempStr = "";
            int count = 0;
            while ((tempStr = bufferedReader.readLine()) != null) {
                count++;
                String sourceTestFrame = tempStr.split(";")[0];
                if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "CUBS" + separator
                            + "0" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "CUBS" + separator
                            + "1" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "CUBS" + separator
                            + "2" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "CUBS" + separator
                            + "3" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else {
                    System.out.println("第" + String.valueOf(count) + "行出错");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
