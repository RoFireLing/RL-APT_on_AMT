package labprograms.strategies.util;

import labprograms.constant.Constant;

import java.io.File;
import java.util.*;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/05/08
 */
public class Control {
    private String objectName;

    /**
     * 包含分区中的测试用例对应的测试帧
     */
    private Map<String, Set<String>> sourceTestCasePartition4ACMS;
    private Map<String, String[]> sourcefollowMRPartition4ACMS;

    private Map<String, Set<String>> sourceTestCasePartition4CUBS;
    private Map<String, String[]> sourcefollowMRPartition4CUBS;

    private Map<String, Set<String>> sourceTestCasePartition4ERS;
    private Map<String, String[]> sourcefollowMRPartition4ERS;

    private Map<String, Set<String>> sourceTestCasePartition4MOS;
    private Map<String, String[]> sourcefollowMRPartition4MOS;

    public Control(String objectName) {
        this.objectName = objectName;
        initializePartitions();
    }

    public static void main(String[] args) {
        Control control = new Control("ERS");
        System.out.println(control.PBMRGetMR(8,
                "{I-1c,#,I-3a,I-4a,#}"));
    }

    /**
     * 初始化ACMS系统的map，键为分区号，值为分区中对应的 source test cases
     */
    private void initializePartitions() {
        if (objectName.equals("ACMS")) {
            sourceTestCasePartition4ACMS = new HashMap<>();
            sourcefollowMRPartition4ACMS = new HashMap<>();
            String path = Constant.partitionPath + separator + objectName;
            File file = new File(path);
            String[] partitionsName = file.list();
            for (int i = 0; i < partitionsName.length; i++) {
                Set<String> testframes = new HashSet<>();
                String partitionPath = path + separator + String.valueOf(i);
                File tempFile = new File(partitionPath);
                String[] fileNames = tempFile.list();
                sourcefollowMRPartition4ACMS.put(String.valueOf(i), fileNames);
                for (String name : fileNames) {
                    String sourceTestFrames = name.split(";")[0];
                    testframes.add(sourceTestFrames);
                }
                sourceTestCasePartition4ACMS.put(String.valueOf(i), testframes);
            }

        } else if (objectName.equals("CUBS")) {
            sourceTestCasePartition4CUBS = new HashMap<>();
            sourcefollowMRPartition4CUBS = new HashMap<>();
            String path = Constant.partitionPath + separator + objectName;
            for (int i = 0; i < Constant.getPartitionNumber(objectName); i++) {
                Set<String> testframes = new HashSet<>();
                String partitionPath = path + separator + String.valueOf(i);
                File tempFile = new File(partitionPath);
                String[] fileNames = tempFile.list();
                sourcefollowMRPartition4CUBS.put(String.valueOf(i), fileNames);
                for (String name : fileNames) {
                    String sourceTestFrames = name.split(";")[0];
                    testframes.add(sourceTestFrames);
                }
                sourceTestCasePartition4CUBS.put(String.valueOf(i), testframes);
            }
        } else if (objectName.equals("ERS")) {
            sourceTestCasePartition4ERS = new HashMap<>();
            sourcefollowMRPartition4ERS = new HashMap<>();
            String path = Constant.partitionPath + separator + objectName;
            for (int i = 0; i < Constant.getPartitionNumber(objectName); i++) {
                Set<String> testframes = new HashSet<>();
                String partitionPath = path + separator + String.valueOf(i);
                File tempFile = new File(partitionPath);
                String[] fileNames = tempFile.list();
                sourcefollowMRPartition4ERS.put(String.valueOf(i), fileNames);
                for (String name : fileNames) {
                    String sourceTestFrames = name.split(";")[0];
                    testframes.add(sourceTestFrames);
                }
                sourceTestCasePartition4ERS.put(String.valueOf(i), testframes);
            }
        } else {
            sourceTestCasePartition4MOS = new HashMap<>();
            sourcefollowMRPartition4MOS = new HashMap<>();
            String path = Constant.partitionPath + separator + objectName;
            File file = new File(path);
            String[] partitionsName = file.list();
            for (int i = 0; i < partitionsName.length; i++) {
                Set<String> testframes = new HashSet<>();
                String partitionPath = path + separator + String.valueOf(i);
                File tempFile = new File(partitionPath);
                String[] fileNames = tempFile.list();
                sourcefollowMRPartition4MOS.put(String.valueOf(i), fileNames);
                for (String name : fileNames) {
                    String sourceTestFrames = name.split(";")[0];
                    testframes.add(sourceTestFrames);
                }
                sourceTestCasePartition4MOS.put(String.valueOf(i), testframes);
            }
        }
    }

