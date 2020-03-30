package Hacks.LA;

import java.util.List;

public class Hospital {
    //Hospital is a *concept* in our model. There is no physical hospital, and we simply mark a person
    //as "hospitalized" when he/she is in hospital.

    //This class primarily handles the recording of hospital capacity and safely increase and decrease it.
    private int capacity;

    Hospital(int population){
        capacity = (int)(population * Constants.HOSPITAL_CAPACITY_PERCENTAGE);
    }

    public boolean release(Person patient) {
        //determine if a person can be released from hospital, called by Person class
        if (patient.getState() == State.HOSPITALIZED) {
            capacity++;
            return true;
        }
        return false;
    }

    public boolean hospitalize(Person patient) {
        //determine if a person can be hospitalized
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
