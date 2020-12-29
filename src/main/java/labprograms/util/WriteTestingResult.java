package labprograms.util;

import labprograms.constant.Constant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/04/19
 */
public class WriteTestingResult {

    public void write(String object, String mutantName, String testcasesinfo,
                      String count) {
        Constant constant = new Constant();
        String path = constant.testingresultdir + separator + object;
        File file = new File(path);
//        String content = mutantName + ";" + testcasesinfo + ";" + count;
        String content = mutantName + ";" + count;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
            printWriter.write(content + "\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
