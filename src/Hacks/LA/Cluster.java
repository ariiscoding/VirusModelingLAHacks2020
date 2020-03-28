package Hacks.LA;

import java.util.InputMismatchException;
import java.util.List;

public class Cluster {
    //represent social cluster

    private Person[][] field;

    Cluster () {
        //create a 100x100 field by default
        this(100, 100, Utils.randomInt(Constants.CLUSTER_POPULATION_LOWER_LIMIT, Constants.CLUSTER_POPULATION_UPPER_LIMIT));
    }

    public Cluster (int xLength, int yLength, int population) {
        field = new Person[xLength][yLength];
        spawn(xLength, yLength, population);
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
}
