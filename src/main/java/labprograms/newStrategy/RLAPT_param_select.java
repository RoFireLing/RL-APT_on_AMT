package labprograms.newStrategy;

import labprograms.ACMS.sourceCode.AirlinesBaggageBillingService;
import labprograms.constant.Constant;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.UsedMutantsSet;
import labprograms.newStrategy.utl.InstantiationTestFrame;
import labprograms.newStrategy.utl.RLAPT_param;
import labprograms.result.RecordResult;
import labprograms.strategies.util.*;
import labprograms.testCase.TestCase4ACMS;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RLAPT_param_select {
    public static void main(String[] args) {
        RLAPT_param_select param = new RLAPT_param_select();

        double alphaset[] = {1, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
        double gammaset[] = {1, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0};
        double r0set[] = {1, 5, 10, 15, 20};

        int i = 1;
        for (double alpha : alphaset) {
//            for (int i = 0; i < 20; i++) {
            param.testing("ACMS", i, alpha, 1, 1);
        }
        for (double gamma : gammaset) {
            param.testing("ACMS", i, 1, gamma, 1);
        }
        for (double r0 : r0set) {
            param.testing("ACMS", i, 1, 0.6, r0);
        }
//        }
    }

    public void testing(String objectName, int repeatTimes, double alpha, double gamma, double r0) {

        //建立分区与测试用例之间的关系
        TestCasesOfPartition testCasesOfPartition = new TestCasesOfPartition(objectName);

        //记录时间的对象
        TimeRecorder timeRecorder = new TimeRecorder();

        //记录metrics值的对象
        MeasureRecorder measureRecorder = new MeasureRecorder();

        for (int i = 0; i < 300; i++) {
            System.out.println("执行第" + String.valueOf(i + 1) + "次测试：");

            //初始化RL APT
            RLAPT_param rlapt = new RLAPT_param();

            //初始化测试剖面
            rlapt.initializeRLAPT(Constant.getPartitionNumber(objectName));

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

            //记录分区号的对象
            int partitionIndex = 0;
            //记录下一分区号的对象
            int nextPartitionIndex = 0;

            for (int j = 0; j < 10000; j++) {

                //计数器自增
                counter++;

                /**开始选择分区和测试用例*/
                long startSelectTestCase = System.nanoTime();
                //选择分区
                if (counter == 1) {
                    partitionIndex = new Random().nextInt(Constant.getPartitionNumber(objectName));
                } else {
                    partitionIndex = nextPartitionIndex;
                }
                //epsilon greedy策略选择下一分区
                nextPartitionIndex = rlapt.nextPartition4RLAPT(partitionIndex, counter);
                //选择测试用例
                String testframesAndMr = testCasesOfPartition.getSourceFollowAndMR(partitionIndex);
                long endSelectTestCase = System.nanoTime();

                //记录选择测试用例需要的时间
                if (killedMutants.size() == 0) {
                    onceTimeRecord.firstSelectionTimePlus(endSelectTestCase - startSelectTestCase);
                } else if (killedMutants.size() == 1) {
                    onceTimeRecord.secondSelectionTimePlus(endSelectTestCase - startSelectTestCase);
                }


                //标志位：表示测试用力是否杀死变异体
                boolean isKilledMutants = false;

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

                        if (objectName.equals("ACMS")) {
                            double sourceResult = 0;
                            double expectedResult = 0;
                            mutantMethod = mutantClazz.getMethod(methodName, int.class, int.class, boolean.class, double.class, double.class);

                            // 产生测试用例
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = InstantiationTestFrame.instantiation(objectName, testframesAndMr.split(";")[0]);
                            TestCase4ACMS sourceTestCase = (TestCase4ACMS) stc;
                            long endGenerateTestCase = System.nanoTime();

                            //记录测试用例的产生时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                            }

                            //　执行测试用例
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (double) mutantMethod.invoke(mutantInstance, sourceTestCase.getAirClass(), sourceTestCase.getArea(), sourceTestCase.isStudent(), sourceTestCase.getLuggage(), sourceTestCase.getEconomicfee());
                            expectedResult = new AirlinesBaggageBillingService().feeCalculation(sourceTestCase.getAirClass(), sourceTestCase.getArea(), sourceTestCase.isStudent(), sourceTestCase.getLuggage(), sourceTestCase.getEconomicfee());
                            long endExecuteTestCase = System.nanoTime();

                            //　记录测试用例的执行时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            }

                            //判断结果是否杀死变异体
                            if (sourceResult != expectedResult) {
                                //检测出故障
                                isKilledMutants = true;
                                //检测出第一个故障，记录此时的数据
                                if (killedMutants.size() == 0) {
                                    onceMeasureRecord.FmeasurePlus(counter);
                                }
                                if (killedMutants.size() == 1) {
                                    onceMeasureRecord.F2measurePlus(counter -
                                            onceMeasureRecord.getFmeasure());
                                }
                                killedMutants.add(entry.getKey());
                            }
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
                //根据测试结果调整Q table
                rlapt.adjustRLAPT_Q(partitionIndex, nextPartitionIndex, isKilledMutants, alpha, gamma, r0);
            }
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
        String txtLogName = "param4" + objectName + ".txt";
        double FT = timeRecorder.getAverageExecuteFirstTestCaseTime() + timeRecorder.getAverageGenerateFirstTestCaseTime() + timeRecorder.getAverageSelectFirstTestCaseTime();
        double F2T = timeRecorder.getAverageExecuteSecondTestCaseTime() + timeRecorder.getAverageGenerateSecondTestCaseTime() + timeRecorder.getAverageSelectSecondTestCaseTime();
        RecordResult.recordResult(txtLogName, repeatTimes, measureRecorder.getAverageFmeasure(),
                measureRecorder.getAverageF2measure(), FT, F2T, alpha, gamma, r0);

//        //记录详细的实验结果
//        ResultRecorder resultRecorder = new ResultRecorder();
//        resultRecorder.initializeMeasureArray(measureRecorder.getFmeasureArray(), measureRecorder.getF2measureArray());
//        resultRecorder.initializeMeasureAverageAndVariance(measureRecorder.getAverageFmeasure(), measureRecorder.getAverageF2measure(),
//                measureRecorder.getVarianceFmeasure(), measureRecorder.getVarianceF2measure());
//
//        resultRecorder.getTimeArray(timeRecorder.getFirstSelectTestCaseArray(), timeRecorder.getFirstGenerateTestCaseArray(),
//                timeRecorder.getFirstExecuteTestCaseArray(), timeRecorder.getSecondSelectTestCaseArray(),
//                timeRecorder.getSecondGenerateTestCaseArray(), timeRecorder.getSecondExecuteTestCaseArray());
//
//        resultRecorder.getTimeAverage(timeRecorder.getAverageSelectFirstTestCaseTime(), timeRecorder.getAverageGenerateFirstTestCaseTime(),
//                timeRecorder.getAverageExecuteFirstTestCaseTime(), timeRecorder.getAverageSelectSecondTestCaseTime(),
//                timeRecorder.getAverageGenerateSecondTestCaseTime(), timeRecorder.getAverageExecuteSecondTestCaseTime());
//
//        resultRecorder.getTimeVariance(timeRecorder.getVarianceSelectFirstTestCaseTime(), timeRecorder.getVarianceGenerateFirstTestCaseTime(),
//                timeRecorder.getVarianceExecuteFirstTestCaseTime(), timeRecorder.getVarianceSelectSecondTestCaseTime(),
//                timeRecorder.getVarianceGenerateSecondTestCaseTime(), timeRecorder.getVarianceExecuteSecondTestCaseTime());
//
//        String excelLogName = "param.xlsx";
//        resultRecorder.writeResult(excelLogName, repeatTimes);
    }
}
