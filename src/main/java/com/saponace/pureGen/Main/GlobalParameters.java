package com.saponace.pureGen.Main;

public abstract class GlobalParameters {
    /**
     * The size of the chunks of the world. The chunks are used for rendering
     * and the size of the chunks does not affect the generation
     * Used in: main.Main
     */
    public final static int chunkSize = 16;
    /**
     * If set to true, details about the generation and rendering process
     * will be printed in the console.
     * Used in: main.DebugUtils
     */
    public final static boolean debugMode = false;
    /**
     * The distance between two successive height gradients. The gradients
     * are put on a grid. This variable is the size of the squares of the grid.
     * Used in: generation.HeightMap
     */
    public final static int heightGradientsInterval = 20;
    /**
     * The maximum height the world can reach.
     * Used in: generation.HeightMap
     */
    public final static double maxHeight = 40.0;
    /**
     * The minimum height the world can reach.
     * Used in: generation.HeightMap
     */
    public final static double minHeight = 0.0;
    /**
     * The distance between the center of the world and the sky panes. Half
     * the size of the box made of the six sky panes
     * Used in: rendering.SkyRenderer
     */
    public final static int skyDistance = 500;
    /**
     * The radius of the circle around the cam where the chunks should be
     * displayed (and created if they have not been created yet)
     */
    public final static int nearChunkDisplayingRadius = 3;
}
