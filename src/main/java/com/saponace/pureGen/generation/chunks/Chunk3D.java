package com.saponace.pureGen.generation.chunks;


import com.saponace.pureGen.generation.chunks.Chunk2D;
import com.saponace.pureGen.enumerations.BlockType;
import com.saponace.pureGen.generation.Block;
import com.saponace.pureGen.rendering.nodeManagers.Chunk3DNodeManager;
import com.saponace.pureGen.utils.Position2D;
import com.saponace.pureGen.utils.Position3D;

import java.util.ArrayList;

public class Chunk3D{
    /**
     * The size of the chunk (how many blocks long are the side of the chunk)
     */
    private int size;
    /**
     * The list containing 'size' Chunk2D. The chunk2D are vertical
     * layers in the x-y axis pane following the z axis
     */
    private ArrayList<Chunk2D<Block>> data3DMatrix;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : chunks)
     */
    private Position3D offsetInChunks;
    /**
     * Offset of the chunk on the x axis, y axis, and z axis (unit : blocks)
     */
    private Position3D offsetInBlocks;
    /**
     * The attaching and detaching node manager
     */
    private Chunk3DNodeManager chunk3DNodeManager;


    /**
     * Create an instance of Chunk3D
     * @param size The size of the chunk
     * @param offsetInChunks The offset of the chunk in the world
     */
    public Chunk3D(int size, Position3D offsetInChunks){
        this.size = size;
        this.offsetInChunks = offsetInChunks;
        this.offsetInBlocks = new Position3D(
                offsetInChunks.getX() * this.size,
                offsetInChunks.getY() * this.size,
                offsetInChunks.getZ() * this.size);
        data3DMatrix = new ArrayList<>(size);
        for(int i=0; i < size; i++)
            data3DMatrix.add(new Chunk2D<Block>(size, new Position2D
                    (offsetInChunks.getX(), offsetInChunks.getY())));
        chunk3DNodeManager = new Chunk3DNodeManager("chunk " +
                offsetInChunks.toString());
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size; k++){
                    Position3D positionInWorld = new Position3D(
                            i + offsetInBlocks.getX(),
                            j + offsetInBlocks.getY(),
                            k + offsetInBlocks.getZ());
                    Block blockToSet = new Block(
                            positionInWorld,
                            BlockType.NONE);
                    set(positionInWorld, blockToSet);
                }
    }


    /**
     * Get the node manager attached to the chunk
     * @return The node manager
     */
    public Chunk3DNodeManager getChunk3DNodeManager() {
        return chunk3DNodeManager;
    }

    /**
     * Return a couple representing the position of the dot projected on the
     * xy plane
     * @param position3D The position of the block (absolute position
     *                 in the world, not in the chunk)
     * @return a couple representing the position of the dot projected on the
     * xy plane
     */
    private Position2D getCoupleVerticalPosition(Position3D position3D){
        return new Position2D(position3D.getX(), position3D.getY());
    }


    /**
     * Get the size of the chunk (how many blocks long are the side of the chunk)
     * @return the size of the chunk
     */
    public int getSize() {
        return size;
    }


    /**
     * Get the offset of the chunk on the x axis, y axis, and z axis (unit : blocks)
     * @return the offset of the chunk
     */
    public Position3D getOffsetInBlocks() {
        return offsetInBlocks;
    }

    /**
     * Set one element in the chunk
     * @param position The position of the element to set (absolute position
     *                 in the world, not in the chunk)
     * @param shouldDisplay A boolean telling whether or not a
     *                 block should be rendered
     * @param block The block to set
     */
    public void set(Position3D position, Block block) {
        Position2D verticalPosition = getCoupleVerticalPosition(position);
        data3DMatrix.get(position.getZ() - offsetInBlocks.getZ())
                .set(verticalPosition, block);
    }
    /**
     * Get one element in the chunk
     * @param position The position of the element to get. The position is
     *                 absolute in the world, not in the chunk
     */
    public Block get(Position3D position) {
        Position2D verticalPosition = getCoupleVerticalPosition(position);
        return data3DMatrix.get(position.getZ() - offsetInBlocks.getZ())
                .get(verticalPosition);
    }

    /**
     * Tells whether a point is in the chunk or not
     * @param position The position of the point
     * @return A boolean telling if yes or not the point is in the chunk
     */
    public boolean contains(Position3D position){
        return (position.getX() > offsetInBlocks.getX()
                && position.getX() < offsetInBlocks.getX() + this.size
                && position.getY() > offsetInBlocks.getY()
                && position.getY() < offsetInBlocks.getY() + this.size
                && position.getZ() > offsetInBlocks.getZ()
                && position.getZ() < offsetInBlocks.getZ() + this.size);
    }

    /**
     * Get the node manager of the chunk
     * @return The node manager
     */
    public Chunk3DNodeManager getNodeManager(){
        return chunk3DNodeManager;
    }


}
