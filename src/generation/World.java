package generation;

import Main.GlobalParameters;
import enumerations.BlockType;
import generation.chunks.Chunk3DCollection;
import generation.chunks.ChunkDoesNotExistException;
import rendering.ChunkRenderer;
import rendering.nodeManagers.Chunk3DNodeManager;
import rendering.nodeManagers.WorldNodeManager;
import utils.Couple;
import utils.Position2D;
import utils.Position3D;

public class World {

    /**
     * The minimum coordinates of the world (we assume that the world can not
     * have holes)
     */
    private Position3D minCoordinates = new Position3D(0, 0, 0);
    /**
     * The maximum coordinates of the world (we assume that the world can not
     * have holes)
     */
    private Position3D maxCoordinates = new Position3D(0, 0, 0);
    /**
     * Collection of chunks of the world
     */
    private Chunk3DCollection chunks;
    /**
     * The heightmap of the world
     */
    private HeightMap heightMap;
    /**
     * The attaching and detaching node manager
     */
    WorldNodeManager worldNodeManager;


    /**
     * Create a world
     * @param chunkSize The size of the chunks
     */
    public World(int chunkSize){
        chunks = new Chunk3DCollection(chunkSize);
        worldNodeManager = new WorldNodeManager("world base node", chunkSize,
                chunks);
        Chunk3DNodeManager.setParentNode(worldNodeManager.getNode());
        heightMap = new HeightMap(GlobalParameters.heightGradientsInterval,
                new Couple<>(GlobalParameters.minHeight,
                        GlobalParameters.maxHeight));
    }


    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the X axis (negative side)
     * @return The distance
     */
    public int xMin(){return this.minCoordinates.getX();}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Y axis (negative side)
     * @return The distance
     */
    public int yMin(){return this.minCoordinates.getY();}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Z axis (negative side)
     * @return The distance
     */
    public int zMin(){return this.minCoordinates.getZ();}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the X axis (positive side)
     * @return The distance
     */
    public int xMax(){return this.maxCoordinates.getX();}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Y axis (positive side)
     * @return The distance
     */
    public int yMax(){return this.maxCoordinates.getY();}
    /**
     * Get the maximum distance from the origin where blocks have been
     * generated on the Z axis (positive side)
     * @return The distance
     */
    public int zMax(){return this.maxCoordinates.getZ();}

    /**
     * Get the chunks collection of the world
     * @return The chunks collection
     */
    public Chunk3DCollection getChunks(){
        return chunks;
    }

    /**
     * Give the type the block at the given position should be
     * @param position The position of the block
     * @return The type of the block
     */
    private BlockType blockTypeToSet(Position3D position){
        Position2D horizontalProjection = new Position2D(
                position.getX(), position.getZ());
        int j = position.getY();

        double height = heightMap.getHeight(horizontalProjection);
        if(j == height - 1)
            return BlockType.GRASS;
        else if(j < height -1)
            return BlockType.DIRT;
        else
            return BlockType.AIR;

    }
    /**
     * Get the block at the given coordinates
     * @param position The position of the block to get
     * @return The type of the block
     */
    public Block getBlock(Position3D position){
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        if(x < xMin() || xMax() < x ||
                y < yMin() || yMax() < y ||
                z < zMin() || zMax() < z)
            return new Block(position, BlockType.OUT_OF_BOUNDS);
        else
            try {
                return chunks.getChunkOfBlockAt(position).get(position);
            } catch (ChunkDoesNotExistException e) {
                return new Block(new Position3D(0, 0, 0),
                        BlockType.OUT_OF_BOUNDS);
            }
    }
    /**
     * Set the block type at the given coordinates
     * @param position The position of the block to modify
     * @param blockType The type of the block to set
     */
    public void setBlock(Position3D position, BlockType blockType) throws BlockDoesNotExistException, ChunkDoesNotExistException {
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        if(xMin() <= x && x <= xMax()
                && yMin() <= y && y <= yMax()
                && zMin() <= z && z <= zMax())
            chunks.getChunkOfBlockAt(position).set(position,
                    new Block(position, blockType));
        else throw  new BlockDoesNotExistException("Trying to modify the type of a " +
                "block that does not exist at " + position.toString());
    }

