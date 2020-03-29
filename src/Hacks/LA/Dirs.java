package Hacks.LA;

public enum Dirs {
    NORTH(-1,0), SOUTH(1,0), WEST(0,-1), EAST(0,1);

    int deltaX;
    int deltaY;

    Dirs (int x, int y) {
        deltaX = x;
        deltaY = y;
    }
}
