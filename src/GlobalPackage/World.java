package GlobalPackage;

public class World {

	public final static int GRASS = 0;
	public final static int WATER = 0;
	
	public static int[][][] matrix;
	
	public int xSize(){ return matrix.length;}
	public int ySize(){ return matrix[0].length;} 
	public int zSize(){ return matrix[0][1].length;} 
	


	public World(int x, int y, int z){
		matrix = new int[x][y][z];

       for(int i = 0; i < x; i+=1){
            for(int j = 0; j < y; j+=1){
            	for(int k = 0; k < z; k+=1){
            		System.out.printf(" " + this.setBlock(i, j, k, i));
            	}
            }
        }
	}
	
	public int getBlock(int x, int y, int z){
		return matrix[x][y][z];
	}

	public int setBlock(int x, int y, int z, int blockType){
		return matrix[x][y][z] = blockType;
	}

	public void printInConsole(){
		for(int i = 0; i < this.xSize(); i+=1){
			for(int j = 0; j < this.ySize(); j+=1){
				for(int k = 0; k < this.zSize(); k+=1)
					System.out.printf(this.getBlock(i, j, k) + " ");
				System.out.printf("\n");
			}
			System.out.printf("\n");
		}
	}
}