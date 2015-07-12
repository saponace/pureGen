package package1;

public class World {
    /**
     * Enumeration of all the block types
     */
    public static enum BlockType {AIR, GRASS, DIRT, STONE, WATER, OUT_OF_BOUNDS};
    /**
     * 3D array containing the blocks
     */
    public static BlockType[][][] matrix;


    /**
     * Test constructor
     * @param x The width of the world
     * @param y The length of the world
     * @param z The height of the world
     */
    public World(int x, int y, int z){
        matrix = new BlockType[x][y][z];
        // heightMap -> height of the surface of the world
        HeightMap heightMap = new HeightMap(0, x, 1, y, 0, z);

        for(int i = 0; i < x; i++)
            for(int k = 0; k < z; k++){
                for(int j = 0; j < y; j++){
                    int height = heightMap.getHeight(i, k);
//					int dirtThickness = (int)(Math.random()*4);
                    if(j == height - 1)
                        this.setBlock(i, j, k, BlockType.GRASS);

                    else if(j < height -1)
                        this.setBlock(i, j, k, BlockType.DIRT);

//					else if(j < height - 1 && j > height - 1 - dirtThickness)
//						this.setBlock(i, j, k, BlockType.DIRT);

//					else if(j < height - 1 - dirtThickness)
//						this.setBlock(i, j, k, BlockType.STONE);

                    else
                        this.setBlock(i, j, k, BlockType.AIR);
                }
            }
    }


    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the X axis (negative side)
     * @return The distance
     */
    public int xMin(){ return 0;}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Y axis (negative side)
     * @return The distance
     */
    public int yMin(){ return 0;}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Z axis (negative side)
     * @return The distance
     */
    public int zMin(){ return 0;}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the X axis (positive side)
     * @return The distance
     */
    public int xMax(){ return matrix.length;}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Y axis (positive side)
     * @return The distance
     */
    public int yMax(){ return matrix[0].length;}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Z axis (positive side)
     * @return The distance
     */
    public int zMax(){ return matrix[0][1].length;}
    /**
     * Get the block at the given coordinates
     * @param x The x-axis offset of the block
     * @param y The y-axis offset of the block
     * @param z The z-axis offset of the block
     * @return The type of the block
     */
    public BlockType getBlock(int x, int y, int z){
        if(x < 0 || x >= this.xMax() ||
                y < 0 || y >= this.yMax() ||
                z < 0 || z >= this.zMax())
            return BlockType.OUT_OF_BOUNDS;
        else return matrix[x][y][z];
    }
    /**
     * Set the block type at the given coordinates
     * @param x The x-axis offset of the block
     * @param y The y-axis offset of the block
     * @param z The z-axis offset of the block
     * @param block The type of the block
     */
    public void setBlock(int x, int y, int z, BlockType block){
        matrix[x][y][z] = block;
    }

    /**
     * The toString overrided method
     * @return A string representing the world
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < this.xMax(); i++){
            for(int j = this.yMax()-1 ; j >= 0; j--){
                for(int k = 0; k < this.zMax(); k++){
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