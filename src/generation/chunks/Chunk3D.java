package generation.chunks;

import utils.Couple2;
import utils.Triplet;

public class Chunk3D<T>{
    /**
     * The size of the chunks (length unit : cube side length)
     */
    private int chunkSize;
    /**
     * The array containing 'chunkSize' Chunk2D. The chunk2D are vertical
     * layers following the z axis
     */
    private Chunk2D<T>[] data3DMatrix;


    /**
     * Create an instance of Chunk3D
     * @param chunkSize The size of the chunk
     */
    public Chunk3D(int chunkSize){
        this.chunkSize = chunkSize;
        data3DMatrix = new Chunk2D[chunkSize];
    }


    /**
     * Return a couple representing the position of the dot projected on the
     * xy plane
     * @param position3D The position of the dot in the 3D space
     * @Return a couple representing the position of the dot projected on the
     * xy plane
     */
    private Couple2 getCoupleVerticalPosition(Triplet<Integer, Integer, Integer>
                                                     position3D){
        return new Couple2(position3D.getFirst(),
                position3D.getSecond());
    }


    /**
     * Set one element in the chunk
     * @param position The position of the element to set
     * @param data The value to assign to this element
     */
    public void set(Triplet<Integer, Integer, Integer> position, T data) {
        Couple2 verticalPosition = getCoupleVerticalPosition(position);
        data3DMatrix[position.getThird()].set(verticalPosition, data);
    }
    /**
     * Get one element in the chunk
     * @param position The position of the element to get
     */
    public T get(Triplet<Integer, Integer, Integer> position) {
        Couple2 verticalPosition = getCoupleVerticalPosition(position);
        return data3DMatrix[position.getThird()].get(verticalPosition);
    }
}
