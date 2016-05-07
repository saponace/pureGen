package utils;

public class Position2D extends Couple<Integer, Integer> {
    public Position2D(Integer first, Integer second){
        super(first, second);
    }


    /**
     * Set the x coordinate
     */
    public void setX(int x) {
        this.setFirst(x);
    }
    /**
     * Set the y coordinate
     * @return The y coordinate
     */
    public void setY(int y) {
        this.setSecond(y);
    }
    /**
     * Get the x coordinate
     * @return The first member of the couple
     */
    public int getX() {
        return this.getFirst();
    }
    /**
     * Get the y coordinate
     * @return The y coordinate
     */
    public int getY() {
        return this.getSecond();
    }

}
