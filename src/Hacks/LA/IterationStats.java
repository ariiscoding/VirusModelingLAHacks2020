package Hacks.LA;

public class IterationStats {
    //this class calculates and saves statistics of each round of simulation.
    int time;
    int healthy;
    int incubated;
    int infected;
    int hospitalized;
    int immune;
    int deceased;

    public IterationStats() {
        time = Time.getTime();
        healthy = 0;
        incubated = 0;
        infected = 0;
        hospitalized = 0;
        immune = 0;
        deceased = 0;
    }

    public void count (Person person) {
        //each iteration, we count how many people are in each category.
        if (person == null) {
            return;
        }
        State state = person.getState();
        if (state == State.HEALTHY) {
            healthy++;
        }
        else if (state == State.INFECTED && person.getInfectionTime() != null && person.getInfectionTime() + 14 > time) {
            incubated++;
        }
        else if (state == State.INFECTED) {
            infected++;
        }
        else if (state == State.HOSPITALIZED) {
            hospitalized++;
        }
        else if (state == State.IMMUNE) {
            immune++;
        }
        else if (state == State.DECEASED) {
            deceased++;
        }
    }

    public int getTime() {
        return time;
    }

    public int getHealthy() {
        return healthy;
    }

    public int getIncubated() {
        return incubated;
    }

    public int getInfected() {
        return infected;
    }

    public int getHospitalized() {
        return hospitalized;
    }

    public int getImmune() {
        return immune;
    }

    public int getDeceased() {
        return deceased;
    }
}