    /**
     * 判断随机的MR编号中的原始测试用例是否在选中的分区中
     *
     * @param indexOfPartition 选中的分区编号
     * @param sourceTestCase   随机的mr内容中提取的source test frame
     * @return
     */
    public boolean isTestFrameBelongToPartition(int indexOfPartition, String sourceTestCase) {
        boolean flag = true;
        Set<String> tempSet = new HashSet<>();
        if (objectName.equals("ACMS")) {
            return sourceTestCasePartition4ACMS.get(String.valueOf(indexOfPartition)).contains(sourceTestCase);
        } else if (objectName.equals("CUBS")) {
            return sourceTestCasePartition4CUBS.get(String.valueOf(indexOfPartition)).contains(sourceTestCase);
        } else if (objectName.equals("ERS")) {
            return sourceTestCasePartition4ERS.get(String.valueOf(indexOfPartition)).contains(sourceTestCase);
        } else {
            return sourceTestCasePartition4ERS.get(String.valueOf(indexOfPartition)).contains(sourceTestCase);
        }
    }

    /**
     * 获得一个MR根据程序的属性
     *
     * @return
     */
    public String PBMRGetMR(int indexOfPartition, String sourceTestCase) {
        List<String> candidates = new ArrayList<>();
        List<String> bestCandidateList = new ArrayList<>();
        String bestCandidate = "";
        if (objectName.equals("ACMS")) {
            String[] tempArray = sourcefollowMRPartition4ACMS.get(String.valueOf(indexOfPartition));
            for (String str : tempArray) {
                if (str.split(";")[0].contains(sourceTestCase)) {
                    candidates.add(str);
                }
            }
        } else if (objectName.equals("CUBS")) {
            String[] tempArray = sourcefollowMRPartition4CUBS.get(String.valueOf(indexOfPartition));
            for (String str : tempArray) {
                if (str.split(";")[0].contains(sourceTestCase)) {
                    candidates.add(str);
                }
            }
        } else if (objectName.equals("ERS")) {
            String[] tempArray = sourcefollowMRPartition4ERS.get(String.valueOf(indexOfPartition));
            for (String str : tempArray) {
                if (str.split(";")[0].contains(sourceTestCase)) {
                    candidates.add(str);
                }
            }
        } else {
            String[] tempArray = sourcefollowMRPartition4MOS.get(String.valueOf(indexOfPartition));
            for (String str : tempArray) {
                if (str.split(";")[0].contains(sourceTestCase)) {
                    candidates.add(str);
                }
            }
        }
//        bestCandidate = candidates.get(0);
        if (objectName.equals("ACMS")) {
            bestCandidate = candidates.get(0);
            int MaxDA = compareSourceTestFrameAndFollowUpTestFrame(candidates.get(0).split(";")[0],
                    candidates.get(0).split(";")[1]);

            for (int i = 1; i < candidates.size(); i++) {
                String tempSource = candidates.get(i).split(";")[0];
                String tempFollow = candidates.get(i).split(";")[1];
                int tempDA = compareSourceTestFrameAndFollowUpTestFrame(tempSource, tempFollow);
                if (tempDA > MaxDA) {
                    bestCandidate = candidates.get(i);
                    MaxDA = tempDA;
                }
            }
            return bestCandidate;
        } else if (objectName.equals("ERS")) {
            return candidates.get(new Random().nextInt(candidates.size()));
        } else {
            bestCandidateList.add(candidates.get(0));
            int MaxDA = compareSourceTestFrameAndFollowUpTestFrame(candidates.get(0).split(";")[0],
                    candidates.get(0).split(";")[1]);

            for (int i = 1; i < candidates.size(); i++) {
                String tempSource = candidates.get(i).split(";")[0];
                String tempFollow = candidates.get(i).split(";")[1];
                int tempDA = compareSourceTestFrameAndFollowUpTestFrame(tempSource, tempFollow);
                if (tempDA == MaxDA) {
                    bestCandidateList.add(candidates.get(i));
                }
                if (tempDA > MaxDA) {
                    bestCandidateList.clear();
                    bestCandidateList.add(candidates.get(i));
                    MaxDA = tempDA;
                }
            }
            return bestCandidateList.get(new Random().nextInt(bestCandidateList.size()));
        }
    }

