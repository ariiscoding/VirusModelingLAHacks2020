package Hacks.LA;

public class Virus {
    public static final double INFECTION_RATE = Constants.INFECTION_RATE; //infection rate when one cell away
    public static final int INCUBATION_PERIOD = Constants.INCUBATION_PERIOD; //days
    public static final double DEATH_RATE = Constants.DEATH_RATE; //default for people at and under the age of 30
    public static final double DEATH_RATE_INCREMENT = Constants.DEATH_RATE_INCREMENT; //per age over 30


    private Virus(){
    }
}
