package labprograms.mr;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.io.File.separator;

/**
 * describe:
 * this class is responsible to parse xml file and get the relation of two inputs
 *
 * @author phantom
 * @date 2019/04/25
 */
public class ParseXML {


    private String path = System.getProperty("user.dir") + separator + "src" + separator
            + "main" + separator + "java" + separator + "labprograms" + separator + "mr";

    private List<String> testframeAndMr;

    private String objectName;


    public ParseXML(String objectName) {
        this.objectName = objectName;
        this.testframeAndMr = new ArrayList<>();
    }

    public static void main(String[] args) {
        ParseXML parseXML = new ParseXML("MOS");
        parseXML.getAllRelations();
        parseXML.writeTestframeAndMr();
    }

    /**
     * the method that parses the xml file in order to get the content of relations
     */
    public void getAllRelations() {
        Set<String> relations = new HashSet<>();
        String pathFile = path + separator + objectName + ".xml";
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            //get the object of xml file
            document = saxReader.read(pathFile);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //get root node
        Element root = document.getRootElement();
        //get "pairsfromdiffgroups" node
        Element pairsfromdiffgroupsNode = root.element("pairsfromdiffgroups");
        List<Element> pairsfromdiffgroupsList = pairsfromdiffgroupsNode.elements();
        int countNo = 0;

        for (Element ele : pairsfromdiffgroupsList) {
            // get all elements of pairsfromdiffgroupsNode
            List<Element> elegroups = ele.elements();
            for (Element group : elegroups) {
                // get all mr info
                List<Element> mrinfo = group.elements();
                String str = "";
                for (Element info : mrinfo) {

                    if (info.getName().equals("hasmr")) {
                        if (info.getText().equals("No")) {
                            countNo++;
                            break;
                        }
                    }
                    if (info.getName().equals("inputrelationdefinition")) {
                        String temp_str = info.getText();
                        String[] tempArray = temp_str.split("->");
                        str += tempArray[0] + ";";
                        str += tempArray[1] + ";";
                    }
                    if (info.getName().equals("outputrelationdefinition")) {
                        str += info.getText();
                        relations.add(info.getText());
                    }
                }
                if (!(str.equals("") || str.equals("\n"))) {
                    testframeAndMr.add(str);
                }

            }
        }

        //get "pairsfromdiffgroups" node
        Element pairsfromsamegroupsNode = root.element("pairsfromsamegroups");
        List<Element> pairsfromsamegroupsList = pairsfromsamegroupsNode.elements();
        for (Element ele : pairsfromsamegroupsList) {
            // get all elements of pairsfromdiffgroupsNode
            List<Element> elegroups = ele.elements();
            for (Element group : elegroups) {
                // get all mr info
                List<Element> mrinfo = group.elements();
                String str = "";
                for (Element info : mrinfo) {

                    if (info.getName().equals("hasmr")) {
                        if (info.getText().equals("No")) {
                            countNo++;
                            break;
                        }
                    }
                    if (info.getName().equals("inputrelationdefinition")) {
                        String temp_str = info.getText();
                        String[] tempArray = temp_str.split("->");
                        str += tempArray[0] + ";";
                        str += tempArray[1] + ";";
                    }

                    if (info.getName().equals("outputrelationdefinition")) {
                        str += info.getText();
                        relations.add(info.getText());
                    }
                }
                if (!(str.equals("") || str.equals("\n"))) {
                    testframeAndMr.add(str);
                }
            }
        }
        System.out.println(relations);
        System.out.println("不存在MR的条目有：" + String.valueOf(countNo));
    }

    public void writeTestframeAndMr() {
        String filePath = path + separator + objectName;
        File file = new File(filePath);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < testframeAndMr.size(); i++) {
            printWriter.write(testframeAndMr.get(i) + "\n");
        }
        printWriter.close();
    }
}
