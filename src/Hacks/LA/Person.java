package Hacks.LA;

public class Person {
    private int x;
    private int y;
    private State state;
    private int age;
    private double deathRateWhenInfected;
    private boolean preexistingConditions;
    private double mobility;
    private Integer infectionTime; //TODO: self-healing function
    private Integer hospitalizedTime;
    //Todo: add necessary setters

    public Person (int x, int y) {
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

    public boolean hospitalize (Hospital hospital) {
        if (hospital.hospitalize(this)) {
            state = State.HOSPITALIZED;
            hospitalizedTime = Time.getTime();
            calculateDeathRate();
            return true;
        }
        return false;
    }

    public boolean cure (Hospital hospital) {
        calculateDeathRate();
        if (state == State.INFECTED && infectionTime != null && infectionTime >= Constants.SELF_CURE_TIME) {
            state = State.IMMUNE;
            return true;
        }
        else if (state == State.HOSPITALIZED && hospitalizedTime != null && hospitalizedTime >= Constants.HOSPITAL_CURE_TIME && hospital.release(this)) {
            state = State.IMMUNE;
            return true;
        }
        return false;
    }

    public boolean determineDeath (Hospital hospital) {
        //roll the dice and see if the person die
        double hades = Utils.randomProbUniform();

        if (hades <= deathRateWhenInfected) {
            return die(hospital);
        }

        return false;
    }

    public boolean die (Hospital hospital) {
        if (state == State.DECEASED) {
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
        //Death rate = (base death rate + age over 30 * increment percentage + pre-existing condition) / hospital decrement
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
