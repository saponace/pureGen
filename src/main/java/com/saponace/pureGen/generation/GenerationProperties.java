package com.saponace.pureGen.generation;

import java.util.Properties;

public class GenerationProperties {

    public GenerationProperties(Properties props) {
        minHeight = Integer.parseInt(
                props.getProperty("minHeight", "0"));
        maxHeight = Integer.parseInt(
                props.getProperty("maxHeight", "40"));
        chunkSize = Integer.parseInt(
                props.getProperty("chunkSize", "16"));
        nearChunksGenerationRadius = Integer.parseInt(
                props.getProperty("nearChunksGenerationRadius", "3"));
        heightGradientsInterval = Integer.parseInt(
                props.getProperty("heightGradientsInterval", "20"));
    }

    /**
     * The maximum height the world can reach.
     */
    public int maxHeight;

    /**
     * The minimum height the world can reach.
     */
    public int minHeight;
    /**
     *  The size of the chunks. The chunks are used for rendering
     *  and the size of the chunks does not affect the generation
     */
    public int chunkSize;
    /**
     * The radius of the circle around the cam where the chunks should be
     * generated
     */
    public int nearChunksGenerationRadius;
    /**
     * The distance between two successive height gradients. The gradients
     * are put on a grid. This variable is the size of the squares of the grid.
     * Used in: generation.HeightMap
     */
    public int heightGradientsInterval;

    @Override
    public String toString() {
        return "Maximum height: " + maxHeight + ",\n" +
                "Minimum height: " + minHeight + ",\n" +
                "Chunk size: " + chunkSize + ",\n" +
                "Near chunks generation radius: " + nearChunksGenerationRadius + ",\n" +
                "Height gradients interval: " + heightGradientsInterval;
    }
}
