package generation.chunks;


import utils.CoupleOfInt;

import java.util.ArrayList;

public class Chunk2D<T>{
    /**
     * The size of the chunks (length unit : cube side length)
     */
    private int size;
    /**
     * The 2D array containing the data
     */
    private ArrayList<ArrayList<T>> dataMatrix;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : chunks)
     */
    private CoupleOfInt offsetInChunks;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : blocks)
     */
    private CoupleOfInt offsetInBlocks;


    /**
     * Create an instance of Chunk2D
     * @param size The size of the chunk
     * @param offset The offset of the chunk in the world
     */
    public Chunk2D(int size, CoupleOfInt offsetInChunks){
        this.size = size;
        this.offsetInChunks = offsetInChunks;
        this.offsetInBlocks = new CoupleOfInt(
                offsetInChunks.getFirst() / this.size,
                offsetInChunks.getSecond() / this.size);

        this.dataMatrix = new ArrayList<>(size);
        for(ArrayList<T> subList : this.dataMatrix)
            subList = new ArrayList<>(size);
    }


    /**
     * Set one element in the chunk
     * @param position The position of the element to set
     * @param data The value to assign to this element
     */
    public void set(CoupleOfInt position, T data) {
        this.dataMatrix.get(position.getFirst() - offsetInBlocks.getFirst())
                .set(position.getSecond() - offsetInBlocks.getSecond(), data);
    }
    /**
     * Get one element in the chunk
     * @param position The position of the element to get
     */
    public T get(CoupleOfInt position) {
        return dataMatrix.get(position.getFirst() - offsetInBlocks.getFirst())
                .get(position.getSecond() - offsetInBlocks.getSecond());
    }


    /**
     * Tells whether a point is in the chunk or not
     * @param position The position of the point
     * @return A boolean telling if yes or not the point is in the chunk
     */
    public boolean contains(CoupleOfInt position){
        return (position.getFirst() > offsetInBlocks.getFirst()
                && position.getFirst() < offsetInBlocks.getFirst() + this.size
                && position.getSecond() > offsetInBlocks.getSecond()
                && position.getSecond() < offsetInBlocks.getSecond() + this.size);
    }
}
