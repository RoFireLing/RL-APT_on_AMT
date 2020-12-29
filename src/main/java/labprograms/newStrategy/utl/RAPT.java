package labprograms.newStrategy.utl;

import java.util.Random;

/**
 * @Description:
 * @auther phantom
 * @create 2019-09-23 下午5:19
 */
public class RAPT {
    private static final int[] pun4ACMS = {79, 73, 67, 60, 96, 84, 29, 23};
    private static final int[] pun4CUBS = {50, 5, 43};
    private static final int[] pun4ERS = {91, 89, 88, 126, 62, 39, 39, 51, 55, 52, 35, 53};
    private static final int[] pun4MOS = {390, 298, 403, 261, 403, 217, 226, 84, 132, 21};

    /**
     * the test profile of RAPT
     */
    private double[] RAPT;

    private double RAPT_epsilon = 0.05;

    private double RAPT_delta;

    /**
     * the factor of reward
     */
    private int[] rew;

    /**
     * the factor of punishment
     */
    private int[] bou;

    private int[] pun;

    private void setRAPT_delta(double RAPT_delta) {
        this.RAPT_delta = RAPT_delta;
    }

    private void setBou_RAPT(String objectName, int[] punArray) {
        if (objectName.equals("ACMS")) {
            rew = new int[8];
            pun = new int[8];
            bou = new int[8];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        } else if (objectName.equals("CUBS")) {
            rew = new int[3];
            pun = new int[3];
            bou = new int[3];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        } else if (objectName.equals("ERS")) {
            rew = new int[12];
            pun = new int[12];
            bou = new int[12];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        } else {
            rew = new int[10];
            pun = new int[10];
            bou = new int[10];
            for (int i = 0; i < punArray.length; i++) {
                bou[i] = punArray[i];
            }
        }

    }

    /**
     * initialize the test profile of RAPT
     *
     * @param numberOfPartitions the number of partitions
     */
    public void initializeRAPT(int numberOfPartitions) {
        RAPT = new double[numberOfPartitions];
        for (int i = 0; i < RAPT.length; i++) {
            RAPT[i] = 1.0 / numberOfPartitions;
            pun[i] = 0;
        }
    }

    /**
     * get a index of partition
     * Note that the first number of partitions is 0
     *
     * @return the index
     */
    public int nextPartition4RAPT() {
        boolean flag = false;
        int partitionindex = 0;
        for (int i = 0; i < rew.length; i++) {
            if (rew[i] > 0) {
                flag = true;
                partitionindex = i;
                break;
            }
        }
        if (flag) {
            return partitionindex;
        } else {
            int index = -1;
            double randomNumber = new Random().nextDouble();
            double sum = 0;
            do {
                index++;
                sum += RAPT[index];
            } while (randomNumber >= sum && index < RAPT.length - 1);
            return index;
        }
    }


    /**
     * adjust the test profile for RAPT testing
     *
     * @param
     * @param isKilledMutant
     */
    public void adjustRAPT(int formersourcePartitionIndex,
                           int formerfollowUpPartitionIndex,
                           boolean isKilledMutant) {
        double old_i = RAPT[formersourcePartitionIndex];
        double old_f = RAPT[formerfollowUpPartitionIndex];

        if (formersourcePartitionIndex == formerfollowUpPartitionIndex) {
            if (isKilledMutant) {
                double sum = 0;
                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex) {
                        RAPT[i] -= (1 + Math.log(rew[formersourcePartitionIndex]))
                                * RAPT_epsilon / (RAPT.length - 1);
                        if (RAPT[i] < 0) {
                            RAPT[i] = 0;
                        }
                    }
                    sum += RAPT[i];
                }
                RAPT[formersourcePartitionIndex] = 1 - sum;
            } else {
                for (int i = 0; i < RAPT.length; i++) {
                    if (i == formersourcePartitionIndex) {
                        if (old_i >= RAPT_delta) {
                            RAPT[i] -= RAPT_delta;
                        }
                        if (old_i < RAPT_delta || bou[i] == pun[i]) {
                            RAPT[i] = 0;
                        }
                    } else {
                        if (old_i >= RAPT_delta) {
                            RAPT[i] += RAPT_delta / (RAPT.length - 1);
                        }
                        if (old_i < RAPT_delta || bou[i] == pun[i]) {
                            RAPT[i] += old_i / (RAPT.length - 1);
                        }
                    }
                }
            }
        } else { // not belong to the same partition
            if (isKilledMutant) {
                double sum = 0;
                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex && i != formerfollowUpPartitionIndex) {
                        if (RAPT[i] > (RAPT_epsilon * (1 + Math.log(rew[i])) / (RAPT.length - 2))) {
                            RAPT[i] -= RAPT_epsilon * (1 + Math.log(rew[i])) / (RAPT.length - 2);
                        } else {
                            RAPT[i] = 0;
                        }
                    }
                    sum += RAPT[i];
                }
                RAPT[formersourcePartitionIndex] = old_i + ((1 - sum) - old_i - old_f) / 2;
                RAPT[formerfollowUpPartitionIndex] = old_f + ((1 - sum) - old_f - old_i) / 2;
            } else { // not reveal a mutant
                if (old_i > RAPT_delta) {
                    RAPT[formersourcePartitionIndex] = old_i - RAPT_delta;
                }
                if (old_i <= RAPT_delta || pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]) {
                    RAPT[formersourcePartitionIndex] = 0;
                    if (pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]) {
                        pun[formersourcePartitionIndex] = 0;
                    }
                }
                if (old_f > RAPT_delta) {
                    RAPT[formerfollowUpPartitionIndex] = old_f - RAPT_delta;
                }
                if (old_f <= RAPT_delta || pun[formerfollowUpPartitionIndex] == bou[formerfollowUpPartitionIndex]) {
                    RAPT[formerfollowUpPartitionIndex] = 0;
                    if (pun[formersourcePartitionIndex] == bou[formersourcePartitionIndex]) {
                        pun[formersourcePartitionIndex] = 0;
                    }
                }

                for (int i = 0; i < RAPT.length; i++) {
                    if (i != formersourcePartitionIndex && i != formerfollowUpPartitionIndex) {
                        RAPT[i] += ((old_i - RAPT[formersourcePartitionIndex]) +
                                (old_f - RAPT[formerfollowUpPartitionIndex])) / (RAPT.length - 2);
                    }
                }
            }
        }

    }

    public void setParameters4RAPT(String objectName) {
        if (objectName.equals("ACMS")) {
            setRAPT_delta(0.01718312324929972);
            setBou_RAPT(objectName, pun4ACMS);
        } else if (objectName.equals("CUBS")) {
            setRAPT_delta(0.026004801920768313);
            setBou_RAPT(objectName, pun4CUBS);
        } else if (objectName.equals("ERS")) {
            setRAPT_delta(0.019839685749552596);
            setBou_RAPT(objectName, pun4ERS);
        } else {
            setRAPT_delta(0.009424821887743086);
            setBou_RAPT(objectName, pun4MOS);
        }
    }

}
