package Hacks.LA;

public class Virus {
    public static final double INFECTION_RATE = Constants.INFECTION_RATE; //infection rate when one cell away
    public static final int INCUBATION_PERIOD = Constants.INCUBATION_PERIOD; //days
    public static final double DEATH_RATE = Constants.DEATH_RATE; //default for people at and under the age of 30
    public static final double DEATH_RATE_INCREMENT = Constants.DEATH_RATE_INCREMENT; //per age over 30


    private Virus(){
    }

    public getInfo(){
        System.out.println("The current virus has an infection rate of ", INFECTION_RATE, ".");
        System.out.println("The incubation period lasts ", INCUBATION_PERIOD, " days.");
        System.out.println("The default fatality rate is ", DEATH_RATE, ".");
    }
}
