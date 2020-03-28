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
    private Integer hospitalzedTime;
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
        hospitalzedTime = null;
    }

    public boolean hospitalize (Hospital hospital) {
        if (hospital.hospitalize(this)) {
            state = State.HOSPITALIZED;
            hospitalzedTime = Time.getTime();
            return true;
        }
        return false;
    }

    public boolean determineCure (Hospital hospital) {
        if (state == State.INFECTED && infectionTime != null && infectionTime >= Constants.SELF_CURE_TIME) {
            state = State.IMMUNE;
            return true;
        }
        else if (state == State.HOSPITALIZED && hospitalzedTime != null && hospitalzedTime >= Constants.HOSPITAL_CURE_TIME && hospital.release(this)) {
            state = State.IMMUNE;
            return true;
        }
        return false;
    }

    public boolean determineDeath (Hospital hospital) {
        //TODO: roll the dice and see if the person die

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
        //TODO: review this function when virus class is ready.
        if(age>=80){
            return 15.0;
        }
        else if(age>=70&&age<80){
            return 8.0;
        }
        else if(age>=60&&age<70){
            return 3.6;
        }
        else if(age>=50&&age<60){
            return 1.3;
        }
        else if(age>=40&&age<50){
            return 0.4;
        }
        else if(age>=10&&age<40){
            return 0.2;
        }
        else{
            return 0.0;
        }
        return Constants.DEATH_RATE + (double)age * Constants.DEATH_RATE_INCREMENT;
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
