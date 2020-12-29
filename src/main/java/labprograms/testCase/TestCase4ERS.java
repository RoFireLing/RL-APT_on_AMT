package labprograms.testCase;

/**
 * describe:
 * test case
 *
 * @author phantom
 * @date 2019/04/17
 */

public class TestCase4ERS {
    private String stafflevel;
    private double actualmonthlymileage;
    private double monthlysalesamount;
    private double airfareamount;
    private double otherexpensesamount;

    public TestCase4ERS(String stafflevel, double actualmonthlymileage, double monthlysalesamount,
                        double airfareamount, double otherexpensesamount) {
        setStafflevel(stafflevel);
        setActualmonthlymileage(actualmonthlymileage);
        setMonthlysalesamount(monthlysalesamount);
        setAirfareamount(airfareamount);
        setOtherexpensesamount(otherexpensesamount);
    }


    public String getStafflevel() {
        return stafflevel;
    }

    public void setStafflevel(String stafflevel) {
        this.stafflevel = stafflevel;
    }

    public double getActualmonthlymileage() {
        return actualmonthlymileage;
    }

    public void setActualmonthlymileage(double actualmonthlymileage) {
        this.actualmonthlymileage = actualmonthlymileage;
    }

    public double getMonthlysalesamount() {
        return monthlysalesamount;
    }

    public void setMonthlysalesamount(double monthlysalesamount) {
        this.monthlysalesamount = monthlysalesamount;
    }

    public double getAirfareamount() {
        return airfareamount;
    }

    public void setAirfareamount(double airfareamount) {
        this.airfareamount = airfareamount;
    }

    public double getOtherexpensesamount() {
        return otherexpensesamount;
    }

    public void setOtherexpensesamount(double otherexpensesamount) {
        this.otherexpensesamount = otherexpensesamount;
    }
}
