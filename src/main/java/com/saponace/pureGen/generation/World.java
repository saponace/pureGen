package com.saponace.pureGen.generation;

import com.saponace.pureGen.Main.GlobalParameters;
import com.saponace.pureGen.enumerations.BlockType;
import com.saponace.pureGen.generation.chunks.Chunk3DCollection;
import com.saponace.pureGen.generation.chunks.ChunkDoesNotExistException;
import com.saponace.pureGen.rendering.ChunkRenderer;
import com.saponace.pureGen.rendering.nodeManagers.Chunk3DNodeManager;
import com.saponace.pureGen.rendering.nodeManagers.WorldNodeManager;
import com.saponace.pureGen.utils.Couple;
import com.saponace.pureGen.utils.Position2D;
import com.saponace.pureGen.utils.Position3D;

public class World {

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
     *
     * @param chunkSize The size of the chunks
     */
    public World(int chunkSize) {
        chunks = new Chunk3DCollection(chunkSize);
        worldNodeManager = new WorldNodeManager("world base node", chunkSize,
                chunks);
        Chunk3DNodeManager.setParentNode(worldNodeManager.getNode());
        heightMap = new HeightMap(GlobalParameters.heightGradientsInterval,
                new Couple<>(GlobalParameters.minHeight,
                        GlobalParameters.maxHeight));
    }


    /**
     * Get the chunks collection of the world
     *
     * @return The chunks collection
     */
    public Chunk3DCollection getChunks() {
        return chunks;
    }

    /**
     * Give the type the block at the given position should be
     *
     * @param position The position of the block
     * @return The type of the block
     */
    private BlockType blockTypeToSet(Position3D position) {
        Position2D horizontalProjection = new Position2D(
                position.getX(), position.getZ());
        int j = position.getY();

        double height = heightMap.getHeight(horizontalProjection);
        System.out.println(j + " " + " " + height);
        if (j == height - 1)
            return BlockType.GRASS;
        else if (j < height - 1)
            return BlockType.DIRT;
        else
            return BlockType.AIR;
    }

    /**
     * Get the block at the given coordinates
     *
     * @param position The position of the block to get
     * @return The type of the block. If the block does no exist yet (chunk
     * not generated) return a block of type NONE at position (0, 0, 0)
     */
    public Block getBlock(Position3D position) {
        try {
            return chunks.getChunkOfBlockAt(position).get(position);
        } catch (ChunkDoesNotExistException e) {
            return new Block(new Position3D(0, 0, 0),
                    BlockType.NONE);
        }
    }

    /**
     * Set the block type at the given coordinates
     *
     * @param position  The position of the block to modify
     * @param blockType The type of the block to set
     */
    public void setBlock(Position3D position, BlockType blockType) throws BlockDoesNotExistException, ChunkDoesNotExistException {
        chunks.getChunkOfBlockAt(position).set(position,
                new Block(position, blockType));
    }

    /**
     * Set the block at the given coordinates according to the heightMap
     *
     * @param position The position of the block to set
     */
    public void setBlock(Position3D position) throws BlockDoesNotExistException, ChunkDoesNotExistException {
        setBlock(position, blockTypeToSet(position));
    }


    /**
     * Get the node manager of the world
     *
     * @return The node manager
     */
    public WorldNodeManager getNodeManager() {
        return worldNodeManager;
    }


    /**
     * Check in the cube centered on position and of 2*radius chunks of
     * size if the chunks have been generated. If not, they are generated.
     * It actually generate the blocks of the distance of one more chunk around
     * the cube and only prepare the displaying of the chunks inside the cube
     * . We do that because to prepare a chunk to displaying, he surrounding
     * chunks need to have already been generated (because of BlockRenderer
     * .isQuadNeeded)
     *
     * @param camLocation The center of the cube
     * @param radius      Half the size of the cube
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
     *
     * @param chunkPos The position of the chunk to generate
     */
    private void generateChunk(Position3D chunkPos) {
        int chunkSize = chunks.getSize();
        int xStart = chunkPos.getX() * chunkSize;
        int yStart = chunkPos.getY() * chunkSize;
        int zStart = chunkPos.getZ() * chunkSize;
        for (int i = xStart; i < xStart + chunkSize; i++)
            for (int j = yStart; j < yStart + chunkSize; j++)
                for (int k = zStart; k < zStart + chunkSize; k++) {
                    Position3D currBlockPos = new Position3D(i, j, k);
                    Position2D currBlockPosProjection = new Position2D(i, k);
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
}
