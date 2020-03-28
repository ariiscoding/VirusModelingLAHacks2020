package Hacks.LA;

public class Constants {
    //Cluster-related
    public static final int CLUSTER_POPULATION_UPPER_LIMIT = 100;
    public static final int CLUSTER_POPULATION_LOWER_LIMIT = 60;
    public static final int NUMBER_OF_CLUSTERS = 10;
    public static final double INTERCLUSTER_MOVEMENT_RATE = 0.05;

    //Person-related
    public static final int UPPER_AGE_LIMIT = 100;

    //Healthcare system-related
    public static final double HOSPITAL_CAPACITY_PERCENTAGE = 0.15; //as a percentage of total population in a city
    public static final double HOSPITAL_FATALITY_RATE_DECREMENT = 0.8; //how much death rate is decreased by when in hospital
    public static final int AVERAGE_CURE_TIME = 10; //days

    //Virus-related
    public static final double INFECTION_RATE = 0.7; //infection rate when one cell away
    public static final int INCUBATION_PERIOD = 14; //days
    public static final double DEATH_RATE = 0.01; //for people at and under the age of 30
    public static final double DEATH_RATE_INCREMENT = 0.004; //per age over 30
}
