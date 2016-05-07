package utils;

public class Triplet<A, B, C> {
    private Couple<A, B> couple;
    private C third;


    /**
     * Create an instance of triplet with the given members
     * @param first The first member to set to the triplet
     * @param second The second member to set to the triplet
     * @param third The second member to set to the triplet
     */
    public Triplet(A first, B second, C third) {
        this.couple = new Couple(first, second);
        this.third = third;
    }


    /**
     * Set the first member of the couple
     * @param first The value to assign to the first member of the triplet
     */
    public void setFirst(A first) {
        this.couple.setFirst(first);
    }
    /**
     * Set the second member of the couple
     * @param second The value to assign to the second member of the triplet
     */
    public void setSecond(B second) {
        this.couple.setSecond(second);
    }
    /**
     * Set the third member of the couple
     * @param third The value to assign to the third member of the triplet
     */
    public void setThird(C third) {
        this.third = third;
    }
    /**
     * Get the first member of the couple
     * @return The first member of the triplet
     */
    public A getFirst() {
        return this.couple.getFirst();
    }
    /**
     * Get the second member of the couple
     * @return The second member of the triplet
     */
    public B getSecond() {
        return this.couple.getSecond();
    }
    /**
     * Get the third member of the couple
     * @return The third member of the triplet
     */
    public C getThird() {
        return this.third;
    }


    @Override
    public int hashCode() {
        int hashThird = third != null ? third.hashCode() : 0;

        return (couple.hashCode() + hashThird) * hashThird + couple.hashCode();
    }
    /**
     * Overriden equals(Object o) method, useful to use as key in HashMaps
     * @param other The object to test the equality with
     * @return A boolean telling if the parameter is equal to the instance or
     * not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Triplet) {
            Triplet otherTriplet = (Triplet) other;
            return ( this.couple.equals(otherTriplet.couple) &&
                    ( this.third == otherTriplet.third ||
                            ( this.third != null && otherTriplet.third != null &&
                                    this.third.equals(otherTriplet.third))) );
        }

        return false;
    }
    /**
     * Overriden toString() method
     * @return A String representing the triplet
     */
    @Override
    public String toString(){
        return "(" + couple.getFirst() + ", " + couple.getSecond() + ", " +
                third +")";
    }
}
