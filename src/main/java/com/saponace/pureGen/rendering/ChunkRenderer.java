package com.saponace.pureGen.rendering;

import com.saponace.pureGen.generation.Block;
import com.saponace.pureGen.generation.chunks.Chunk3D;
import com.saponace.pureGen.utils.Position3D;

public class ChunkRenderer {
    /**
     * Display all the blocks in the chunk, but does not attach them to any node
     */
    public static void preDisplay(Chunk3D chunk){
        int size = chunk.getSize();
        Position3D offsetInBlocks = chunk.getOffsetInBlocks();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                for (int k = 0; k < size; k++){
                    Position3D positionInWorld = new Position3D(
                            i + offsetInBlocks.getX(),
                            j + offsetInBlocks.getY(),
                            k + offsetInBlocks.getZ());
                    Block block = chunk.get(positionInWorld);
                    BlockRenderer.drawBlockInChunk(block, chunk.getChunk3DNodeManager());
                }
    }
}
