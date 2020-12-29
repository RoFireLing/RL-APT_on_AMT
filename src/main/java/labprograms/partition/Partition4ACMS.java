package labprograms.partition;

import labprograms.constant.Constant;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 * partition1 : I-1a;I-2a
 * partition2 : I-1a;I-2b
 * partition3 : I-1b;I-2a
 * partition4 : I-1b;I-2b
 * partition5 : I-1c;I-2a
 * partition6 : I-1c;I-2b
 * partition7 : I-1d;I-2a
 * partition8 : I-1d;I-2b
 *
 * @author phantom
 * @date 2019/04/28
 */
public class Partition4ACMS {

    public static void main(String[] args) {
        Partition4ACMS acms = new Partition4ACMS();
        acms.partition();
    }

    public void partition() {
        String path = new Constant().getMrPath() + separator + "ACMS";

        File file = new File(path);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String tempStr = "";
            int count = 0;
            while ((tempStr = bufferedReader.readLine()) != null) {
                count++;
                String sourceTestFrame = tempStr.split(";")[0];
                if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "0" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "1" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "2" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "3" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "4" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "5" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2a")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "6" + separator + tempStr;
                    File tempfile = new File(tempPath);
                    tempfile.mkdir();
                } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2b")) {
                    String tempPath = new Constant().getPartitionPath() + separator + "ACMS" + separator
                            + "7" + separator + tempStr;
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
