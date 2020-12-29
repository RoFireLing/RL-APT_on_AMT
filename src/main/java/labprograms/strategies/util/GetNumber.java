package labprograms.strategies.util;

import labprograms.constant.Constant;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/05/12
 */
public class GetNumber {


    public int getNumber(String mr, String objectName) {
        int index = 0;
        String path = Constant.mrDirPath + separator + objectName;
        File file = new File(path);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String tempstr = "";
            int counter = 0;
            while ((tempstr = bufferedReader.readLine()) != null) {
                counter++;
                if (tempstr.equals(mr)) {
                    index = counter;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return index;
    }


}
