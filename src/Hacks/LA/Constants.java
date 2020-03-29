package Hacks.LA;

public class Constants {
    //Cluster-related
    public static final int CLUSTER_POPULATION_UPPER_LIMIT = 50;
    public static final int CLUSTER_POPULATION_LOWER_LIMIT = 30;
    public static final int NUMBER_OF_CLUSTERS = 1000;
    public static final double INTERCLUSTER_MOVEMENT_RATE = 0.05;

    //Person-related
    public static final int LOWER_AGE_LIMIT = 0; //inclusive lower limit
    public static final int UPPER_AGE_LIMIT = 100; //inclusive upper limit
    public static final double PREEXISTING_CONDITION_PROB = 0.3;
    public static final double AVERAGE_MOBILITY = 0.2;
    public static final int SELF_CURE_TIME = 30; //days

    //Healthcare system-related
    public static final double HOSPITAL_CAPACITY_PERCENTAGE = 0.15; //as a percentage of total population in a city
    public static final double HOSPITAL_FATALITY_RATE_DECREMENT = 0.8; //how much death rate is decreased by when in hospital
    public static final int AVERAGE_CURE_TIME = 10; //days
    public static final int HOSPITAL_CURE_TIME = 10; //days. Release on this day.

    //Virus-related
    public static final double INFECTION_RATE = 0.7; //infection rate when one cell away
    public static final int INCUBATION_PERIOD = 14; //days
    public static final double DEATH_RATE = 0.01; //for people at and under the age of 30
    public static final double DEATH_RATE_INCREMENT_AGE = 0.004;
    public static final int DANGER_AGE = 40;
    public static final double DEATH_RATE_INCREMENT_WITH_PREEXISTING_CONDITIONS = 0.06;
    public static final int INFECTION_RADIUS = 2;
}