    /**
     * Set the block at the given coordinates according to the heightMap
     * @param position The position of the block to set
     *
     */
    public void setBlock(Position3D position) throws BlockDoesNotExistException, ChunkDoesNotExistException {
        setBlock(position, blockTypeToSet(position));
    }


    /**
     * Get the node manager of the world
     * @return The node manager
     */
    public WorldNodeManager getNodeManager(){
        return worldNodeManager;
    }

    private void updateMinAndMaxCoordinates(Position3D position){
        minCoordinates.setX(Math.min(xMin(), position.getX()));
        minCoordinates.setY(Math.min(yMin(), position.getY()));
        minCoordinates.setZ(Math.min(zMin(), position.getZ()));
        maxCoordinates.setX(Math.max(xMin(), position.getX()));
        maxCoordinates.setY(Math.max(yMin(), position.getY()));
        maxCoordinates.setZ(Math.max(zMin(), position.getZ()));
    }


    /**
     * Check in the cube centered on position and of 2*radius chunks of
     * size if the chunks have been generated. If not, they are generated.
     * It actually generate the blocks of the distance of one more chunk around
     * the cube and only prepare the displaying of the chunks inside the cube
     * . We do that because to prepare a chunk to displaying, he surrounding
     * chunks need to have already been generated (because of BlockRenderer
     * .isQuadNeeded)
     * @param camLocation The center of the cube
     * @param radius Half the size of the cube
     */
    public void generateChunksAround(Position3D camLocation, int radius) {
        int chunkSize = chunks.getSize();
        int genRadius = radius + 2;
        int camXChunk = camLocation.getX() / chunkSize;
        int camYChunk = camLocation.getY() / chunkSize;
        int camZChunk = camLocation.getZ() / chunkSize;
        // These loops are on chunks in the radius
        for (int i = camXChunk - genRadius; i < camXChunk + genRadius; i++)
            for (int j = camYChunk - genRadius; j < camYChunk + genRadius;
                 j++)
                for (int k = camZChunk - genRadius; k < camZChunk + genRadius;
                     k++) {
                    Position3D currChunkPos = new Position3D(i, j, k);
                    if (!chunks.hasBeenGenerated(currChunkPos)) {
                        chunks.addChunk(currChunkPos);
                        generateChunk(currChunkPos);
                    }
                }
    }

    /**
     * Generate a chunk.
     * @param chunkPos The position of the chunk to generate
     */
    private void generateChunk(Position3D chunkPos){
        int chunkSize = chunks.getSize();
        int xStart = chunkPos.getX() * chunkSize;
        int yStart = chunkPos.getY() * chunkSize;
        int zStart = chunkPos.getZ() * chunkSize;
        for (int i = xStart; i < xStart + chunkSize; i++)
            for (int j = yStart; j < yStart + chunkSize; j++)
                for (int k = zStart; k < zStart + chunkSize; k++) {
                    Position3D currBlockPos = new Position3D( i, j, k);
                    Position2D currBlockPosProjection = new Position2D(i, k);
                    updateMinAndMaxCoordinates(currBlockPos);
                    heightMap.getHeight(currBlockPosProjection);
                    try {
                        setBlock(currBlockPos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        try {
            ChunkRenderer.preDisplay(chunks.getChunk(chunkPos));
        } catch (ChunkDoesNotExistException e) {
            e.printStackTrace();
        }
    }




    /**
     * The toString overridden method
     * @return A string representing the world
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < this.xMax(); i++){
            for(int j = this.yMax()-1 ; j >= 0; j--){
                for(int k = 0; k < this.zMax(); k++){
                    char charToPrint;
                    BlockType btype = this.getBlock(
                            new Position3D(i, j, k)).getBlockType();
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