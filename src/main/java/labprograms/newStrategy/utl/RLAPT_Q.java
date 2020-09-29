package labprograms.newStrategy.utl;

import java.util.Random;

public class RLAPT_Q {

    // param of RL-APT
    private double[][] RLAPT;

    private double RLAPT_alpha = 1;

    private double RLAPT_gamma = 0.6;

    private double RLAPT_r0 = 1;

    // initialize the Q-table of RL-APT
    public void initializeRLAPT(int numberofPartitions) {
        RLAPT = new double[numberofPartitions][numberofPartitions];
        for (int i = 0; i < numberofPartitions; i++) {
            for (int j = 0; j < numberofPartitions; j++) {
                RLAPT[i][j] = 0;
            }
        }
    }

    // get a index of partition (epsilon-greedy)
    public int nextPartition4RLAPT(int formerPartitionNumber, int noTC) {
        // epsilon-greedy
//        int index = -1;
//        double randomNumber = new Random().nextDouble();
////        double epsilon = 1 / Math.sqrt(noTC);
//        double epsilon;
//        if (noTC < 5 * RLAPT.length)
//            epsilon = 1 - 0.1 * (noTC / RLAPT.length);
//        else
//            epsilon = 0.5;
//
//        if (randomNumber <= epsilon) {
//            index = new Random().nextInt(RLAPT.length);
//        } else
//            index = (int) getMax(RLAPT[formerPartitionNumber])[1];
//        return index;

        // Boltzmann
        double[] probability = new double[RLAPT.length];
        double prosum = 0;
        for (int i = 0; i < probability.length; i++) {
            probability[i] = Math.exp(RLAPT[formerPartitionNumber][i]);
            prosum += probability[i];
        }
        for (int i = 0; i < probability.length; i++) {
            probability[i] /= prosum;
        }
        int index = -1;
        double randomNumber = new Random().nextDouble();
        double sum = 0;
        do {
            index++;
            sum += probability[index];
        } while (randomNumber >= sum && index < probability.length - 1);
        return index;
    }

    // adjust the Q-table for RLAPT testing based on Q-Learning
    // NextPartitionIndex = nextPartition4RLAPT(NowPartitionIndex, noTC)
    public void adjustRLAPT_Q(int NowPartitionIndex, int NextPartitionIndex, boolean isKilledMutans) {
        double r = 0;
        if (NowPartitionIndex == NextPartitionIndex) {
            if (isKilledMutans) {
                r = RLAPT_r0;
            } else
                r = -RLAPT_r0;
        } else {
            if (isKilledMutans) {
                r = -RLAPT_r0 / RLAPT.length;
            } else
                r = RLAPT_r0 / RLAPT.length;
        }
        RLAPT[NowPartitionIndex][NextPartitionIndex] += RLAPT_alpha * (r
                + RLAPT_gamma * getMax(RLAPT[NextPartitionIndex])[0] - RLAPT[NowPartitionIndex][NextPartitionIndex]);
    }

    // get MaxValue or MaxValueIndex
    public double[] getMax(double[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int maxIndex = 0;
        double[] arrnew = new double[2];
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[maxIndex] < arr[i + 1]) {
                maxIndex = i + 1;
            }
        }
        arrnew[0] = arr[maxIndex];
        arrnew[1] = maxIndex;
        return arrnew;
    }
}
