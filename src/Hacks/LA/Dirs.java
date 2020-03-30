package Hacks.LA;

public enum Dirs {
    //this enum enlists all possible directions a person can potentially move.
    //Note we do not support move diagonally for simplicity. However, it can be added quite easily in future releases.
    NORTH(-1,0), SOUTH(1,0), WEST(0,-1), EAST(0,1);

    int deltaX;
    int deltaY;

    Dirs (int x, int y) {
        deltaX = x;
        deltaY = y;
    }
}
