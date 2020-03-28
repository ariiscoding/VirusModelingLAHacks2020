package Hacks.LA;

public class Time {
    private static Time timeInstance;
    private static int time;

    private Time() {
        time = 0;
    }

    public static int getTime() {
        if (timeInstance == null) {
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
