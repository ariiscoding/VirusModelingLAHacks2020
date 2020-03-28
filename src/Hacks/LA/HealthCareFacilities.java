package Hacks.LA;

import java.util.List;

public class HealthCareFacilities {
    private double HOSPITAL_CAPACITY_PERCENTAGE = Constants.HOSPITAL_CAPACITY_PERCENTAGE; //as a percentage of total population in a city
    private double HOSPITAL_FATALITY_RATE_DECREMENT = Constants.HOSPITAL_FATALITY_RATE_DECREMENT; //how much death rate is decreased by when in hospital
    private double AVERAGE_CURE_TIME = Constants.AVERAGE_CURE_TIME; //days
    private int capacity = (int) (HOSPITAL_CAPACITY_PERCENTAGE * Constants.POPULATION);

    private HealthCareFacilities(){
    }

    private void hospitalize(Person patient) {
        if (!isFull()) {
            capacity--;
            patient.setState(HOSPITALIZED);
        }
    }

    private boolean isFull() {
        if (capacity <= 0) {
            return true;
        }
        return false;
    }
}
