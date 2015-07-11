package package1;

public class HeightMap {

	private int xSize;
	private int yMin;
	private int yMax;
	private int zSize;
	private int frequency;
	private static int[][] heightArray;
	private static double[][] gradients;

	// Get the height of the surface on the given coordinates
	public int getHeight(int i, int k) {
		return heightArray[i][k];
	}	
	
	// Set the height of the surface on the given coordinates
	private void setHeight(int i, int k, int height) {
		heightArray[i][k] = height;
	}

	// Create the array giving the height of the surface of the world
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

	// Build the gradients (which is an array that gives the gradients of
	// each corner of each region of the heightmap
	private void buildGradients() {
		gradients = new double[(int) (xSize / frequency) + 1 + xSize][(int) (zSize / frequency)
				+ 1 + zSize];
		for (int i = 0; i < gradients.length; i++)
			for (int k = 0; k < gradients[0].length; k++)
				gradients[i][k] = Math.random();
	}

	// Gives the distance between two dots on a plan
	private double planDistance(int x1, int z1, int x2, int z2) {
		int absDeltaX = Math.abs(x1 - x2);
		int absDeltaZ = Math.abs(z1 - z2);
		return Math.sqrt(Math.pow(absDeltaX, 2) + (Math.pow(absDeltaZ, 2)));
	}

	// Computes the height of a given spot on the map according to Perlin
	// algorithm
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

	private void perlin() {
		for (int i = 0; i < xSize; i++)
			for (int k = 0; k < zSize; k++) {
				int height = computeHeight(i, k);
				setHeight(i, k, height);
			}

	}

	// Smoothen the heightArray (lower height differences between close
	// locations)
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
