package labprograms.mutants;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.io.File.separator;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/04/18
 */
public class MutantsSet {

    private static final String PARENT_DIR = "labprograms.";

    private String mutants_dir;

    private String dir;

    private Map<String, String> map = new HashMap<String, String>();

    @Setter
    private String objectName;

    @Getter
    @Setter
    private Map<String, Mutant> mutants;

    public MutantsSet(String objectName) {
        setObjectName(objectName);
        mutants = new HashMap<>();
        mutants_dir = PARENT_DIR + this.objectName + ".mutants.";
        dir = System.getProperty("user.dir") + separator + "src" + separator + "main" +
                separator + "java" + separator + "labprograms" + separator + objectName
                + separator + "mutants";
        putMap();
    }

    public static void main(String[] args) {
        MutantsSet mutantsSet = new MutantsSet("ACMS");
        System.out.println(mutantsSet.getMutants().get("SDL_8").getFullName());
    }

    public Map<String, Mutant> getMutants() {
        File dir = new File(this.dir);
        String[] mutantNames = dir.list();
        for (String mutantName : mutantNames) {
            String fullName = mutants_dir + mutantName + "." + map.get(objectName).toString();
            Mutant mutant = new Mutant(fullName);
            mutants.put(mutantName, mutant);
        }
        return mutants;
    }

    private void putMap() {
        this.map.put("ACMS", "AirlinesBaggageBillingService");
        this.map.put("CUBS", "BillCalculation");
        this.map.put("ERS", "ExpenseReimbursementSystem");
        this.map.put("MOS", "MealOrderingSystem");
    }

//    private static final String PARENT_DIR = "labprograms.";
//    private String objectName;
//
//    private String[] classNames;
//
//    @Getter
//    private List<String> mutantSet;
//
//    public MutantsSet(String objectName){
//        this.objectName = objectName;
//        mutantSet = new ArrayList<>();
//        classNames = new String[] {"AirlinesBaggageBillingService", "BillCalculation", "ExpenseReimbursementSystem",
//                "MealOrderingSystem"};
//        initialize();
//    }
//
//    private void initialize(){
//        String dir = System.getProperty("user.dir") + separator + "src" + separator + "main" +
//                separator + "java" + separator + "labprograms";
//        String mutantDir = dir + separator + objectName + separator + "mutants";
//
//        String[] mutantnames = new File(mutantDir).list();
//
//        for (int i = 0; i < mutantnames.length; i++) {
//            String fullName = PARENT_DIR + objectName + ".mutants." + mutantnames[i] +
//        }
//
//    }


}
