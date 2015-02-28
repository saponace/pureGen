package GlobalPackage;

public class Dynamic2DArray {

	private int rows;
	private int cols;
	private int[] data;

	public Dynamic2DArray(int cols, int rows) {
		this.rows = rows;
		this.cols = cols;
		data = new int[cols * rows];
	}

	private static int getIndex(int col, int row, int width) {
		return row * width + col;
	}

	public int get(int col, int row) {
		return data[getIndex(col, row, cols)];
	}

	public void set(int col, int row, int value) {
		data[getIndex(col, row, cols)] = value;
	}

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
