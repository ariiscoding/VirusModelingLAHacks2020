package Hacks.LA;

import java.util.Deque;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;

public class Cluster {
    //represent social cluster

    private Person[][] field;
    private int population;
    public final int xLength;
    public final int yLength;

    Cluster () {
        //create a 100x100 field by default
        this(100, 100, Utils.randomInt(Constants.CLUSTER_POPULATION_LOWER_LIMIT, Constants.CLUSTER_POPULATION_UPPER_LIMIT));
    }

    public Cluster (int xLength, int yLength, int population) {
        field = new Person[xLength][yLength];
        this.population = population;
        this.xLength = xLength;
        this.yLength = yLength;
        spawn(xLength, yLength, population);
    }

    public void loop(City city, Queue<Person> interclusterMover) {
        //loop through every space

        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {

                Person cur = field[x][y];
                if (cur==null) {
                    //place a person from interclusterMover queue here
                    if (!interclusterMover.isEmpty()) {
                        field[x][y] = interclusterMover.poll();
                        cur = field[x][y];
                    }
                    else {
                        continue;
                    }
                }
                //step 1: update state (such as from hospitalized -> immune)
                cur.updateState(city.hospital);

                //Step 2: decide if this person will be moved to another cluster
                    //note: we don't count it in this case, otherwise we would over count
                if (Utils.randomBool(Constants.INTERCLUSTER_MOVEMENT_RATE)) {
                    interclusterMover.offer(cur);
                    continue;
                }

                //step 3: move (NOTE: may change coordinate)
                PersonMover.move(this, x, y);

                //step 4: determine infection of other people
                int newX = cur.getX();
                int newY = cur.getY();

                areaInfect(newX, newY);

                //step 5: check status again, such as infected -> hospitalized
                cur.updateState(city.hospital);

                //step final: count
                city.stats.count(cur);
            }
        }
    }

    public boolean loopCleanQueue(City city, Queue<Person> interclusterMover) {
        //similar to normal loop, but we no longer add new people to interclusterMover

        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {

                if(interclusterMover.isEmpty()) {
                    return true;
                }

                Person cur = field[x][y];

                if (cur != null) {
                    continue;
                }
                else {
                    field[x][y]= interclusterMover.poll();
                    cur = field[x][y];
                }

                //step 1: update state (such as from hospitalized -> immune)
                cur.updateState(city.hospital);

                //step 3: move (NOTE: may change coordinate)
                PersonMover.move(this, x, y);

                //step 4: determine infection of other people
                int newX = cur.getX();
                int newY = cur.getY();

                areaInfect(newX, newY);

                //step 5: check status again, such as infected -> hospitalized
                cur.updateState(city.hospital);

                //step final: count
                city.stats.count(cur);
            }
        }
        if (interclusterMover.isEmpty()) {
            return true;
        }
        return false;
    }

    private void areaInfect (int centerX, int centerY) {
        for (int x = centerX - Constants.INFECTION_RADIUS; x <= Math.min(xLength-1, centerX + Constants.INFECTION_RADIUS); x++) {
            for (int y = centerY - Constants.INFECTION_RADIUS; y <= Math.min(yLength-1, centerY + Constants.INFECTION_RADIUS); y++) {
                if (infectablePerson(x, y)) {
                    //roll the dice
                    if (Utils.randomBool(Constants.INFECTION_RATE)) {
                        field[x][y].infect();
                    }
                }
            }
        }
    }

    public void initialInfection (int[] count) {
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                if (count[0] >= Constants.INITIAL_INFECTED) {
                    return;
                }
                if (infectablePerson(x,y)) {
                    field[x][y].infect();
                    count[0]++;
                }
            }
        }
    }

    public boolean validCell (int x, int y) {
        return x >= 0 && x < xLength && y >=0 && y < yLength;
    }

    public boolean infectablePerson (int x, int y) {
        if (validCell(x, y)) {
            Person person = field[x][y];
            if (person != null && person.getState() == State.HEALTHY) {
                return true;
            }
        }
        return false;
    }

    public boolean movePerson (int x, int y, int newX, int newY) {
        if (canMoveHere(newX, newY)) {
            field[x][y].setXY(newX, newY);
            field[newX][newY] = field[x][y];
            field[x][y] = null;
            return true;
        }
        return false;
    }

    public boolean canMoveHere (int x, int y) {
        if (x < 0 || x >= xLength || y < 0 || y >= yLength) {
            return false;
        }
        else if (field[x][y] != null) {
            return false;
        }
        return true;
    }

    public Person getPerson (int x, int y) {
        if (x < 0 || x >= field.length || y < 0 || y >= field.length) {
            return null;
        }
        return field[x][y];
    }

    private void spawn (int xLength, int yLength, int population) {
        if (xLength * yLength < population) {
            throw new InputMismatchException("The field size is not large enough for the population size.");
        }
        while (population > 0) {
            int x = Utils.randomInt(0, xLength);
            int y = Utils.randomInt(0, yLength);
            if (isAvailable(x, y)) {
                field[x][y] = new Person(x, y);
                population--;
            }
        }
    }

    private boolean isAvailable (int x, int y) {
        //return if the box is available
        if (x > field.length || y > field[x].length) {
            return false;
        }
        else if (field[x][y] != null && field[x][y].getState() != State.DECEASED) {
            return false;
        }

        return true;
    }

    public int getPopulation() {
        return population;
    }
}
