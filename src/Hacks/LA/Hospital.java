package Hacks.LA;

import java.util.List;

public class Hospital {
//    private double HOSPITAL_CAPACITY_PERCENTAGE;
//    private double HOSPITAL_FATALITY_RATE_DECREMENT;
//    private double AVERAGE_CURE_TIME;
    private int capacity;

    Hospital(int population){
//        HOSPITAL_CAPACITY_PERCENTAGE = Constants.HOSPITAL_CAPACITY_PERCENTAGE;
//        HOSPITAL_FATALITY_RATE_DECREMENT = Constants.HOSPITAL_FATALITY_RATE_DECREMENT;
//        AVERAGE_CURE_TIME = Constants.AVERAGE_CURE_TIME;
        capacity = (int)(population * Constants.HOSPITAL_CAPACITY_PERCENTAGE);
    }

    public boolean release(Person patient) { //determine if a person can be released from hospital, called by Person class
        if (patient.getState() == State.HOSPITALIZED) {
            capacity++;
            return true;
        }
        return false;
    }

    public boolean hospitalize(Person patient) { //determine if a person can be hospitalized
        if (patient.getState() == State.INFECTED) {
            if (!isFull()) {
                capacity--;
                return true;
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
