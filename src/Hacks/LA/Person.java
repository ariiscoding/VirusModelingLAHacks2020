package Hacks.LA;

public class Person {
    private int x;
    private int y;
    private State state;
    private int age;
    private double deathRateWhenInfected;
    private boolean preexistingConditions;
    private double mobility;
    private Integer infectionTime;
    private Integer hospitalizedTime;

    public Person (int x, int y) {
        //generate a person with randomly generated stats
        this(x, y, State.HEALTHY, Utils.randomInt(Constants.LOWER_AGE_LIMIT, Constants.UPPER_AGE_LIMIT+1), Utils.randomBool(Constants.PREEXISTING_CONDITION_PROB), Utils.randomProb(Constants.AVERAGE_MOBILITY));
    }

    public Person (int x, int y, State state, int age, boolean preexistingConditions, double mobility) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.age = age;
        this.preexistingConditions = preexistingConditions;
        this.mobility = mobility;
        deathRateWhenInfected = calculateDeathRate();
        infectionTime = null;
        hospitalizedTime = null;
    }

    public void updateState (Hospital hospital) {
        //A helper function for "loop"
        //handles hospitalization, cure, and killing mechanisms for each person
        //Sub-functions will handle condition checking automatically

        if (state == State.DECEASED) {
            return;
        }
        hospitalize(hospital);
        cure(hospital);
        determineDeath(hospital);
    }

    public boolean hospitalize (Hospital hospital) {
        //Check if a person is sick and has been sick long enough to be hospitalized
        //then communicate with hospital to check capacity

        //If all are true, hospitalize this person

        if (state != State.INFECTED) {
            return false;
        }
        else if (infectionTime == null || infectionTime + Constants.INCUBATION_PERIOD + Constants.WAIT_BEFORE_HOSPITALIZATION > Time. getTime()) {
            return false;
        }
        if (hospital.hospitalize(this)) {
            state = State.HOSPITALIZED;
            hospitalizedTime = Time.getTime();
            calculateDeathRate();
            return true;
        }
        return false;
    }

    public boolean cure (Hospital hospital) {
        //Check if the person has been sick long enough to cure him/her

        if (state == State.INFECTED && infectionTime != null && infectionTime + Constants.SELF_CURE_TIME <= Time.getTime()) {
            //self-cure
            state = State.IMMUNE;
            calculateDeathRate();
            return true;
        }
        else if (state == State.HOSPITALIZED && hospitalizedTime != null && hospitalizedTime + Constants.HOSPITAL_CURE_TIME <= Time.getTime() && hospital.release(this)) {
            //out of hospital
            state = State.IMMUNE;
            calculateDeathRate();
            return true;
        }
        return false;
    }

    public boolean determineDeath (Hospital hospital) {
        //Check if the person can possibly die (only if infected or hospitalized)
        //If yes, roll a dice and see if the person is unlucky enough to die

        if (state != State.INFECTED && state != State.HOSPITALIZED) {
            return false;
        }

        if (Utils.randomBool(deathRateWhenInfected)) {
            return die(hospital);
        }

        return false;
    }

    public boolean die (Hospital hospital) {
        //Handle the killing logics
        //Also communicate with hospital to release capacity if necessary

        if (state == State.DECEASED || state == State.IMMUNE) {
            return false;
        }

        if (state == State.HOSPITALIZED) {
            hospital.release(this);
        }
        state = State.DECEASED;

        return true;
    }

    public boolean infect() {
        //Infect a person. Return false if the person cannot be infected.

        if (state != State.HEALTHY) {
            return false;
        }
        state = State.INFECTED;
        infectionTime = Time.getTime();

        return true;
    }

    private double calculateDeathRate () {
        //Death rate = (base death rate + age over 40 * increment percentage + pre-existing condition) / hospital decrement

        double deathRate = Constants.DEATH_RATE + Constants.DEATH_RATE_INCREMENT_AGE * Math.max(0, age - Constants.DANGER_AGE);
        if (preexistingConditions) {
            deathRate += Constants.DEATH_RATE_INCREMENT_WITH_PREEXISTING_CONDITIONS;
        }
        if (state == State.HOSPITALIZED) {
            deathRate *= (1-Constants.HOSPITAL_FATALITY_RATE_DECREMENT);
        }

        this.deathRateWhenInfected = deathRate;
        return deathRate;
    }

    void setXY (int newX, int newY) {
        //setter with default access privilege

        this.x = newX;
        this.y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public State getState() {
        return state;
    }

    public int getAge() {
        return age;
    }

    public double getDeathRateWhenInfected() {
        return deathRateWhenInfected;
    }

    public boolean isPreexistingConditions() {
        return preexistingConditions;
    }

    public double getMobility() {
        return mobility;
    }

    public Integer getInfectionTime() {
        return infectionTime.intValue();
    }
}
