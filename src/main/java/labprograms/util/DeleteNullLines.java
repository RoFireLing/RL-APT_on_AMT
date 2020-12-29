package labprograms.util;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 * delete the null lines in the specified file
 *
 * @author phantom
 * @date 2019/04/27
 */
public class DeleteNullLines {
    private String path = System.getProperty("user.dir") + separator + "src" + separator +
            "main" + separator + "java" + separator + "labprograms" + separator + "mr";
    private String objectName;

    public static void main(String[] args) {
        DeleteNullLines deleteNullLines = new DeleteNullLines();
        deleteNullLines.deleteNullLines("MOS");
    }

    public void deleteNullLines(String objectName) {
        this.objectName = objectName;
        String filePath = path + separator + objectName;
        File file = new File(filePath);
        File newfile = new File(filePath);
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            printWriter = new PrintWriter(new FileWriter(newfile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String tempStr = "";
        try {
            while ((tempStr = bufferedReader.readLine()) != "\n") {
                printWriter.write(tempStr + "\n");
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
