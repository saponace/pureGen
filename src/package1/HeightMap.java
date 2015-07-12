package package1;

public class HeightMap {
    /**
     * The width of the heightMap
     */
    private int xSize;
    /**
     * The minimum height of the heightMap
     */
    private int yMin;
    /**
     * The maximum height of the heightMap
     */
    private int yMax;
    /**
     * The length of the heightMap
     */
    private int zSize;
    /**
     * The frequency at which a new gradient should be chose to generate the
     * heightmap
     */
    private int frequency;
    /**
     * The 2D array containing the height
     */
    private static int[][] heightArray;
    /**
     * The 2D array containing the gradients
     */
    private static double[][] gradients;


    /**
     * Create an instance of heightMap
     * @param xMin The minimum offset of the X-axis
     * @param xMax The maximum offset of the X-axis
     * @param yMin The minimum offset of the Y-axis
     * @param yMax The maximum offset of the Y-axis
     * @param zMin The minimum offset of the Z-axis
     * @param zMax The maximum offset of the Z-axis
     */
    public HeightMap(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
        this.xSize = xMax - xMin;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zSize = zMax - zMin;
        heightArray = new int[xSize][zSize];
        frequency = 30;
        buildGradients();

        computeHeight(60, 0);

        perlin();
        smoothenSurface(2, 10);
    }


    /**
     * Get the height of the surface on the given coordinates
     * @param i The offset of the X-axis where the height should be returned
     * @param k The offset of the Z-axis where the height should be returned
     * @return The height
     */
    public int getHeight(int i, int k) {
        return heightArray[i][k];
    }
    /**
     * Set the height of the surface on the given coordinates
     * @param i The offset of the X-axis where the height should be returned
     * @param k The offset of the Z-axis where the height should be returned
     * @param height The height to set
     */
    private void setHeight(int i, int k, int height) {
        heightArray[i][k] = height;
    }
    /**
     * Build the gradients array. Basically it is a 2D array containing the
     * gradients of each corner of each region of the heightMap
     */
    private void buildGradients() {
        gradients = new double[(int) (xSize / frequency) + 1 + xSize][(int) (zSize / frequency)
                + 1 + zSize];
        for (int i = 0; i < gradients.length; i++)
            for (int k = 0; k < gradients[0].length; k++)
                gradients[i][k] = Math.random();
    }
    /**
     * Compute the distance between two dots in a plane
     * @param x1 The offset of the first dot on the x-axis
     * @param z1 The offset of the first dot on the z-axis
     * @param x2 The offset of the second dot on the x-axis
     * @param z2 The offset of the second dot on the z-axis
     * @return The distance
     */
    private double planDistance(int x1, int z1, int x2, int z2) {
        int absDeltaX = Math.abs(x1 - x2);
        int absDeltaZ = Math.abs(z1 - z2);
        return Math.sqrt(Math.pow(absDeltaX, 2) + (Math.pow(absDeltaZ, 2)));
    }
    /**
     * Compute the height of a given spot on the map following an algorithm
     * almost similar to the perlin algorithm
     * @param i The offset of the spot on the x-axis
     * @param k The offset of the spot on the z-axis
     * @return The wanted height
     */
    private int computeHeight(int i, int k) {
        int gradientsX = i / frequency;
        int gradientsZ = k / frequency;
        int height = 0;
        for (int counti = 0; counti <= 1; counti++)
            for (int countk = 0; countk <= 1; countk++) {
                int gradX = gradientsX + counti;
                int gradZ = gradientsZ + countk;
                double dist = frequency
                        - planDistance(i, k, gradX * frequency, gradZ
                        * frequency);
                height += yMin + dist * gradients[gradX][gradZ];
            }
        if (height > yMax)
            height = yMax;
        return height;

    }

    /**
     * Generate the whole heightMap
     */
    private void perlin() {
        for (int i = 0; i < xSize; i++)
            for (int k = 0; k < zSize; k++) {
                int height = computeHeight(i, k);
                setHeight(i, k, height);
            }

    }
    /**
     * Apply something like an interpolation on the heightmap. Very basic
     * @param radius The radius around every block the algorithm
     *               should interpolate
     * @param iterations The number of times the algorithm is applied to the
     *                   heightmap
     */
    private void smoothenSurface(int radius, int iterations) {
        for (int count = 0; count < iterations; count++)
            for (int i = 0; i < xSize; i++)
                for (int k = 0; k < zSize; k++) {
                    int coef = 0;
                    for (int di = radius * -1; di < radius; di++)
                        for (int dk = radius * -1; dk < radius; dk++) {
                            if (i + di > 0 && i + di < xSize && k + dk > 0
                                    && k + dk < zSize) {
                                if (getHeight(i, k) < getHeight(i+di, k+dk))
                                    coef++;
                                else if (getHeight(i, k) > getHeight(i+di, k+dk))
                                    coef--;
                            }
                        }
                    int formerHeight = getHeight(i, k);
                    if (coef > 0)
                        setHeight(i, k, formerHeight + 1);
                    else if (coef < 0)
                        setHeight(i, k, formerHeight - 1);
                }
    }
}
