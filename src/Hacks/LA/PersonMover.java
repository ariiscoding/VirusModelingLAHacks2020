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

    private static void shuffleDirs() {
        //shuffle direction to simulate random movement within a cluster

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
