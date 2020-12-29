package labprograms.testCase;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;

/**
 * describe:
 * 产生ACMS的测试用例集，写进XML文档中
 *
 * @author phantom
 * @date 2019/07/22
 */
public class GenerateACMSTestCaseInXML {

    public static void main(String[] args) {
        GenerateACMSTestCaseInXML generator = new GenerateACMSTestCaseInXML();
        generator.writeTestSuite2XML();
    }

    /**
     * 生成测试用例
     *
     * @return
     */
    private List<TestCase4ACMS4XML> generateTestCases4ACMS() {
        List<TestCase4ACMS4XML> suite = new ArrayList<>();
        TestCase4ACMS4XML case1 = new TestCase4ACMS4XML(0, 0, true,
                0.00, 1000.15, 0.00, 1,
                "1-1;2-1;3-1;4-1;5-1");
        suite.add(case1);
        return suite;
    }

    /**
     * 将生成的测试用例写入XML文件中
     */
    public void writeTestSuite2XML() {
        //获取测试用例集
        List<TestCase4ACMS4XML> suite = generateTestCases4ACMS();

        //创建xml对象
        Document document = DocumentHelper.createDocument();
        //创建xml的根节点
        Element root = document.addElement("testSuite");
        //生产分区节点
        Element partition_1_Element = root.addElement("partition");
        partition_1_Element.addAttribute("id", "1");
        partition_1_Element.addAttribute("scheme", "1-1;2-1");
        for (int i = 0; i < suite.size(); i++) {
            Element testCase = partition_1_Element.addElement("testCase");
            testCase.addAttribute("id", String.valueOf(i));
            Element airClass = testCase.addElement("airClass");
            airClass.setText(String.valueOf(suite.get(i).getAirClass()));
            Element area = testCase.addElement("area");
            area.setText(String.valueOf(suite.get(i).getArea()));
            Element isStudent = testCase.addElement("isStudent");
            isStudent.setText(String.valueOf(suite.get(i).isStudent()));
            Element luggage = testCase.addElement("luggage");
            luggage.setText(String.valueOf(suite.get(i).getLuggage()));
            Element economicfee = testCase.addElement("economicfee");
            economicfee.setText(String.valueOf(suite.get(i).getEconomicfee()));
            Element expectedResult = testCase.addElement("expectedResult");
            expectedResult.setText(String.valueOf(suite.get(i).getExpectedResult()));
            Element options = testCase.addElement("options");
            options.setText(suite.get(i).getOptions());
        }

        //设置生成的xml格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置编码格式
        format.setEncoding("UTF-8");

        try {
            XMLWriter writer = new XMLWriter(new FileOutputStream(new File(
                    System.getProperty("user.dir") + separator + "testSuite4ACMS.xml")));

            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("生成xml成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
