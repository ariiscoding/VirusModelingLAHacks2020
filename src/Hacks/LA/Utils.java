package Hacks.LA;

import java.util.InputMismatchException;
import java.util.Random;

public class Utils {
    //A class that provides many tools for other functions
    //All functions are static so they can be used without instantiating Utils

    private static Random random = new Random();

    public static int randomInt (int lower, int upper) {
        //randomly generate a number between lower (inclusive) and upper (exclusive)

        return lower + random.nextInt(upper-lower);
    }

    public static boolean randomBool (double trueThreshold) {
        //randomly generate a boolean, with a pre-defined probability of getting true

        double dice = randomProbUniform();
        if (dice <= trueThreshold) {
            return true;
        }
        else {
            return false;
        }
    }

    public static double randomProbUniform () {
        //provide a uniformly distributed random number between 0.0 and 1.0

        return random.nextDouble();
    }

    @Deprecated
    public static double randomProb (double center) {
        //randomly generate a probability between 0 (inclusive) and 1 (exclusive)
        //*NORMAL DISTRIBUTION AROUND CENTER*
        //Deprecated because not very useful for our simulation

        return randomProb(center, 0, 1);
    }

    @Deprecated
    public static double randomProb (double center, double lower, double upper) {
        //randomly generate a probability centered around center and between lower (inclusive) and upper (exclusive)
        //*NORMAL DISTRIBUTION AROUND CENTER*
        //Deprecated because not very useful for our simulation

        if (center < lower || center > upper) {
            throw new InputMismatchException("Center must be within the lower-upper range.");
        }

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
