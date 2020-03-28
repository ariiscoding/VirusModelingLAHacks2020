package Hacks.LA;

import java.util.Random;

public class Utils {
    private static Random random = new Random();

    public static int randomInt (int lower, int upper) {
        //randomly generate a number between lower (inclusive) and upper (exclusive)
        return lower + random.nextInt(upper-lower);
    }

    public static boolean randomBool (double trueProb) {
        //randomly generate a boolean, with a pre-defined probability of getting true
        double dice = random.nextGaussian();
        if (dice <= trueProb) {
            return true;
        }
        else {
            return false;
        }
    }

    public static double randomProbUniform () {
        return random.nextDouble();
    }

    public static double randomProb (double center) {
        //randomly generate a probability between 0 (inclusive) and 1 (exclusive)
        return randomProb(center, 0, 1);
    }

    public static double randomProb (double center, double lower, double upper) {
        //randomly generate a probability centered around center and between lower (inclusive) and upper (exclusive)

        double output = random.nextGaussian();
        output = randomProbHelper(output, center, upper, lower);

        while (output < lower || output >= upper) {
            output = random.nextGaussian();
            output = randomProbHelper(output, center, upper, lower);
        }

        return output;
    }

    private static double randomProbHelper(double output, double center, double upper, double lower) {
        if (center == 0d){
            return output;
        }
        else if (lower > upper) {
            double temp = lower;
            lower = upper;
            upper = temp;
        }

        output /= 0.5; //center it based on normal distribution

        //conform to range
        output = lower + output * (upper - lower);

        return output;
    }
}
