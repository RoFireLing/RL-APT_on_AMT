package controller;

import labprograms.constant.Constant;
import labprograms.newStrategy.utl.GetMRInfo;

import java.util.Map;
import java.util.Random;

/**
 * @Description:　test case selecting strategy : randodm
 * MR selecting strategy : random
 * @auther phantom
 * @create 2019-09-19 上午9:29
 */
public class RandomSelectTestCaseRandomSelectMR implements SelectingTestCaseAndMR {
    private String testframesAndMr;

    @Override
    public String selectTestCase(String objectName) {
        String tc = null;
        int index = new Random().nextInt(Constant.getNUmberOfMR(objectName)) + 1;
        Map<String, String> mrInfo = GetMRInfo.getMRinfo(objectName);
        tc = mrInfo.get(String.valueOf(index));
        testframesAndMr = tc;
        return tc;
    }

}
