package labprograms.strategies.util;

import labprograms.constant.Constant;

import java.io.File;
import java.util.Random;

import static java.io.File.separator;

/**
 * describe: this class is responsible to record
 * the test cases of each partition for every object
 *
 * @author phantom
 * @date 2019/05/14
 */
public class TestCasesOfPartition {

    private String objectName;

    private Bin[] partitionsInfo;

    private Random random;

    public TestCasesOfPartition(String objectName) {
        random = new Random();
        this.objectName = objectName;
        if (this.objectName.equals("ACMS")) {
            partitionsInfo = (Bin[]) new Bin[Constant.getPartitionNumber(objectName)];
            for (int i = 0; i < Constant.getPartitionNumber(objectName); i++) {
                partitionsInfo[i] = new Bin();
            }
        } else if (this.objectName.equals("CUBS")) {
            partitionsInfo = (Bin[]) new Bin[Constant.getPartitionNumber(objectName)];
            for (int i = 0; i < Constant.getPartitionNumber(objectName); i++) {
                partitionsInfo[i] = new Bin();
            }
        } else if (this.objectName.equals("ERS")) {
            partitionsInfo = (Bin[]) new Bin[Constant.getPartitionNumber(objectName)];
            for (int i = 0; i < Constant.getPartitionNumber(objectName); i++) {
                partitionsInfo[i] = new Bin();
            }
        } else {
            partitionsInfo = (Bin[]) new Bin[Constant.getPartitionNumber(objectName)];
            for (int i = 0; i < Constant.getPartitionNumber(objectName); i++) {
                partitionsInfo[i] = new Bin();
            }
        }
        reloadPartitionTestCases();
    }


    private void reloadPartitionTestCases() {
        String path = Constant.partitionPath + separator + objectName;
        for (int i = 0; i < partitionsInfo.length; i++) {
            String tempPartitionPath = path + separator + String.valueOf(i);
            File file = new File(tempPartitionPath);
            String[] testcases = file.list();
            for (int j = 0; j < testcases.length; j++) {
                partitionsInfo[i].add(testcases[j]);
            }
        }
    }


    public String getSourceFollowAndMR(int partitionIndex) {
        int size = partitionsInfo[partitionIndex].size();
        int randomNumber = random.nextInt(size);
        String sourceFollowAndMR = partitionsInfo[partitionIndex].getTestCase(randomNumber);
        return sourceFollowAndMR;
    }


}
