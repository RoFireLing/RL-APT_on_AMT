package labprograms.testCase;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/07/22
 */
public class TestCase4ACMS4XML {
    private int airClass;
    private int area;
    private boolean isStudent;
    private double luggage;
    private double economicfee;
    private double expectedResult;
    /**
     * 该测试用例所在的分区
     */
    private int partition;
    /**
     * 组成该测试用例的options的组合
     */
    private String options;

    public TestCase4ACMS4XML(int airClass, int area, boolean isStudent,
                             double luggage, double economicfee,
                             double expectedResult, int partition,
                             String options) {
        this.airClass = airClass;
        this.area = area;
        this.isStudent = isStudent;
        this.luggage = luggage;
        this.economicfee = economicfee;
        this.expectedResult = expectedResult;
        this.partition = partition;
        this.options = options;
    }

    public int getAirClass() {
        return airClass;
    }

    public int getArea() {
        return area;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public double getLuggage() {
        return luggage;
    }

    public double getEconomicfee() {
        return economicfee;
    }

    public int getPartition() {
        return partition;
    }

    public String getOptions() {
        return options;
    }

    public double getExpectedResult() {
        return expectedResult;
    }

}
