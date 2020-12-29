package labprograms.mutants;

import lombok.Getter;
import lombok.Setter;

/**
 * describe:
 *
 * @author phantom
 * @date 2019/04/18
 */
public class Mutant {

    @Setter
    @Getter
    private String fullName;

    public Mutant(String fullName) {
        setFullName(fullName);
    }
}
