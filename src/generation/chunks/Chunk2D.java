package generation.chunks;

import utils.Couple2;

import java.util.ArrayList;

public class Chunk2D<T>{
    /**
     * The size of the chunks (length unit : cube side length)
     */
    private int chunkSize;
    /**
     * The 2D array containing the data
     */
    private ArrayList<ArrayList<T>> dataMatrix;


    /**
     * Create an instance of Chunk2D
     * @param chunkSize The size of the chunk
     */
    public Chunk2D(int chunkSize){
        this.chunkSize = chunkSize;
        this.dataMatrix = new ArrayList<>(chunkSize);
        for(ArrayList<T> subList : this.dataMatrix)
            subList = new ArrayList<>(chunkSize);
    }


    /**
     * Set one element in the chunk
     * @param position The position of the element to set
     * @param data The value to assign to this element
     */
    public void set(Couple2<Integer, Integer> position, T data) {
        this.dataMatrix.get(position.getFirst()).set(position.getSecond(), data);
    }
    /**
     * Get one element in the chunk
     * @param position The position of the element to get
     */
    public T get(Couple2<Integer, Integer> position) {
        return dataMatrix.get(position.getFirst()).get(position.getSecond());
    }



    public boolean contains(Couple2<Integer, Integer> position){
    //TODO ecrire contains
        return true;
    }


}
