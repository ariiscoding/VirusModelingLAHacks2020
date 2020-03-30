package Hacks.LA;

public class Virus {

    public static void getInfo(){
        System.out.println("The current virus has an infection rate of " + Constants.INFECTION_RATE + ".");
        System.out.println("The incubation period lasts " + Constants.INCUBATION_PERIOD + " days.");
        System.out.println("The fatality rate is " +  Constants.DEATH_RATE + " for people under the age of " + Constants.DANGER_AGE +".");
    }
}
