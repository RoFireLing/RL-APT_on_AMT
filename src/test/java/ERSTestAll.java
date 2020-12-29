import junit.framework.Test;
import junit.framework.TestSuite;

public class ERSTestAll {
    public static Test suite() {
        TestSuite testSuite = new TestSuite("test all");
        testSuite.addTestSuite(ERS.SDL_27Test.class);
        return testSuite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ERSTestAll.suite());
    }
}