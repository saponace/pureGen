//package package1;
//
//public class Dynamic3DArray {
//
//	private int minX;
//	private int maxX;
//	private int minY;
//	private int maxY;
//	private int minZ;
//	private int maxZ;
//	private int[][][] xarr;
//
//
//	public Dynamic3DArray(int xSize, int ySize, int zSize) {
//		minX = - xSize/2;
//		maxX = xSize/2;
//		minY = - ySize/2;
//		maxY = ySize/2;
//		minZ = - zSize/2;
//		maxZ = zSize/2;
//
//		xarr = new int[maxX - minX];
//		for(int i=minX; i < maxX; i++){
//			int[][] currY = new int[maxY - minY];
//			xarr[i] = currY;
//			for(int j=minY; j < maxY; j++){
//                int[] currZ = new int[maxZ - minZ];
//
//			}
//		}
//
////		data = new int[(maxX - minX) * (maxY - minY) * (maxZ - minZ)];
//	}
//
////	private static int getIndex(int col, int row, int width) {
////		return row * width + col;
////	}
//
////	public int get(int col, int row) {
////		return data[getIndex(col, row, cols)];
////	}
//
////	public void set(int col, int row, int value) {
////		data[getIndex(col, row, cols)] = value;
////	}
//
////	public void resize(int cols, int rows) {
////		int[] newData = new int[cols * rows];
////		int colsToCopy = Math.min(cols, this.cols);
////		int rowsToCopy = Math.min(rows, this.rows);
////		for (int i = 0; i < rowsToCopy; ++i) {
////			int oldRowStart = getIndex(0, i, this.cols);
////			int newRowStart = getIndex(0, i, cols);
////			System.arraycopy(data, oldRowStart, newData, newRowStart,
////					colsToCopy);
////		}
////		data = newData;
////	}
//
//}
