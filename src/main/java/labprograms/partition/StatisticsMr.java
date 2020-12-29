package labprograms.partition;

import labprograms.constant.Constant;

import java.io.File;

import static java.io.File.separator;

/**
 * describe:
 * this class is responsible to statistics the number of MRs
 *
 * @author phantom
 * @date 2019/04/29
 */
public class StatisticsMr {

    public static void main(String[] args) {
        StatisticsMr statisticsMr = new StatisticsMr();
        statisticsMr.statisticsMr("MOS");
    }

    public void statisticsMr(String objectName) {
        String path = new Constant().getPartitionPath() + separator + objectName;
        File parentfile = new File(path);
        String[] partitionsNames = parentfile.list();
        int numbers = 0;
        for (int i = 0; i < partitionsNames.length; i++) {
            String partitionPath = path + separator + partitionsNames[i];
            File tempfile = new File(partitionPath);
            File[] tempfiles = tempfile.listFiles();
            numbers += tempfiles.length;
        }
        System.out.println("一共检测到了" + String.valueOf(numbers) + "个MRs");
    }
}