    /**
     * 根据原始帧和衍生测试帧计算DA距离
     *
     * @param sourceTestFrame   原始帧
     * @param followUpTestFrame 衍生帧
     * @return 距离
     */
    private int compareSourceTestFrameAndFollowUpTestFrame(String sourceTestFrame,
                                                           String followUpTestFrame) {
        sourceTestFrame = sourceTestFrame.
                replace("{", "").
                replace("}", "");

        followUpTestFrame = followUpTestFrame.
                replace("{", "").
                replace("}", "");

        String[] sourceChioces = sourceTestFrame.split(",");
        String[] followChioces = followUpTestFrame.split(",");
        int da = 0;
        if (sourceChioces.length > followChioces.length) {
            da = sourceChioces.length - followChioces.length;
            for (int i = 0; i < followChioces.length; i++) {
                if (!sourceChioces[i].equals(followChioces[i])) {
                    da++;
                }
            }
        } else if (sourceChioces.length < followChioces.length) {
            da = followChioces.length - sourceChioces.length;
            for (int i = 0; i < followChioces.length; i++) {
                if (!sourceChioces[i].equals(followChioces[i])) {
                    da++;
                }
            }
        } else {
            for (int i = 0; i < followChioces.length; i++) {
                if ((sourceChioces[i].equals("#") || followChioces[i].equals("#"))) {
                    continue;
                }
                if (!sourceChioces[i].equals(followChioces[i])) {
                    da++;
                }
            }
        }
        return da;
    }

    public int judgeThePartitionOfFollowTestFrame(String objectName,
                                                  String followTestFrame) {
        String sourceTestFrame = followTestFrame;
        if (objectName.equals("ACMS")) {
            if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2a")) {
                return 0;
            } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2b")) {
                return 1;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2a")) {
                return 2;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2b")) {
                return 3;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2a")) {
                return 4;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2b")) {
                return 5;
            } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2a")) {
                return 6;
            } else {
                return 7;
            }
        } else if (objectName.equals("CUBS")) {
            if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3a")) {
                return 0;
            } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3b")) {
                return 1;
            } else {
                return 2;
            }
        } else if (objectName.equals("ERS")) {
            if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3a")) {
                return 0;
            } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3b")) {
                return 1;
            } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3c")) {
                return 2;
            } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-3d")) {
                return 3;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3a")) {
                return 4;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3b")) {
                return 5;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3c")) {
                return 6;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-3d")) {
                return 7;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3a")) {
                return 8;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3b")) {
                return 9;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-3c")) {
                return 10;
            } else {
                return 11;
            }
        } else {
            if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2a")) {
                return 0;
            } else if (sourceTestFrame.contains("I-1a") && sourceTestFrame.contains("I-2b")) {
                return 1;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2a")) {
                return 2;
            } else if (sourceTestFrame.contains("I-1b") && sourceTestFrame.contains("I-2b")) {
                return 3;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2a")) {
                return 4;
            } else if (sourceTestFrame.contains("I-1c") && sourceTestFrame.contains("I-2b")) {
                return 5;
            } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2a")) {
                return 6;
            } else if (sourceTestFrame.contains("I-1d") && sourceTestFrame.contains("I-2b")) {
                return 7;
            } else if (sourceTestFrame.contains("I-1e") && sourceTestFrame.contains("I-2a")) {
                return 8;
            } else {
                return 9;
            }
        }
    }

}
