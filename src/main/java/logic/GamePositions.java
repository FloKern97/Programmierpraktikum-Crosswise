package logic;

/**
 * Position class to ensure that a player cannot enter an invalid position.
 *
 * @author Florian Kern/cgt104661
 */

public record GamePositions(int posX, int posY) implements Comparable<GamePositions> {

    /**
     * Getter for the X-position.
     *
     * @return The X-position
     */
    public int getX() {
        return this.posX;
    }

    /**
     * Getter for the Y-position.
     *
     * @return The Y-Position
     */
    public int getY() {
        return this.posY;
    }


    @Override
    public int compareTo(GamePositions pos) {
        int res = 0;
        if (this.posY < pos.getY()) {
            res = -1;
        } else if (this.posY > pos.getY()) {
            res = 1;
        }

        if (res == 0 && this.posX < pos.posX) {
            res = -1;
        } else if (res == 0 && this.posX > pos.posX) {
            res = 1;
        }

        return res;
    }
}