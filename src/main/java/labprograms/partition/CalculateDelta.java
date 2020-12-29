package labprograms.partition;

/**
 * describe:
 * delta = (max * 0.05/(1 - max) - max2 * 0.05 / (1 -max2)) * 0.8 + max2 * 0.05 / (1 -max2)
 *
 * @author phantom
 * @date 2019/05/10
 */
public class CalculateDelta {

    public static void calculate(double max, double max2) {
        double delta = ((max * 0.05 / (1 - max)) - (max2 * 0.05 / (1 - max2))) *
                0.8 + (max2 * 0.05 / (1 - max2));
        System.out.println(delta);
    }

    public static void main(String[] args) {
        CalculateDelta.calculate(0.25, 0.0278);
    }
}
