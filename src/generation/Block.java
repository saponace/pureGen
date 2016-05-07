package generation;

import enumerations.BlockType;
import utils.Position3D;

public class Block {
    /**
     * The position of the block
     */
    private Position3D position;
    /**
     * The type of the block
     */
    private BlockType blockType;

    static int i = 0;

    /**
     * Create an instance of BlockType
     * @param position The position of the block
     */
    public Block(Position3D position) {
        this.position = position;
    }
    /**
     * Create an instance of BlockType
     * @param position The position of the block
     * @param blockType The type of the block
     */
    public Block(Position3D position, BlockType blockType) {
        this.position = position;
        this.blockType = blockType;
//        if(blockType != BlockType.AIR)
//            System.out.println(i++);
    }

    /**
     * Get the position of the block
     * @return The position of the block
     */
    public Position3D getPosition() {
        return position;
    }

    /**
     * Get the blockType
     * @return The blockType of the block
     */
    public BlockType getBlockType() {
        return blockType;
    }
    /**
     * Set the blockType
     * @param blockType The blockType to set
     */
    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}
