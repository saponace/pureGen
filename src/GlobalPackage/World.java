package GlobalPackage;

public class World {

	// Enum of every blocktype
	public static enum BlockType {AIR, GRASS, DIRT, STONE, WATER, OUT_OF_BOUNDS};
	
	// Matrix containing blocks. Core of the world
	public static BlockType[][][] matrix;
	
	// Width of the world
	public int xSize(){ return matrix.length;}

	// Length of the world
	public int ySize(){ return matrix[0].length;} 

	// Height of the world
	public int zSize(){ return matrix[0][1].length;} 
	


	// Test constructor
	public World(int x, int y, int z){
		matrix = new BlockType[x][y][z];
		for(int i = 0; i < x; i+=1)
			for(int j = 0; j < y; j+=1)
				for(int k = 0; k < z; k+=1){
					if(j<6)
						this.setBlock(i, j, k, BlockType.STONE);
					else if(j<9)
						this.setBlock(i, j, k, BlockType.DIRT);
					else
						this.setBlock(i, j, k, BlockType.GRASS);

				}
	}
	
	
	
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


	
	// Display letters representing the world in te debug console
	public void printInConsole(){
		for(int i = 0; i < this.xSize(); i+=1){
			for(int j = 0; j < this.ySize(); j+=1){
				for(int k = 0; k < this.zSize(); k+=1){
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
					System.out.printf(charToPrint + " ");
				}
				System.out.printf("\n");
			}
			System.out.printf("\n");
		}
	}
}