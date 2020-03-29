package Hacks.LA;

import java.util.InputMismatchException;

public class Constants {
    //Simulation-related
    public static final int MIN_ITERATION = 20;

    //Cluster-related
    public static final int CLUSTER_POPULATION_UPPER_LIMIT = 20;
    public static final int CLUSTER_POPULATION_LOWER_LIMIT = 15;
    public static final int CLUSTER_X_LENGTH = 25;
    public static final int CLUSTER_Y_LENGTH = 25;
    public static final int NUMBER_OF_CLUSTERS = 10000;
    public static final double INTERCLUSTER_MOVEMENT_RATE = 0.5;

    //Person-related
    public static final int LOWER_AGE_LIMIT = 0; //inclusive lower limit
    public static final int UPPER_AGE_LIMIT = 100; //inclusive upper limit
    public static final double PREEXISTING_CONDITION_PROB = 0.4;
    public static final double AVERAGE_MOBILITY = 0.2;
    public static final int SELF_CURE_TIME = 20; //days

    //Healthcare system-related
    public static final double HOSPITAL_CAPACITY_PERCENTAGE = 0.11; //as a percentage of total population in a city
    public static final double HOSPITAL_FATALITY_RATE_DECREMENT = 0.8; //how much death rate is decreased by when in hospital
    //public static final int AVERAGE_CURE_TIME = 10; //days
    public static final int HOSPITAL_CURE_TIME = 10; //days. Release on this day.
    public static final int WAIT_BEFORE_HOSPITALIZATION = 10; //how many days are between showing symptoms and being admitted to hospital

    //Virus-related
    public static final double INFECTION_RATE = 0.9; //infection rate
    public static final int INCUBATION_PERIOD = 14; //days
    public static final double DEATH_RATE = 0.001; //for people at and under the age of 30
    public static final double DEATH_RATE_INCREMENT_AGE = 0.001;
    public static final int DANGER_AGE = 40;
    public static final double DEATH_RATE_INCREMENT_WITH_PREEXISTING_CONDITIONS = 0.01;
    public static final int INFECTION_RADIUS = 15;
    public static final int INITIAL_INFECTED = 500;


    public static void check() {
        //check validity of inputs
        if (CLUSTER_POPULATION_LOWER_LIMIT < 0) {
            throw new InputMismatchException("Cluster initial population must be non-negative.");
        }
        if (INITIAL_INFECTED < 0) {
            throw new InputMismatchException("Initial infected must be non-negative.");
        }
        if (CLUSTER_POPULATION_UPPER_LIMIT < CLUSTER_POPULATION_LOWER_LIMIT) {
            throw new InputMismatchException("Cluster initial population upper limit must not be less than the lower limit.");
        }

        if (INITIAL_INFECTED > CLUSTER_POPULATION_LOWER_LIMIT * NUMBER_OF_CLUSTERS) {
            throw new InputMismatchException("Initial infection amount must be less than population lower limit times number of clusters.");
        }

        if (CLUSTER_X_LENGTH * CLUSTER_Y_LENGTH < CLUSTER_POPULATION_UPPER_LIMIT) {
            throw new InputMismatchException("The cluster X and Y lengths must be large enough for population upper limit.");
        }
    }
}
