package GlobalPackage;

public class World {

	// Enum of every blocktype
	public static enum BlockType {AIR, GRASS, DIRT, STONE, WATER, OUT_OF_BOUNDS};

	// Matrix containing blocks. Core of the world
	public static BlockType[][][] matrix;

	
	

	// Test constructor
	public World(int x, int y, int z){
		matrix = new BlockType[x][y][z];
		// heightMap -> height of the surface of the world
		HeightMap heightMap = new HeightMap(0, x, 1, y, 0, z);

		for(int i = 0; i < x; i++)
			for(int k = 0; k < z; k++){
				for(int j = 0; j < y; j++){
					int height = heightMap.getHeight(i, k);
					int dirtThickness = (int)(Math.random()*4);
					if(j == height - 1)
						this.setBlock(i, j, k, BlockType.GRASS);

					else if(j < height - 1 && j > height - 1 - dirtThickness)
						this.setBlock(i, j, k, BlockType.DIRT);

					else if(j < height - 1 - dirtThickness)
						this.setBlock(i, j, k, BlockType.STONE);

					else 
						this.setBlock(i, j, k, BlockType.AIR);
				}
			}
	}

	// Width of the world
	public int xSize(){ return matrix.length;}

	// Length of the world
	public int ySize(){ return matrix[0].length;} 

	// Height of the world
	public int zSize(){ return matrix[0][1].length;} 

	// Get a block on the given coordinates
	public BlockType getBlock(int x, int y, int z){
		if(x < 0 || x >= this.xSize() ||
				y < 0 || y >= this.ySize() ||
				z < 0 || z >= this.zSize())
			return BlockType.OUT_OF_BOUNDS;
		else return matrix[x][y][z];
	}



	// Set a block on the given coordinates
	public void setBlock(int x, int y, int z, BlockType block){
		matrix[x][y][z] = block;
	}



	// Display letters representing the world in the debug console
	@Override public String toString() {
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < this.xSize(); i++){
			for(int j = this.ySize()-1 ; j >= 0; j--){
				for(int k = 0; k < this.zSize(); k++){
					char charToPrint;
					BlockType btype = this.getBlock(i, j, k);
					switch(btype){
					case AIR:
						charToPrint = 'A';
						break;
					case GRASS:
						charToPrint = 'G';
						break;
					case DIRT:
						charToPrint = 'D';
						break;
					case STONE:
						charToPrint = 'S';
						break;
					case WATER:
						charToPrint = 'W';
						break;
					default:
						charToPrint = 'X';
					}
					result.append(charToPrint + " ");
				}
				result.append("\n");
			}
			result.append("\n");
		}
		return result.toString();
	}
}