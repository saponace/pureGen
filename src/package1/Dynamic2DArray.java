package package1;

public class Dynamic2DArray {
    /**
     * The amount of columns of the 2D array
     */
	private int cols;
    /**
     * The amount of rows of the 2D array
     */
    private int rows;
    /**
     * The Java array containing the actual data
     */
	private int[] data;


    /**
     * Create an instance of Dynamic2DArray
     * @param cols The number of columns of the 2D array
     * @param rows The number of rows of the 2D array
     */
	public Dynamic2DArray(int cols, int rows) {
		this.rows = rows;
		this.cols = cols;
		data = new int[cols * rows];
	}


    /**
     * Get the position of the data is the actual 1D array containing the data
     * @param col The virtual column of the element
     * @param row The virtual row of the element
     * @param width The width of the array
     * @return The offset of the element in the 1D array that actually store the data
     */
	private static int getIndex(int col, int row, int width) {
		return row * width + col;
	}
    /**
     * Get the data at the given location
     * @param col The column of the data
     * @param row The row of the data
     * @return The data at the given location
     */
	public int get(int col, int row) {
		return data[getIndex(col, row, cols)];
	}
    /**
     * Set the data at the given location
     * @param col The column of the data
     * @param row The row of the data
     * @param value The data to set at the given location
     */
	public void set(int col, int row, int value) {
		data[getIndex(col, row, cols)] = value;
	}
    /**
     * Resize the 2D array
     * @param cols The new number of columns of the array
     * @param rows The new number of rows of the array
     */
	public void resize(int cols, int rows) {
		int[] newData = new int[cols * rows];
		int colsToCopy = Math.min(cols, this.cols);
		int rowsToCopy = Math.min(rows, this.rows);
		for (int i = 0; i < rowsToCopy; ++i) {
			int oldRowStart = getIndex(0, i, this.cols);
			int newRowStart = getIndex(0, i, cols);
			System.arraycopy(data, oldRowStart, newData, newRowStart,
					colsToCopy);
		}
		data = newData;
	}
}
