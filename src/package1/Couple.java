package package1;

public class Couple {
    /**
     * The first member of the Couple
     */
    private int xOffset;
    /**
     * The second member of the Couple
     */
    private int zOffset;


    /**
     * Create an instance of Couple with the given members
     * @param i The first member of the couple to set
     * @param k The second member of the couple to set
     */
    Couple(int i, int k) {
        this.xOffset = i;
        this.zOffset = k;
    }


    /**
     * Get the first member of the Couple
     * @return The first member of the couple
     */
    public int i() {
        return this.xOffset;
    }
    /**
     * Get the second member of the Couple
     * @return The second member of the couple
     */
    public int k() {
        return this.zOffset;
    }


    /**
     * Usual toString() method.
     * @return A string of shape "(x, y)" where x and y are the two member or the Couple
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(
                "(" + this.xOffset + ", " + this.zOffset + ")");
        return result.toString();
    }
    /**
     * Method Overrided so that we cqn use Couples qs keys for The Hashmap
     * @param object The other object to determine if it is equal or not
     * @return A boolean telling if the two objects are equals or not
     */
    @Override
    public boolean equals(Object object){
        Couple c = (Couple) object;
        return ((this.xOffset == c.i()) && (this.zOffset == c.k()));
    }
    /**
     * Idem than equals(). Method overrided to be able to use Couple as keys for Hashmaps.
     * @return 0
     */
    @Override
    public int hashCode(){
        return 0;
    }
}
