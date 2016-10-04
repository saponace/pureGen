package com.saponace.pureGen.generation.chunks;



import com.saponace.pureGen.Main.DebugUtils;
import com.saponace.pureGen.utils.Position3D;

import java.util.HashMap;

public class Chunk3DCollection {
    /**
     * Hashmap that contains chunks
     */
    private HashMap<Position3D, Chunk3D>
            chunksHashMap = new HashMap<>();
    /**
     * The size of the chunks (length unit : cube side length)
     */
    private int chunkSize;

    /**
     * Create an instance of chunks collection
     * @param chunkSize The size of the chunks (unit : blocks)
     */
    public Chunk3DCollection(int chunkSize){
        this.chunkSize = chunkSize;
    }


    /**
     * Get the position of the chunk the block at the given position is.
     * @param blockPosition The position of the point
     * @return A triplet representing the position of the chunk in the world
     * (in chunks units, not in block units)
     */
    public Position3D chunkPositionOfBlockAt(Position3D blockPosition){
        int xChunk = (int) Math.floor((double)blockPosition.getX() / chunkSize);
        int yChunk = (int) Math.floor((double)blockPosition.getY() / chunkSize);
        int zChunk = (int) Math.floor((double)blockPosition.getZ() / chunkSize);
        Position3D chunkPos =
                new Position3D(xChunk, yChunk, zChunk);
        return chunkPos;
    }


    /**
     * Get the chunk in which the block at given position is
     * @param chunkPos The position of the chunk
     * @return The chunk
     */
    public Chunk3D getChunk(Position3D chunkPos) throws ChunkDoesNotExistException {
        if(hasBeenGenerated(chunkPos))
            return chunksHashMap.get(chunkPos);
        else
            throw new ChunkDoesNotExistException("Trying to get an chunk that has not " +
                    "been generated yet (position: " + chunkPos.toString() +
                    ")");
    }

    /**
     * Get the chunk containing the block at the given location
     * @param blockPos The position of the block
     * @return The chunk
     */
    public Chunk3D getChunkOfBlockAt(Position3D blockPos) throws ChunkDoesNotExistException {
        return getChunk(chunkPositionOfBlockAt(blockPos));
    }


    /**
     * Add an empty chunk in the collection
     * @param chunkPos The position of the chunk to add.
     */
    public void addChunk(Position3D chunkPos){
        if(hasBeenGenerated(chunkPos))
            throw new RuntimeException("Trying to create a chunk where an " +
                    "actual chunk already exists (position: " + chunkPos
                    .toString() + ")");
        else{
            chunksHashMap.put(chunkPos, new Chunk3D(chunkSize, chunkPos));
            DebugUtils.printDebug("Chunk at " + chunkPos.toString() + " " +
                    "created");
        }
    }

    /**
     * Tells whether the chunk at the given position has already been
     * generated or not
     * @param chunkPos The position of the hypothetical chunk
     * @return The wanted boolean
     */
    public boolean hasBeenGenerated(Position3D chunkPos){
        return chunksHashMap.containsKey(chunkPos);
    }



    /**
     * Get the size of the chunks
     * @return The size of the chunks
     */
    public int getSize() {
        return chunkSize;
    }


}
