package Hacks.LA;

import java.util.*;

public class Cluster {
    //represent social cluster

    private Person[][] field;
    private int population;
    public final int xLength;
    public final int yLength;
    private List<Person> rubric;

    Cluster () {
        //create a 100x100 field by default
        this(Constants.CLUSTER_X_LENGTH, Constants.CLUSTER_Y_LENGTH, Utils.randomInt(Constants.CLUSTER_POPULATION_LOWER_LIMIT, Constants.CLUSTER_POPULATION_UPPER_LIMIT));
    }

    public Cluster (int xLength, int yLength, int population) {
        field = new Person[xLength][yLength];
        this.population = population;
        this.xLength = xLength;
        this.yLength = yLength;
        rubric = new ArrayList<>();
        spawn(xLength, yLength, population);
    }

    public void recordCoordinates (int clusterIndex, boolean scale, List<Coordinate> list) {

        //scaling related
        int clusterX = clusterIndex % Constants.CLUSTER_PER_ROW;
        int clusterY = clusterIndex / Constants.CLUSTER_PER_ROW;
        int minX = clusterX * Constants.CLUSTER_X_LENGTH;
        int minY = clusterY * Constants.CLUSTER_Y_LENGTH;

        for (Person person : rubric) {
            if (scale) {
                int x = person.getX();
                int y = person.getY();
                list.add(new Coordinate(x + minX, y + minY, person.getState()));
            }
            else {
                list.add(new Coordinate(person));
            }
        }
    }

    public void survey (IterationStats iStats) {
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                iStats.count(field[x][y]);
            }
        }
    }

    public void loop(City city, Queue<Person> interclusterMover) {
        //this is the last part of this flow-through function to loop through every person in the simulation.
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
                    field[x][y] = null;
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
                    cur.setXY(x, y);
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
            }
        }
        if (interclusterMover.isEmpty()) {
            return true;
        }
        return false;
    }

    private void areaInfect (int centerX, int centerY) {
        if (field[centerX][centerY] == null || field[centerX][centerY].getState() != State.INFECTED) {
            return;
        }
        for (int x = Math.max(0, centerX - Constants.INFECTION_RADIUS); x <= Math.min(xLength-1, centerX + Constants.INFECTION_RADIUS); x++) {
            for (int y = Math.max(0, centerY - Constants.INFECTION_RADIUS); y <= Math.min(yLength-1, centerY + Constants.INFECTION_RADIUS); y++) {
                if (infectiblePerson(x, y)) {
                    //roll the dice to decide if the person gets infected
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

                if (infectiblePerson(x,y) && Utils.randomBool(Constants.INFECTION_RATE)) {
                    field[x][y].infect();
                    count[0]++;
                }
            }
        }
    }

    public boolean validCell (int x, int y) {
        return x >= 0 && x < xLength && y >=0 && y < yLength;
    }

    public boolean infectiblePerson(int x, int y) {
        if (validCell(x, y)) {
            Person person = field[x][y];
            if (person != null && person.getState() == State.HEALTHY) {
                return true;
            }
        }
        return false;
    }

    public boolean movePerson (int x, int y, int newX, int newY) {
        //move a person from x,y to newX,newY
        if (canMoveHere(newX, newY)) {
            Person temp = field[x][y];
            field[x][y] = null;
            field[newX][newY] = temp;
            temp.setXY(newX, newY);
            return true;
        }
        return false;
    }

    public boolean canMoveHere (int x, int y) {
        //decide if x, y is a valid coordinate to move a person into
        if (x < 0 || x >= xLength || y < 0 || y >= yLength) {
            return false;
        }
        else if (field[x][y] != null) {
            return false;
        }
        return true;
    }

    public Person getPerson (int x, int y) {
        //safely get a person at the target coordinate
        if (x < 0 || x >= field.length || y < 0 || y >= field.length) {
            return null;
        }
        return field[x][y];
    }

    private void spawn (int xLength, int yLength, int population) {
        //initialize the cluster with all healthy people. This method does not handle initial infection.
        if (xLength * yLength < population) {
            throw new InputMismatchException("The field size is not large enough for the population size.");
        }
        while (population > 0) {
            int x = Utils.randomInt(0, xLength);
            int y = Utils.randomInt(0, yLength);
            if (availableForSpawn(x, y)) {
                Person temp = new Person(x, y);
                field[x][y] = temp;
                rubric.add(temp);
                population--;
            }
        }
    }

    private boolean availableForSpawn (int x, int y) {
        //return if the box is available for spawning a new person
        if (x >= field.length || y >= field[x].length || x < 0 || y < 0 || field[x][y] != null) {
            return false;
        }

        return true;
    }

    public int getPopulation() {
        return population;
    }
}
