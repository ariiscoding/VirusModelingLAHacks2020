package Hacks.LA;

public class Person {
    private int x;
    private int y;
    private State state;
    private int age;
    private double deathRateWhenInfected;
    private boolean preexistingConditions;
    private double mobility;

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
    }

    private double calculateDeathRate () {
        //TODO: review this function when virus class is ready.
        return Constants.DEATH_RATE + (double)age * Constants.DEATH_RATE_INCREMENT;
    }
}
