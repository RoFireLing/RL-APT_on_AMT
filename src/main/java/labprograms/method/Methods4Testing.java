package labprograms.method;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * describe:
 * recording the methods of each program
 *
 * @author phantom
 * @date 2019/04/18
 */
public class Methods4Testing {

    @Getter
    private String methodName;

    private Map<String, String> map;

    public Methods4Testing(String objectName) {
        initializeMap();
        methodName = this.map.get(objectName);
    }

    private void initializeMap() {
        this.map = new HashMap<>();
        this.map.put("ACMS", "feeCalculation");
        this.map.put("CUBS", "phoneBillCalculation");
        this.map.put("ERS", "calculateReimbursementAmount");
        this.map.put("MOS", "generateMSR");
    }


}
