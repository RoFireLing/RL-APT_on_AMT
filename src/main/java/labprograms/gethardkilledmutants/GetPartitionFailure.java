package labprograms.gethardkilledmutants;

import labprograms.constant.Constant;
import labprograms.strategies.util.Control;

import java.io.*;
import java.util.*;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/05/09
 */
public class GetPartitionFailure {

    public static void main(String[] args) {
        GetPartitionFailure getPartitionFailure = new GetPartitionFailure();
        for (int i = 0; i < 3; i++) {
            System.out.println("partition" + String.valueOf(i) + ":");
            getPartitionFailure.getPartitionFailureRate("CUBS", String.valueOf(i));
        }

    }

    public void getPartitionFailureRate(String objectName, String number) {
        String path = Constant.gethardkilledmutantsPath + separator
                + objectName + "UsedMutants";
        File file = new File(path);
        BufferedReader bufferedReader = null;
        Map<String, List<String>> listMap = new HashMap<>();

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String tempStr = "";
            while ((tempStr = bufferedReader.readLine()) != null) {
                String[] tempArray = tempStr.split(":");
                String mutantName = tempArray[0];
                String tcs = tempArray[2];
                if (tcs.contains(", ")) {
                    listMap.put(mutantName, Arrays.asList(tcs.split(", ")));
                } else {
                    tcs = tcs.replace("[", "").replace("]", "");
                    List<String> tcsList = new ArrayList<>();
                    tcsList.add(tcs);
                    listMap.put(mutantName, tcsList);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tempPath = Constant.mrDirPath + separator + objectName;
        File tempPathFile = new File(tempPath);
        Control control = new Control(objectName);
        int numberCounter = 0;
        try {
            for (Map.Entry<String, List<String>> entry : listMap.entrySet()) {
                bufferedReader = new BufferedReader(new FileReader(tempPathFile));
//                System.out.println(entry.getKey() + ":");
                String temp = "";
                int counter = 0;

                while ((temp = bufferedReader.readLine()) != null) {
                    counter++;
                    if (entry.getValue().contains(String.valueOf(counter))) {
                        int pa = control.judgeThePartitionOfFollowTestFrame(objectName, temp.split(";")[0]);
//                        System.out.println(temp + ":  "+String.valueOf(pa));
                        if (String.valueOf(pa).equals(number)) {
                            numberCounter++;
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(numberCounter);
    }


}
