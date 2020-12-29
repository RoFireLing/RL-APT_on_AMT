package labprograms.executingtest;

import labprograms.constant.Constant;
import labprograms.log.ResultRecorder;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.UsedMutantsSet;
import labprograms.newStrategy.utl.GetMRInfo;
import labprograms.newStrategy.utl.InstantiationTestFrame;
import labprograms.newStrategy.utl.ObtainMRFromOneLine;
import labprograms.result.RecordResult;
import labprograms.strategies.util.MeasureRecorder;
import labprograms.strategies.util.OnceMeasureRecord;
import labprograms.strategies.util.OnceTimeRecord;
import labprograms.strategies.util.TimeRecorder;
import labprograms.testCase.TestCase4CUBS;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @Description:
 * @auther phantom
 * @create 2019-09-18 下午5:58
 */
public class TestingCUBS implements TestingObject {
    private String objectName = "CUBS";


    @Override
    public void executeTestCase(int repeatTimes, String tester) {
        //记录时间的对象
        TimeRecorder timeRecorder = new TimeRecorder();

        //记录metrics值的对象
        MeasureRecorder measureRecorder = new MeasureRecorder();

        //获得ＭＲ的信息
        Map<String, String> mrInfo = GetMRInfo.getMRinfo(objectName);

        //开始执行测试
        for (int i = 0; i < Constant.repeatNumber; i++) {
            System.out.println(tester + "4" + objectName + ":" + "执行第" + String.valueOf(i + 1) + "次测试：");
            //获得变异体集合
            UsedMutantsSet mutantsSet = new UsedMutantsSet(objectName);
            Map<String, Mutant> mutantMap = mutantsSet.getMutants();

            //初始化一个存放杀死的变异体的集合
            Set<String> killedMutants = new HashSet<>();

            //获得待测程序的待测方法名
            String methodName = new Methods4Testing(objectName).getMethodName();

            //初始化一个记录执行的测试用例数目的对象
            int counter = 0;

            //初始化记录时间的对象
            OnceTimeRecord onceTimeRecord = new OnceTimeRecord();

            //初始化记录度量标准值的对象
            OnceMeasureRecord onceMeasureRecord = new OnceMeasureRecord();

            for (int j = 0; j < 10000; j++) {

                //开始选择测试用例
                long startSelectTestCase = System.nanoTime();
                int index = new Random().nextInt(mrInfo.size()) + 1;
                //计数器自增
                counter++;
                String testframesAndMr = mrInfo.get(String.valueOf(index));
                //测试用例选择结束
                long endSelectTestCase = System.nanoTime();
                //记录选择测试用例需要的时间
                if (killedMutants.size() == 0) {
                    onceTimeRecord.firstSelectionTimePlus(endSelectTestCase - startSelectTestCase);
                } else if (killedMutants.size() == 1) {
                    onceTimeRecord.secondSelectionTimePlus(endSelectTestCase - startSelectTestCase);
                }

                //获得ＭＲ
                String MR = ObtainMRFromOneLine.getMR(objectName, testframesAndMr);

                //遍历变异体
                for (Map.Entry<String, Mutant> entry : mutantMap.entrySet()) {
                    if (killedMutants.contains(entry.getKey())) {
                        continue;
                    }
                    Mutant mutant = entry.getValue();
                    Object mutantInstance = null;
                    Method mutantMethod = null;
                    Class mutantClazz = null;

                    try {
                        mutantClazz = Class.forName(mutant.getFullName());
                        Constructor mutantConstructor = mutantClazz.getConstructor();
                        mutantInstance = mutantConstructor.newInstance();

                        double sourceResult = 0;
                        double followUpResult = 0;
                        mutantMethod = mutantClazz.getMethod(methodName, String.class, int.class,
                                int.class, int.class);

                        //产生测试用例
                        long startGenerateTestCase = System.nanoTime();

                        Object stc = InstantiationTestFrame.instantiation(objectName,
                                testframesAndMr.split(";")[0]);
                        Object ftc = InstantiationTestFrame.instantiation(objectName,
                                testframesAndMr.split(";")[1]);

                        TestCase4CUBS sourceTestCase = (TestCase4CUBS) stc;
                        TestCase4CUBS followUpTestCase = (TestCase4CUBS) ftc;
                        long endGenerateTestCase = System.nanoTime();

                        //记录产生测试用例的时间
                        if (killedMutants.size() == 0) {
                            onceTimeRecord.firstGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                        } else if (killedMutants.size() == 1) {
                            onceTimeRecord.secondGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                        }

                        //执行测试用例
                        long startExecuteTestCase = System.nanoTime();
                        sourceResult = (double) mutantMethod.invoke(mutantInstance,
                                sourceTestCase.getPlanType(),
                                sourceTestCase.getPlanFee(), sourceTestCase.getTalkTime(), sourceTestCase.getFlow());

                        followUpResult = (double) mutantMethod.invoke(mutantInstance,
                                followUpTestCase.getPlanType(),
                                followUpTestCase.getPlanFee(), followUpTestCase.getTalkTime(), followUpTestCase.getFlow());
                        long endExecuteTestCase = System.nanoTime();

                        //记录测试用例执行需要的时间
                        if (killedMutants.size() == 0) {
                            onceTimeRecord.firstExecutingTime(endExecuteTestCase - startExecuteTestCase);
                        } else if (killedMutants.size() == 1) {
                            onceTimeRecord.secondExecutingTime(endExecuteTestCase - startExecuteTestCase);
                        }

                        if (MR.equals("The output will not change") && sourceResult != followUpResult) {
                            if (killedMutants.size() == 0) {
                                onceMeasureRecord.FmeasurePlus(counter * 2);

                            }

                            if (killedMutants.size() == 1) {
                                onceMeasureRecord.F2measurePlus(counter * 2 -
                                        onceMeasureRecord.getFmeasure());
                            }
                            killedMutants.add(entry.getKey());
                        }

                        if (MR.equals("The output will increase") && sourceResult >= followUpResult) {
                            if (killedMutants.size() == 0) {
                                onceMeasureRecord.FmeasurePlus(counter * 2);
                            }
                            if (killedMutants.size() == 1) {
                                onceMeasureRecord.F2measurePlus(counter * 2 -
                                        onceMeasureRecord.getFmeasure());
                            }
                            killedMutants.add(entry.getKey());
                        }

                        if (MR.equals("The output will decrease") && sourceResult <= followUpResult) {
                            if (killedMutants.size() == 0) {
                                onceMeasureRecord.FmeasurePlus(counter * 2);
                            }
                            if (killedMutants.size() == 1) {
                                onceMeasureRecord.F2measurePlus(counter * 2 -
                                        onceMeasureRecord.getFmeasure());
                            }
                            killedMutants.add(entry.getKey());
                        }
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }


            //记录Ｆ和Ｆ２的值
            measureRecorder.addFMeasure(onceMeasureRecord.getFmeasure());
            measureRecorder.addF2Measure(onceMeasureRecord.getF2measure());

            //记录相应的测试用例选择、生成和执行的时间
            timeRecorder.addFirstSelectTestCase(onceTimeRecord.getFirstSelectingTime());
            timeRecorder.addFirstGenerateTestCase(onceTimeRecord.getFirstGeneratingTime());
            timeRecorder.addFirstExecuteTestCase(onceTimeRecord.getFirstExecutingTime());
            timeRecorder.addSecondSelectTestCase(onceTimeRecord.getSecondSelectingTime());
            timeRecorder.addSecondGenerateTestCase(onceTimeRecord.getSecondGeneratingTime());
            timeRecorder.addSecondExecuteTestCase(onceTimeRecord.getSecondExecutingTime());
        }
        //记录均值结果方便查看
        String txtLogName = tester + "4" + objectName + ".txt";
        RecordResult.recordResult(txtLogName, repeatTimes, measureRecorder.getAverageFmeasure(),
                measureRecorder.getAverageF2measure());

        //记录详细的实验结果
        ResultRecorder resultRecorder = new ResultRecorder();
        resultRecorder.initializeMeasureArray(measureRecorder.getFmeasureArray(), measureRecorder.getF2measureArray());
        resultRecorder.initializeMeasureAverageAndVariance(measureRecorder.getAverageFmeasure(), measureRecorder.getAverageF2measure(),
                measureRecorder.getVarianceFmeasure(), measureRecorder.getVarianceF2measure());

        resultRecorder.getTimeArray(timeRecorder.getFirstSelectTestCaseArray(), timeRecorder.getFirstGenerateTestCaseArray(),
                timeRecorder.getFirstExecuteTestCaseArray(), timeRecorder.getSecondSelectTestCaseArray(),
                timeRecorder.getSecondGenerateTestCaseArray(), timeRecorder.getSecondExecuteTestCaseArray());

        resultRecorder.getTimeAverage(timeRecorder.getAverageSelectFirstTestCaseTime(), timeRecorder.getAverageGenerateFirstTestCaseTime(),
                timeRecorder.getAverageExecuteFirstTestCaseTime(), timeRecorder.getAverageSelectSecondTestCaseTime(),
                timeRecorder.getAverageGenerateSecondTestCaseTime(), timeRecorder.getAverageExecuteSecondTestCaseTime());

        resultRecorder.getTimeVariance(timeRecorder.getVarianceSelectFirstTestCaseTime(), timeRecorder.getVarianceGenerateFirstTestCaseTime(),
                timeRecorder.getVarianceExecuteFirstTestCaseTime(), timeRecorder.getVarianceSelectSecondTestCaseTime(),
                timeRecorder.getVarianceGenerateSecondTestCaseTime(), timeRecorder.getVarianceExecuteSecondTestCaseTime());

        String excelLogName = tester + "4" + objectName + ".xlsx";
        resultRecorder.writeResult(excelLogName, repeatTimes);

    }
}
