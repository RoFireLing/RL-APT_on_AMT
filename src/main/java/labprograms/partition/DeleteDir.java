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
public class DeleteDir {

    public static void main(String[] args) {
        DeleteDir deleteDir = new DeleteDir();
        deleteDir.delete("MOS");
    }

    public void delete(String objectName) {
        String path = new Constant().getPartitionPath() + separator + objectName;
        File parentfile = new File(path);
        String[] partitionsNames = parentfile.list();
        for (int i = 0; i < partitionsNames.length; i++) {
            String partitionPath = path + separator + partitionsNames[i];
            File tempfile = new File(partitionPath);
            File[] tempfiles = tempfile.listFiles();
            for (File f : tempfiles) {
                f.delete();
            }
        }
    }

}
