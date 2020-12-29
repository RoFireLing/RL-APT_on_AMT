package labprograms.mr;

import java.io.*;

import static java.io.File.separator;

/**
 * describe:
 * delete the brace of each line in the specified file
 *
 * @author phantom
 * @date 2019/04/28
 */
public class DeletBrace {

    private String path = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr";

    private String objectName;

    public DeletBrace(String objectName) {
        this.objectName = objectName;
    }

    public static void main(String[] args) {
        DeletBrace deletBrace = new DeletBrace("MOS");
        deletBrace.deleteBrace();
    }

    /**
     * the method that deletes the brace
     */
    public void deleteBrace() {
        String filePath = path + separator + objectName;
        File oldfile = new File(filePath);
        File newfile = new File(filePath);
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new FileReader(oldfile));
            int temp = bufferedReader.read();
            printWriter = new PrintWriter(new FileWriter(newfile));
            String tempStr = "";
            while ((tempStr = bufferedReader.readLine()) != null) {
                for (int i = 0; i < tempStr.length(); i++) {
                    char getChar = tempStr.charAt(i);
                    if (getChar == '}' || getChar == '{') {
                        continue;
                    }
                    stringBuffer.append(getChar);
                }
                stringBuffer.append("\n");
                printWriter.write(stringBuffer.toString());
                stringBuffer.delete(0, stringBuffer.length());
            }
            bufferedReader.close();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
