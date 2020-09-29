package labprograms.newStrategy;

import labprograms.ACMS.sourceCode.AirlinesBaggageBillingService;
import labprograms.CUBS.sourceCode.BillCalculation;
import labprograms.ERS.sourceCode.ExpenseReimbursementSystem;
import labprograms.MOS.sourceCode.MSR;
import labprograms.MOS.sourceCode.MealOrderingSystem;
import labprograms.constant.Constant;
import labprograms.log.ResultRecorder;
import labprograms.method.Methods4Testing;
import labprograms.mutants.Mutant;
import labprograms.mutants.UsedMutantsSet;
import labprograms.newStrategy.utl.GetMRInfo;
import labprograms.newStrategy.utl.InstantiationTestFrame;
import labprograms.newStrategy.utl.RLAPT_MAPT;
import labprograms.result.RecordResult;
import labprograms.strategies.util.*;
import labprograms.testCase.TestCase4ACMS;
import labprograms.testCase.TestCase4CUBS;
import labprograms.testCase.TestCase4ERS;
import labprograms.testCase.TestCase4MOS;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RLAPT_mapt implements NewStrategy {
    public static void main(String[] args) {
        RLAPT_mapt mt = new RLAPT_mapt();
        String[] names = {"ACMS"};
        for (int i = 0; i < 10; i++) {
            for (String name : names) {
                mt.testing(name, i);
            }
        }
    }

    @Override
    public void testing(String objectName, int repeatTimes) {

        //建立分区与测试用例之间的关系
        TestCasesOfPartition testCasesOfPartition = new TestCasesOfPartition(objectName);
        //实例化control类
        Control control = new Control(objectName);

        //记录时间的对象
        TimeRecorder timeRecorder = new TimeRecorder();

        //记录metrics值的对象
        MeasureRecorder measureRecorder = new MeasureRecorder();

        //获得ＭＲ的信息
        Map<String, String> mrInfo = GetMRInfo.getMRinfo(objectName);

        for (int i = 0; i < Constant.repeatNumber; i++) {
            System.out.println("RL-APT4" + objectName + "使用mapt:" +
                    "执行第" + String.valueOf(i + 1) + "次测试：");

            //初始化RL APT
//            MAPT mapt = new MAPT();
            RLAPT_MAPT mapt = new RLAPT_MAPT();

            //初始化测试剖面
            mapt.initializeMAPT(Constant.getPartitionNumber(objectName));


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

            for (int j = 0; j < 10000; j++) {
                //计数器自增
                counter++;

                /**开始选择分区和测试用例*/
                long startSelectTestCase = System.nanoTime();
                //选择分区
                if (counter == 1) {
                    partitionIndex = new Random().
                            nextInt(Constant.getPartitionNumber(objectName));
                } else {
                    partitionIndex = mapt.nextPartition4MAPT(partitionIndex);
                }


                //选择测试用例
                String testframesAndMr = testCasesOfPartition.
                        getSourceFollowAndMR(partitionIndex);
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
                            mutantMethod = mutantClazz.getMethod(methodName, int.class, int.class,
                                    boolean.class, double.class, double.class);

                            // 产生测试用例
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = InstantiationTestFrame.instantiation(objectName,
                                    testframesAndMr.split(";")[0]);
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
                            sourceResult = (double) mutantMethod.invoke(mutantInstance,
                                    sourceTestCase.getAirClass(), sourceTestCase.getArea(),
                                    sourceTestCase.isStudent(), sourceTestCase.getLuggage(),
                                    sourceTestCase.getEconomicfee());
                            expectedResult = new AirlinesBaggageBillingService().feeCalculation(sourceTestCase.getAirClass(), sourceTestCase.getArea(),
                                    sourceTestCase.isStudent(), sourceTestCase.getLuggage(),
                                    sourceTestCase.getEconomicfee());
                            long endExecuteTestCase = System.nanoTime();

                            //　记录测试用例的执行时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            }

                            //判断结果是否违反ＭＲ
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
                        } else if (objectName.equals("CUBS")) {
                            double sourceResult = 0;
                            double expectedResult = 0;
                            mutantMethod = mutantClazz.getMethod(methodName, String.class, int.class,
                                    int.class, int.class);

                            //产生测试用例
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = InstantiationTestFrame.instantiation(objectName,
                                    testframesAndMr.split(";")[0]);
                            TestCase4CUBS sourceTestCase = (TestCase4CUBS) stc;
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
                            expectedResult = new BillCalculation().phoneBillCalculation(sourceTestCase.getPlanType(),
                                    sourceTestCase.getPlanFee(), sourceTestCase.getTalkTime(), sourceTestCase.getFlow());
                            long endExecuteTestCase = System.nanoTime();

                            //记录测试用例执行需要的时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            }

                            if (sourceResult != expectedResult) {
                                //检测出故障
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    onceMeasureRecord.FmeasurePlus(counter);
                                }

                                if (killedMutants.size() == 1) {
                                    onceMeasureRecord.F2measurePlus(counter -
                                            onceMeasureRecord.getFmeasure());
                                }
                                killedMutants.add(entry.getKey());
                            }
                        } else if (objectName.equals("ERS")) {
                            double sourceResult = 0;
                            double expectedResult = 0;
                            mutantMethod = mutantClazz.getMethod(methodName, String.class, double.class,
                                    double.class, double.class, double.class);
                            ExpenseReimbursementSystem ers = new ExpenseReimbursementSystem();
                            Class sourceClazz = ers.getClass();
                            Constructor sourceConstructor = sourceClazz.getConstructor();
                            Object sourceInstance = sourceConstructor.newInstance();
                            Method sourceMethod = sourceClazz.getMethod(methodName, String.class, double.class,
                                    double.class, double.class, double.class);

                            //产生测试用例
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = InstantiationTestFrame.instantiation(objectName,
                                    testframesAndMr.split(";")[0]);
                            TestCase4ERS sourceTestCase = (TestCase4ERS) stc;
                            long endGenerateTestCase = System.nanoTime();

                            //记录产生测试用例需要的时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                            }

                            //执行测试用例
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (double) mutantMethod.invoke(mutantInstance, sourceTestCase.getStafflevel(),
                                    sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
                                    sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());
                            expectedResult = (double) sourceMethod.invoke(sourceInstance, sourceTestCase.getStafflevel(),
                                    sourceTestCase.getActualmonthlymileage(), sourceTestCase.getMonthlysalesamount(),
                                    sourceTestCase.getAirfareamount(), sourceTestCase.getOtherexpensesamount());
                            long endExecuteTestCase = System.nanoTime();

                            //记录执行测试用例需要的时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            }

                            if (sourceResult != expectedResult) {
                                //检测出故障
                                isKilledMutants = true;
                                if (killedMutants.size() == 0) {
                                    onceMeasureRecord.FmeasurePlus(counter);
                                }
                                if (killedMutants.size() == 1) {
                                    onceMeasureRecord.F2measurePlus(counter -
                                            onceMeasureRecord.getFmeasure());
                                }
                                killedMutants.add(entry.getKey());
                            }
                        } else {
                            MSR sourceResult = null;
                            MSR expectedResult = null;
                            mutantMethod = mutantClazz.getMethod(methodName, String.class, String.class, int.class,
                                    String.class, int.class, int.class, int.class);

                            // 产生测试用例
                            long startGenerateTestCase = System.nanoTime();
                            Object stc = InstantiationTestFrame.instantiation(objectName,
                                    testframesAndMr.split(";")[0]);
                            TestCase4MOS sourceTestCase = (TestCase4MOS) stc;
                            long endGenerateTestCase = System.nanoTime();

                            //记录产生测试用例的时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondGeneratingTimePlus(endGenerateTestCase - startGenerateTestCase);
                            }

                            //执行测试用例
                            long startExecuteTestCase = System.nanoTime();
                            sourceResult = (MSR) mutantMethod.invoke(mutantInstance, sourceTestCase.getAircraftmodel(),
                                    sourceTestCase.getChangeinthenumberofcrewmembers(), sourceTestCase.getNewnumberofcrewmembers(),
                                    sourceTestCase.getChangeinthenumberofpilots(), sourceTestCase.getNewnumberofpilots(),
                                    sourceTestCase.getNumberofchildpassengers(), sourceTestCase.getNumberofrequestedbundlesofflowers());
                            expectedResult = new MealOrderingSystem().generateMSR(sourceTestCase.getAircraftmodel(),
                                    sourceTestCase.getChangeinthenumberofcrewmembers(), sourceTestCase.getNewnumberofcrewmembers(),
                                    sourceTestCase.getChangeinthenumberofpilots(), sourceTestCase.getNewnumberofpilots(),
                                    sourceTestCase.getNumberofchildpassengers(), sourceTestCase.getNumberofrequestedbundlesofflowers());
                            long endExecuteTestCase = System.nanoTime();

                            // 记录执行测试用例需要的时间
                            if (killedMutants.size() == 0) {
                                onceTimeRecord.firstExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            } else if (killedMutants.size() == 1) {
                                onceTimeRecord.secondExecutingTime(endExecuteTestCase - startExecuteTestCase);
                            }

//                            String resultRelation = GetResultRelation4MOS.getResultRelation(sourceResult,followUpResult);
                            if (!sourceResult.equals(expectedResult)) {
                                //检测出故障
                                isKilledMutants = true;

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
                //根据测试结果调整测试剖面
                mapt.adjustMAPT(partitionIndex, isKilledMutants);

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
        String txtLogName = "RLAPT_mapt4" + objectName + ".txt";
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

        String excelLogName = "RLAPT_mapt4" + objectName + ".xlsx";
        resultRecorder.writeResult(excelLogName, repeatTimes);
    }
}