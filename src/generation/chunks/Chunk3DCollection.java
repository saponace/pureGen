package generation.chunks;



import utils.CoupleOfInt;
import utils.TripletOfInt;

import java.util.HashMap;

public class Chunk3DCollection<T> {
    /**
     * Hashmap that contains chunks
     */
    private HashMap<TripletOfInt, Chunk3D<T>>
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
     * @param pointPosition The position of the point
     * @return A triplet representing the position of the chunk in the world
     * (in chunks units, not in block units)
     */
    public TripletOfInt getChunkPosOf(TripletOfInt pointPosition){
        TripletOfInt chunkPos =
                new TripletOfInt(pointPosition.getFirst() / chunkSize,
                pointPosition.getSecond() / chunkSize,
                pointPosition.getThird() / chunkSize);
        return chunkPos;
    }


    /**
     * Get the chunk in which the block at given position is
     * @param i The x axis of the block
     * @param k The z axis of the block
     * @return The chunk (or null, if no chunk exist at this position)
     */
    public Chunk3D<T> getChunk(TripletOfInt chunkPos) {
        if (chunksHashMap.containsKey(chunkPos))
            return chunksHashMap.get(chunkPos);
        else
            return null;
    }

    private void addChunk(TripletOfInt chunkPos){
        chunksHashMap.put(chunkPos, new Chunk3D<T>(chunkSize, chunkPos));
    }
}
