package com.saponace.pureGen.generation.chunks;


import com.saponace.pureGen.utils.Position2D;

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
    private Position2D offsetInChunks;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : blocks)
     */
    private Position2D offsetInBlocks;


    /**
     * Create an instance of Chunk2D
     * @param size The size of the chunk
     * @param offsetInChunks The offset of the chunk in the world (in chunks,
     *                       not blocks)
     */
    public Chunk2D(int size, Position2D offsetInChunks){
        this.size = size;
        this.offsetInChunks = offsetInChunks;
        this.offsetInBlocks = new Position2D(
                offsetInChunks.getX() * this.size,
                offsetInChunks.getY() * this.size);

        this.dataMatrix = new ArrayList<>(size);
        for(int i=0; i < size; i++){
            ArrayList<T> subList = new ArrayList<>(size);
            for(int j=0; j < size; j++)
                subList.add(null);
            dataMatrix.add(subList);
        }
    }


    /**
     * Set one element in the chunk
     * @param position The position of the element to set (absolute position
     *                 in the world, independent from the position of the chunk)
     * @param data The value to assign to this element
     */
    public void set(Position2D position, T data) {
        this.dataMatrix.get(position.getX() - offsetInBlocks.getX())
                .set(position.getY() - offsetInBlocks.getY(), data);
    }
    /**
     * Get one element in the chunk
     * @param position The position of the element to get
     */
    public T get(Position2D position) {
        T data = dataMatrix.get(position.getX() - offsetInBlocks.getX())
                .get(position.getY() - offsetInBlocks.getY());
        if (data != null)
            return data;
        else
            throw new RuntimeException("There is no block at " + position
                    .toString() + " but there should be a block here");
    }


    /**
     * Tells whether a point is in the chunk or not
     * @param position The position of the point
     * @return A boolean telling if yes or not the point is in the chunk
     */
    public boolean contains(Position2D position){
        return (position.getX() > offsetInBlocks.getX()
                && position.getX() < offsetInBlocks.getX() + this.size
                && position.getY() > offsetInBlocks.getY()
                && position.getY() < offsetInBlocks.getY() + this.size);
    }
}
