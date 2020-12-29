import junit.framework.Test;
import junit.framework.TestSuite;

public class MOSTestAll {
    public static Test suite() {
        TestSuite testSuite = new TestSuite("test all");
        testSuite.addTestSuite(MOS.SDL_33Test.class);
        testSuite.addTestSuite(MOS.SDL_34Test.class);
        testSuite.addTestSuite(MOS.SDL_24Test.class);
        testSuite.addTestSuite(MOS.SDL_25Test.class);
        testSuite.addTestSuite(MOS.SDL_42Test.class);
        testSuite.addTestSuite(MOS.SDL_43Test.class);
        testSuite.addTestSuite(MOS.SDL_15Test.class);
        testSuite.addTestSuite(MOS.SDL_16Test.class);
        return testSuite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(MOSTestAll.suite());
    }
}