package com.saponace.pureGen.utils;

public class Couple<A, B> {
    private A first;
    private B second;


    /**
     * Create an instance of Couple with the given members
     * @param first The first member to set to the couple
     * @param second The second member to set to the couple
     */
    public Couple(A first, B second) {
        this.first = first;
        this.second = second;
    }


    /**
     * Set the first member of the couple
     * @param first The value to assign to the first member of the couple
     */
    public void setFirst(A first) {
        this.first = first;
    }
    /**
     * Set the second member of the couple
     * @param second The value to assign to the second member of the couple
     */
    public void setSecond(B second) {
        this.second = second;
    }


    /**
     * Get the first member of the couple
     * @return The first member of the couple
     */
    public A getFirst() {
        return first;
    }
    /**
     * Get the second member of the couple
     * @return The second member of the couple
     */
    public B getSecond() {
        return second;
    }


    /**
     * Overriden hashCode() method, useful to use as key in HashMaps
     * @return The hashCode of the couple
     */
    @Override
    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }
    /**
     * Overriden equals(Object o) method, useful to use as key in HashMaps
     * @param other The object to test the equality with
     * @return A boolean telling if the parameter is equal to the instance or
     * not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Couple) {
            Couple otherCouple = (Couple) other;
            return
                    ((  this.first == otherCouple.first ||
                            ( this.first != null && otherCouple.first != null &&
                                    this.first.equals(otherCouple.first))) &&
                            (	this.second == otherCouple.second ||
                                    ( this.second != null && otherCouple.second != null &&
                                            this.second.equals(otherCouple.second))) );
        }

        return false;
    }
    /**
     * Overriden toString() method
     * @return A String representing the couple
     */
    @Override
    public String toString(){
        return "(" + first + ", " + second + ")";
    }

}
