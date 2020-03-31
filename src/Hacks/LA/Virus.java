package Hacks.LA;

public class Virus {

    public static void getInfo(){
        System.out.println("");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("VIRUS REPORT");
        System.out.println("The current virus has an infection rate of " + Constants.INFECTION_RATE + ".");
        System.out.println("The incubation period lasts " + Constants.INCUBATION_PERIOD + " days.");
        System.out.println("The fatality rate is " +  Constants.DEATH_RATE + " for people under the age of " + Constants.DANGER_AGE +".");
        System.out.println("The infection radius is " + Constants.INFECTION_RADIUS + ".");
        System.out.println("There are " + Constants.INITIAL_INFECTED + " people initially infected.");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("");
    }
}
