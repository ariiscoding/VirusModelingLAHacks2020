package Hacks.LA;

public class Time {
    //Utilized a singleton to provide global access to time in simulation
    //Currently we view each unit of time as *one hour*

    private static Time timeInstance;
    private static int time;

    private Time() {
        time = 0;
    }

    public static int getTime() {
        if (timeInstance == null) {
            //lazy initiation
            timeInstance = new Time();
        }

        return time;
    }

    public static void incrementTime() {
        if (timeInstance == null) {
            timeInstance = new Time();
        }

        time++;
    }
}
