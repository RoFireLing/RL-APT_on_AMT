import labprograms.mutants.Mutant;
import labprograms.mutants.MutantsSet;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static java.io.File.separator;

/**
 * describe:
 * this calss is responsible to generate the Junit scripts
 *
 * @author phantom
 * @date 2019/04/20
 */
public class GenerateScripts {
    @Setter
    private String objectName;


    public GenerateScripts(String name) {
        setObjectName(name);
    }

    public static void main(String[] args) {
        GenerateScripts generateScripts = new GenerateScripts("MOS");
//        generateScripts.generateScripts();
        generateScripts.generateTestAll();

    }

    public void generateScripts() {
        Map<String, Mutant> mutants = new MutantsSet(objectName).getMutants();

        for (Map.Entry<String, Mutant> entry : mutants.entrySet()) {
            String mutantName = entry.getKey();
            System.out.println("正在写" + mutantName);
            String content = getContent(mutantName);
            writeScripts(objectName, mutantName, content);
        }
    }

    private String getContent(String mutantName) {
        String className = mutantName + "Test";
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append("package " + objectName + ";\n");
        stringBuffer.append("import junit.framework.TestCase;\n");
        stringBuffer.append("import org.junit.Before;\n");
        stringBuffer.append("import org.junit.Test;\n");
        stringBuffer.append("import labprograms.util.WriteTestingResult;\n");
        stringBuffer.append("import labprograms.testCase.TestCase4ACMS;\n");
        stringBuffer.append("import labprograms.testCase.TestCase4CUBS;\n");
        stringBuffer.append("import labprograms.testCase.TestCase4ERS;\n");
        stringBuffer.append("import labprograms.testCase.TestCase4MOS;\n");
        stringBuffer.append("import java.util.List;\n");
        stringBuffer.append("import java.util.ArrayList;\n");
        stringBuffer.append("import java.util.Random;\n");
        stringBuffer.append("import labprograms.constant.Constant;\n");
        if (objectName.equals("MOS")) {
            stringBuffer.append("import labprograms.MT4MOS.sourceCode.MSR;\n");
        }

        stringBuffer.append("public class " + mutantName + "Test extends TestCase{\n");
        if (objectName.equals("ACMS")) {
            stringBuffer.append("\tprivate List<TestCase4ACMS> testcases;\n");
            stringBuffer.append("\tlabprograms.ACMS.sourceCode.AirlinesBaggageBillingService source = " +
                    "new labprograms.ACMS.sourceCode.AirlinesBaggageBillingService();\n");
        } else if (objectName.equals("CUBS")) {
            stringBuffer.append("\tprivate List<TestCase4CUBS> testcases;\n");
            stringBuffer.append("\tlabprograms.CUBS.sourceCode.BillCalculation source = " +
                    "new labprograms.CUBS.sourceCode.BillCalculation();\n");
        } else if (objectName.equals("ERS")) {
            stringBuffer.append("\tprivate List<TestCase4ERS> testcases;\n");
            stringBuffer.append("\tlabprograms.ERS.sourceCode.ExpenseReimbursementSystem source = " +
                    "new labprograms.ERS.sourceCode.ExpenseReimbursementSystem();\n");
        } else {
            stringBuffer.append("\tprivate List<TestCase4MOS> testcases;\n");
            stringBuffer.append("\tlabprograms.MT4MOS.sourceCode.MealOrderingSystem source = " +
                    "new labprograms.MT4MOS.sourceCode.MealOrderingSystem();\n");
        }
        stringBuffer.append("\tWriteTestingResult writeTestingResult = new WriteTestingResult();\n");
        stringBuffer.append("\t@Test\n");
        stringBuffer.append("\tpublic void test" + mutantName + "(){\n");
        stringBuffer.append("\t\tString mutantName = \"" + mutantName + "\";\n");
        stringBuffer.append("\t\ttestcases = new ArrayList<>();\n\t\tcreateTestCases();\n");
        stringBuffer.append("\t\tint count = 0;\n");

        if (objectName.equals("ACMS")) {
            stringBuffer.append("\t\tfor (TestCase4ACMS tc : testcases) {\n");
            stringBuffer.append("\t\t\tdouble sourceResult = source.feeCalculation(tc.getAirClass(),tc.getArea(),\n" +
                    "                      tc.isStudent(),tc.getLuggage(),tc.getEconomicfee());\n");
            stringBuffer.append("\t\t\tlabprograms.ACMS.mutants." + mutantName + ".AirlinesBaggageBillingService mutant = new labprograms.ACMS.mutants." +
                    mutantName + ".AirlinesBaggageBillingService();\n");
            stringBuffer.append("\t\t\tdouble mutantResult = mutant.feeCalculation(tc.getAirClass(),tc.getArea(),\n" +
                    "                    tc.isStudent(),tc.getLuggage(),tc.getEconomicfee());\n");
            stringBuffer.append("\t\t\tif (sourceResult == mutantResult){\n\t\t\t\tcontinue;\n\t\t\t}else {\n\t\t\t\tcount++;\n" +
                    "\t\t\t}\n\t\t}\n\t\twriteTestingResult.write(\"ACMS\", mutantName,\" \",String.valueOf(count));\n\t}\n");
        } else if (objectName.equals("CUBS")) {
            stringBuffer.append("\t\tfor (TestCase4CUBS tc : testcases) {\n");
            stringBuffer.append("\t\t\tdouble sourceResult = source.phoneBillCalculation(tc.getPlanType(), tc.getPlanFee(), tc.getTalkTime(), tc.getFlow());\n");
            stringBuffer.append("\t\t\tlabprograms.CUBS.mutants." + mutantName + ".BillCalculation mutant = new labprograms.CUBS.mutants." +
                    mutantName + ".BillCalculation();\n");
            stringBuffer.append("\t\t\tdouble mutantResult = mutant.phoneBillCalculation(tc.getPlanType(), tc.getPlanFee(), tc.getTalkTime(), tc.getFlow());\n");
            stringBuffer.append("\t\t\tif (sourceResult == mutantResult){\n\t\t\t\tcontinue;\n\t\t\t}else {\n\t\t\t\tcount++;\n" +
                    "\t\t\t}\n\t\t}\n\t\twriteTestingResult.write(\"CUBS\", mutantName,\" \",String.valueOf(count));\n\t}\n");
        } else if (objectName.equals("ERS")) {
            stringBuffer.append("\t\tfor (TestCase4ERS tc : testcases) {\n");
            stringBuffer.append("\t\t\tdouble sourceResult = source.calculateReimbursementAmount(tc.getStafflevel(), tc.getActualmonthlymileage(), tc.getMonthlysalesamount(), tc.getAirfareamount(), tc.getOtherexpensesamount());\n");
            stringBuffer.append("\t\t\tlabprograms.ERS.mutants." + mutantName + ".ExpenseReimbursementSystem mutant = new labprograms.ERS.mutants." +
                    mutantName + ".ExpenseReimbursementSystem();\n");
            stringBuffer.append("\t\t\tdouble mutantResult = mutant.calculateReimbursementAmount(tc.getStafflevel(), tc.getActualmonthlymileage(), tc.getMonthlysalesamount(), tc.getAirfareamount(), tc.getOtherexpensesamount());\n");
            stringBuffer.append("\t\t\tif (sourceResult == mutantResult){\n\t\t\t\tcontinue;\n\t\t\t}else {\n\t\t\t\tcount++;\n" +
                    "\t\t\t}\n\t\t}\n\t\twriteTestingResult.write(\"ERS\", mutantName,\" \",String.valueOf(count));\n\t}\n");
        } else {
            stringBuffer.append("\t\tfor (TestCase4MOS tc : testcases) {\n" +
                    "\t\t\t MSR sourceResult = source.generateMSR(tc.getAircraftmodel(), tc.getChangeinthenumberofcrewmembers(), tc.getNewnumberofcrewmembers(), tc.getChangeinthenumberofpilots(), tc.getNewnumberofpilots(), " +
                    "tc.getNumberofchildpassengers(), tc.getNumberofrequestedbundlesofflowers());\n" +
                    "\t\t\tlabprograms.MT4MOS.mutants." + mutantName + ".MealOrderingSystem mutant = new labprograms.MT4MOS.mutants." + mutantName + ".MealOrderingSystem();\n" +
                    "\t\t\tMSR mutantResult = mutant.generateMSR(tc.getAircraftmodel(), tc.getChangeinthenumberofcrewmembers(), tc.getNewnumberofcrewmembers(), tc.getChangeinthenumberofpilots(), tc.getNewnumberofpilots(), tc.getNumberofchildpassengers(), tc.getNumberofrequestedbundlesofflowers());\n" +
                    "\t\t\tif (sourceResult.numberOfBundlesOfFlowers == mutantResult.numberOfBundlesOfFlowers &&" +
                    "sourceResult.numberOfBusinessClassMeals == mutantResult.numberOfBusinessClassMeals &&" +
                    "sourceResult.numberOfChildMeals == mutantResult.numberOfChildMeals &&" +
                    "sourceResult.numberOfEconomicClassMeals == mutantResult.numberOfEconomicClassMeals &&" +
                    "sourceResult.numberOfFirstClassMeals == mutantResult.numberOfFirstClassMeals &&" +
                    "sourceResult.numberOfMealsForCrewMembers == mutantResult.numberOfMealsForCrewMembers &&" +
                    "sourceResult.numberOfMealsForPilots == mutantResult.numberOfMealsForPilots){\n\t\t\t\tcontinue;\n\t\t\t}else {\n\t\t\t\tcount++;\n\t\t\t}\n\t\t}\n\t\twriteTestingResult.write(\"MT4MOS\", mutantName,\" \",String.valueOf(count));\n\t}\n");
        }

        stringBuffer.append("\tprivate void createTestCases(){\n\t\tConstant constant = new Constant();\n\t\tRandom random = new Random(0);\n");

        if (objectName.equals("ACMS")) {
            stringBuffer.append("\t\tBoolean[] ISSTUDENT = {true, false};\n\t\t\tfor (int i = 0; i < constant.number; i++) {\n"
                    + "\t\t\tboolean isStudent = ISSTUDENT[random.nextInt(2)];\n\t\t\tint airClass = 0;\n\t\t\tif (isStudent){\n\t\t\t\tairClass = 2;\n" +
                    "\t\t\t}else {\n\t\t\t\tairClass = random.nextInt(4);\n\t\t\t}\n\t\t\tint area = random.nextInt(2);\n" +
                    "\t\t\tdouble luggage = random.nextDouble() * 80;\n\t\t\tdouble economicfee = random.nextDouble() * 3000;\n" +
                    "\t\t\tTestCase4ACMS tc = new TestCase4ACMS(airClass, area, isStudent, luggage, economicfee);\n" +
                    "\t\t\ttestcases.add(tc);\n\t\t}\n\t}\n}");
        } else if (objectName.equals("CUBS")) {
            stringBuffer.append("\t\tString[] types = {\"A\", \"B\", \"a\", \"b\"};\n\t\tint[] Achoices = {46, 96, 286, 886};\n" +
                    "\t\tint[] Bchoices = {46, 96, 126, 186};\n\t\tfor (int i = 0; i < constant.number; i++) {\n" +
                    "\t\t\tString planType = types[random.nextInt(4)];\n\t\t\tint planFee = 0;\n\t\t\tif (planType == \"A\" || planType == \"a\"){\n" +
                    "\t\t\t\tplanFee = Achoices[random.nextInt(4)];\n\t\t\t}else {\n\t\t\t\tplanFee = Bchoices[random.nextInt(4)];\n\t\t\t}\n" +
                    "\t\t\tint talkTime = random.nextInt(4000);\n\t\t\tint flow = random.nextInt(4000);\n\t\t\tTestCase4CUBS tc = new TestCase4CUBS(planType,planFee,talkTime,flow);\n" +
                    "\t\t\ttestcases.add(tc);\n\t\t}\n\t}\n}");
        } else if (objectName.equals("ERS")) {
            stringBuffer.append("\t\tString[] levels = {\"seniormanager\", \"manager\", \"supervisor\"};\n" +
                    "\t\tfor (int i = 0; i < constant.number; i++) {\n" +
                    "\t\t\tString stafflevel = levels[random.nextInt(3)];\n" +
                    "\t\t\tdouble actualmonthlymileage = random.nextDouble() * 8000;\n" +
                    "\t\t\tdouble monthlysalesamount = random.nextDouble() * 150000;\n" +
                    "\t\t\tdouble airfareamount = random.nextDouble() * 10000;\n" +
                    "\t\t\tdouble otherexpensesamount = random.nextDouble() * 10000;\n" +
                    "\t\t\tTestCase4ERS tc = new TestCase4ERS(stafflevel, actualmonthlymileage,\n" +
                    "                    monthlysalesamount,airfareamount, otherexpensesamount);\n" +
                    "\t\t\ttestcases.add(tc);\n\t\t}\n" +
                    "\t}\n}");
        } else {
            stringBuffer.append("\t\tString[] models = {\"747200\", \"747300\", \"747400\", \"000200\", \"000300\"};\n" +
                    "\t\tString[] changenumbers = {\"y\", \"n\"};\n" +
                    "\t\tString[] changpilots = {\"y\", \"n\"};\n" +
                    "\t\tfor (int i = 0; i < constant.number; i++) {\n" +
                    "\t\t\tString aircraftmodel = models[random.nextInt(4)];\n" +
                    "\t\t\tString changeinthenumberofcrewmembers = changenumbers[random.nextInt(2)];\n" +
                    "\t\t\tint newnumberofcrewmembers = random.nextInt(20);\n" +
                    "\t\t\tString changeinthenumberofpilots = changpilots[random.nextInt(2)];\n" +
                    "\t\t\tint newnumberofpilots = random.nextInt(5);\n" +
                    "\t\t\tint numberofchildpassengers = random.nextInt(15);\n" +
                    "\t\t\tint numberofrequestedbundlesofflowers = random.nextInt(500);\n" +
                    "\t\t\tTestCase4MOS tc = new TestCase4MOS(aircraftmodel, changeinthenumberofcrewmembers, newnumberofcrewmembers,\n" +
                    "                    changeinthenumberofpilots, newnumberofpilots, numberofchildpassengers, numberofrequestedbundlesofflowers);\n" +
                    "\t\t\ttestcases.add(tc);\n\t\t}\n" +
                    "\t}\n}");

        }
        return stringBuffer.toString();
    }

