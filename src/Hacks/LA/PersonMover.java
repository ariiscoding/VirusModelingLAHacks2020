package Hacks.LA;

public class PersonMover {
    private static Dirs[] dirs = Dirs.values();

    static void move (Cluster cluster, int x, int y) {
        //move a person by 1 block if possible

        //check if the target can be moved
        if (!canMove(cluster.getPerson(x,y))) {
            return;
        }

        //check against this person's mobility
        double mobility = cluster.getPerson(x,y).getMobility();
        if (!Utils.randomBool(mobility)) {
            return;
        }

        //shuffle directions
        shuffleDirs();

        //make move
        for (Dirs dir : dirs) {
            int newX = x + dir.deltaX;
            int newY = y + dir.deltaY;
            if (cluster.movePerson(x, y, newX, newY)) {
                return;
            }
        }
    }

    //this function is not used. Can delete
    private static boolean validLocation (Cluster cluster, int x, int y) {
        if (x < 0 || x >= cluster.xLength || y < 0 || y >= cluster.yLength) {
            return false;
        }
        else if (cluster.getPerson(x, y) != null) {
            return false;
        }
        return true;
    }

    private static void shuffleDirs() {
        for (int i = 0; i < dirs.length; i++) {
            int target = Utils.randomInt(i, dirs.length);
            Dirs temp = dirs[i];
            dirs[i] = dirs[target];
            dirs[target] = temp;
        }
    }

    private static boolean canMove (Person person) {
        //determine whether the person can be moved
        if (person == null || person.getState() == State.HOSPITALIZED || person.getState() == State.DECEASED) {
            return false;
        }
        return true;
    }
}
