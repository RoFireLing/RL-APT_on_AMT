package labprograms.partition;

import labprograms.constant.Constant;

import java.io.File;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/04/28
 */
public class GenerateDir {

    public static void main(String[] args) {
        GenerateDir generateDir = new GenerateDir();
        generateDir.generateDir("MOS");
    }

    public void generateDir(String objectName) {
        String path = new Constant().getPartitionPath() + separator + objectName;
        if (objectName.equals("ACMS")) {
            for (int i = 0; i < 8; i++) {
                String tempPath = path + separator + String.valueOf(i);
                File file = new File(tempPath);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        } else if (objectName.equals("CUBS")) {
            for (int i = 0; i < 4; i++) {
                String tempPath = path + separator + String.valueOf(i);
                File file = new File(tempPath);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        } else if (objectName.equals("ERS")) {
            for (int i = 0; i < 12; i++) {
                String tempPath = path + separator + String.valueOf(i);
                File file = new File(tempPath);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        } else {
            for (int i = 0; i < 12; i++) {
                String tempPath = path + separator + String.valueOf(i);
                File file = new File(tempPath);
                if (!file.exists()) {
                    file.mkdir();
                }
            }
        }
    }
}
