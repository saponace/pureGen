package com.saponace.pureGen.generation.chunks;



import com.saponace.pureGen.utils.Position2D;

import java.util.HashMap;

public class Chunk2DCollection<T> {
    /**
     * Hashmap that contains chunks
     */
    private HashMap<Position2D, Chunk2D<T>>
            chunksHashMap = new HashMap<>();
    /**
     * The size of the chunks (length unit : cube side length)
     */
    private int chunkSize;

    /**
     * Create an instance of chunks collection
     * @param chunkSize The size of the chunks (unit : blocks)
     */
    public Chunk2DCollection(int chunkSize){
        this.chunkSize = chunkSize;
    }


    /**
     * Get the position of the chunk the block at the given position is.
     * @param pointPosition The position of the point
     * @return A triplet representing the position of the chunk in the world
     * (in chunks units, not in block units)
     */
    public Position2D chunkPositionOfBlockAt(Position2D pointPosition){
        return new Position2D(pointPosition.getX() / chunkSize,
        pointPosition.getY() / chunkSize);
    }


    /**
     * Get the chunk in which the block at given position is
     * @param chunkPos The position of the chunk
     * @return The chunk (or null, if no chunk exist at this position)
     */
    public Chunk2D<T> getChunk(Position2D chunkPos) {
        if (chunksHashMap.containsKey(chunkPos))
            return chunksHashMap.get(chunkPos);
        else
            return null;
    }

    /**
     * Add an empty chunk in the collection
     * @param chunkPos The position of the chunk to add.
     */
    private void addChunk(Position2D chunkPos){
        if(getChunk(chunkPos) != null)
            throw new RuntimeException("Trying to create a chunk where an " +
                    "actual chunk already exists (position: " + chunkPos
                    .toString() + ")");
        else
            chunksHashMap.put(chunkPos, new Chunk2D<T>(chunkSize, chunkPos));
    }
}
