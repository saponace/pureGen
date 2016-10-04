package com.saponace.pureGen.utils;

public class Position3D extends Triplet<Integer, Integer, Integer> {
    public Position3D(Integer first, Integer second, Integer third){
        super(first, second, third);
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
     * Set the z coordinate
     * @return The z coordinate
     */
    public void setZ(int z) {
        this.setThird(z);
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
    /**
     * Get the z coordinate
     * @return The z coordinate
     */
    public int getZ() {
        return this.getThird();
    }


}
