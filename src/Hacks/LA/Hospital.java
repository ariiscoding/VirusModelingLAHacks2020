package Hacks.LA;

import java.util.List;

public class Hospital {
    private double HOSPITAL_CAPACITY_PERCENTAGE = Constants.HOSPITAL_CAPACITY_PERCENTAGE; //as a percentage of total population in a city
    private double HOSPITAL_FATALITY_RATE_DECREMENT = Constants.HOSPITAL_FATALITY_RATE_DECREMENT; //how much death rate is decreased by when in hospital
    private double AVERAGE_CURE_TIME = Constants.AVERAGE_CURE_TIME; //days
    private int capacity = (int) (HOSPITAL_CAPACITY_PERCENTAGE * Constants.POPULATION);

    private Hospital(){
    }

    public boolean release(Person patient) { //determine if a person can be released from hospital, called by Person class
        if (patient.getState() == "HOSPITALIZED") {
            capacity++;
            return true;
        } else {
            return false;
        }
        return false;
    }

    public boolean hospitalize(Person patient) { //determine if a person can be hospitalized
        if (patient.getState() == "INFECTED") {
            if (!isFull()) {
                capacity--;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean isFull() {
        if (capacity <= 0) {
            return true;
        }
        return false;
    }
}
