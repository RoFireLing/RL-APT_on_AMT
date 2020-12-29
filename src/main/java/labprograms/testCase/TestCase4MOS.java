package labprograms.testCase;

/**
 * describe:
 * test case
 *
 * @author phantom
 * @date 2019/04/18
 */

public class TestCase4MOS {
    private String aircraftmodel;
    private String changeinthenumberofcrewmembers;
    private int newnumberofcrewmembers;
    private String changeinthenumberofpilots;
    private int newnumberofpilots;
    private int numberofchildpassengers;
    private int numberofrequestedbundlesofflowers;

    public TestCase4MOS(String aircraftmodel, String changeinthenumberofcrewmembers,
                        int newnumberofcrewmembers, String changeinthenumberofpilots,
                        int newnumberofpilots, int numberofchildpassengers,
                        int numberofrequestedbundlesofflowers) {
        setAircraftmodel(aircraftmodel);
        setChangeinthenumberofcrewmembers(changeinthenumberofcrewmembers);
        setNewnumberofcrewmembers(newnumberofcrewmembers);
        setChangeinthenumberofpilots(changeinthenumberofpilots);
        setNewnumberofpilots(newnumberofpilots);
        setNumberofchildpassengers(numberofchildpassengers);
        setNumberofrequestedbundlesofflowers(numberofrequestedbundlesofflowers);
    }


    public String getAircraftmodel() {
        return aircraftmodel;
    }

    public void setAircraftmodel(String aircraftmodel) {
        this.aircraftmodel = aircraftmodel;
    }

    public String getChangeinthenumberofcrewmembers() {
        return changeinthenumberofcrewmembers;
    }

    public void setChangeinthenumberofcrewmembers(String changeinthenumberofcrewmembers) {
        this.changeinthenumberofcrewmembers = changeinthenumberofcrewmembers;
    }

    public int getNewnumberofcrewmembers() {
        return newnumberofcrewmembers;
    }

    public void setNewnumberofcrewmembers(int newnumberofcrewmembers) {
        this.newnumberofcrewmembers = newnumberofcrewmembers;
    }

    public String getChangeinthenumberofpilots() {
        return changeinthenumberofpilots;
    }

    public void setChangeinthenumberofpilots(String changeinthenumberofpilots) {
        this.changeinthenumberofpilots = changeinthenumberofpilots;
    }

    public int getNewnumberofpilots() {
        return newnumberofpilots;
    }

    public void setNewnumberofpilots(int newnumberofpilots) {
        this.newnumberofpilots = newnumberofpilots;
    }

    public int getNumberofchildpassengers() {
        return numberofchildpassengers;
    }

    public void setNumberofchildpassengers(int numberofchildpassengers) {
        this.numberofchildpassengers = numberofchildpassengers;
    }

    public int getNumberofrequestedbundlesofflowers() {
        return numberofrequestedbundlesofflowers;
    }

    public void setNumberofrequestedbundlesofflowers(int numberofrequestedbundlesofflowers) {
        this.numberofrequestedbundlesofflowers = numberofrequestedbundlesofflowers;
    }
}
