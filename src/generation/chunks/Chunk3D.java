package generation.chunks;


import utils.CoupleOfInt;
import utils.TripletOfInt;

import java.util.ArrayList;

public class Chunk3D<T>{
    /**
     * The size of the chunks (length unit : cube side length)
     */
    private int size;
    /**
     * The list containing 'size' Chunk2D. The chunk2D are vertical
     * layers in the x-y axis pane following the z axis
     */
    private ArrayList<Chunk2D<T>> data3DMatrix;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : chunks)
     */
    private TripletOfInt offsetInChunks;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : blocks)
     */
    private TripletOfInt offsetInBlocks;


    /**
     * Create an instance of Chunk3D
     * @param size The size of the chunk
     * @param offsetInChunks The offset of the chunk in the world
     */
    public Chunk3D(int size, TripletOfInt offsetInChunks){
        this.size = size;
        this.offsetInChunks = offsetInChunks;
        this.offsetInBlocks = new TripletOfInt(
                offsetInChunks.getFirst() / this.size,
                offsetInChunks.getSecond() / this.size,
                offsetInChunks.getThird() / this.size);
        data3DMatrix = new ArrayList<>(size);
    }


    /**
     * Return a couple representing the position of the dot projected on the
     * xy plane
     * @param position3D The position of the dot in the 3D space
     * @return a couple representing the position of the dot projected on the
     * xy plane
     */
    private CoupleOfInt getCoupleVerticalPosition(TripletOfInt position3D){
        return new CoupleOfInt(position3D.getFirst(),
                position3D.getSecond());
    }


    /**
     * Set one element in the chunk
     * @param position The position of the element to set
     * @param data The value to assign to this element
     */
    public void set(TripletOfInt position, T data) {
        CoupleOfInt verticalPosition = getCoupleVerticalPosition(position);
        data3DMatrix.get(position.getThird() - offsetInBlocks.getThird())
                .set(verticalPosition, data);
    }
    /**
     * Get one element in the chunk
     * @param position The position of the element to get
     */
    public T get(TripletOfInt position) {
        CoupleOfInt verticalPosition = getCoupleVerticalPosition(position);
        return data3DMatrix.get(position.getThird() - offsetInBlocks.getThird())
                .get(verticalPosition);
    }
    /**
     * Tells whether a point is in the chunk or not
     * @param position The position of the point
     * @return A boolean telling if yes or not the point is in the chunk
     */
    public boolean contains(TripletOfInt position){
        return (position.getFirst() > offsetInBlocks.getFirst()
                && position.getFirst() < offsetInBlocks.getFirst() + this.size
                && position.getSecond() > offsetInBlocks.getSecond()
                && position.getSecond() < offsetInBlocks.getSecond() + this.size
                && position.getThird() > offsetInBlocks.getThird()
                && position.getThird() < offsetInBlocks.getThird() + this.size);
    }
}
