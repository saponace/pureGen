package com.saponace.pureGen.rendering;

import java.util.Properties;

public class RenderingProperties {

    public RenderingProperties(Properties props) {
        skyDistance = Integer.parseInt(
                props.getProperty("skyDistance", "500"));
        nearChunksDisplayRadius = Integer.parseInt(
                props.getProperty("nearChunksDisplayRadius", "3"));
    }

    /**
     * The distance between the center of the world and the sky panes. Half
     * the size of the box made of the six sky panes
     */
    public int skyDistance;
    /**
     * The radius of the circle around the cam where the chunks should be
     * displayed
     */
    public int nearChunksDisplayRadius;

    @Override
    public String toString() {
         return "Sky distance: " + skyDistance + ",\n" +
         "Near chunk display radius: " + nearChunksDisplayRadius;
    }
}