    private void writeScripts(String objectName, String mutantName, String content) {
        String path = System.getProperty("user.dir") + separator + "src" + separator + "test" +
                separator + "java" + separator + objectName + separator + mutantName + "Test.java";

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.write(content);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateTestAll() {
        String className = objectName + "TestAll";
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append("import junit.framework.Test;\n");
        stringBuffer.append("import junit.framework.TestSuite;\n");
        stringBuffer.append("public class " + className + " {\n");
        stringBuffer.append("\tpublic static Test suite(){\n");
        stringBuffer.append("\t\tTestSuite testSuite = new TestSuite(\"test all\");\n");
        //load test cases
        Map<String, Mutant> mutants = new MutantsSet(objectName).getMutants();
        for (Map.Entry<String, Mutant> entry : mutants.entrySet()) {
            String mutantName = entry.getKey();
            stringBuffer.append("\t\ttestSuite.addTestSuite(" + objectName + "." + mutantName +
                    "Test.class);\n");
        }
        stringBuffer.append("\t\treturn testSuite;\n\t}\n");
        stringBuffer.append("\tpublic static void main(String[] args) {\n");
        stringBuffer.append("\t\tjunit.textui.TestRunner.run(" + objectName + "TestAll.suite());\n\t}\n}");
        String path = System.getProperty("user.dir") + separator + "src" + separator + "test" +
                separator + "java" + separator + className + ".java";
        File file = new File(path);
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.write(stringBuffer.toString());
            stringBuffer.delete(0, stringBuffer.length());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
