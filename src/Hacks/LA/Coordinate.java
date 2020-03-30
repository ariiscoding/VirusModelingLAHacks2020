package Hacks.LA;

public class Coordinate {
    //this class stores coordinates and state of each person, for support of animation.
    private int x;
    private int y;
    private State state;

    public Coordinate (Person person) {
        this.x = person.getX();
        this.y = person.getY();
        this.state = person.getState();
    }

    public Coordinate (int x, int y, State state) {
        this.x = x;
        this.y = y;
        this.state = state;
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
}
