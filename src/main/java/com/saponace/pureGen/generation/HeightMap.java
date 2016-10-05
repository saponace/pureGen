package com.saponace.pureGen.generation;

import com.saponace.pureGen.utils.Couple;
import com.saponace.pureGen.utils.Position2D;

import java.util.HashMap;

public class HeightMap {
    /**
     * The frequency at which a new gradient should be chosen to generate the
     * heightmap
     */
    private int gradientsInterval;
    /**
     * The 2D array containing the heights
     */
    private HashMap<Position2D, Integer> heightMap = new HashMap<>();
    /**
     * The 2D array containing the gradients
     */
    private HashMap<Position2D, Double> gradientsMap = new HashMap<>();
    /**
     * A couple containing the minimum and maximum heights allowed
     */
    private Couple<Integer, Integer> minMaxHeights;


    /**
     * Create an instance of heightMap
     * @param gradientsInterval The distance between two successive
     *                          gradients (on the x and z axis)
     * @param minMaxHeights A couple of integers containing the minimum and
     *                      maximum allowed heights
     */
    public HeightMap(int gradientsInterval, Couple<Integer, Integer> minMaxHeights){
        this.gradientsInterval = gradientsInterval;
        this.minMaxHeights = minMaxHeights;
    }


    /**
     * Get the height of the surface on the given coordinates. If the wanted
     * height has not been computed yet, it is first computed, then returned.
     * @param position The position of the height to get
     * @return The height
     */
    public double getHeight(Position2D position) {
        if(!heightMap.containsKey(position))
            setHeight(position);
        return heightMap.get(position);
    }
    /**
     * Set the height of the surface on the given coordinates. It directly
     * computes the height from the gradients
     * @param position The position of the height to set
     */
    private void setHeight(Position2D position) {
        heightMap.put(position, computeHeight(position));
    }


    /**
     * Compute the distance between two dots in a plane
     * @param p1 The position of the first point
     * @param p2 The position of the second point
     * @return The distance
     */
    private double planDistance(Position2D p1, Position2D p2) {
        int deltaX = Math.abs(p1.getX() - p2.getX());
        int deltaZ = Math.abs(p1.getY() - p2.getY());
        return Math.sqrt(Math.pow(deltaX, 2) + (Math.pow(deltaZ, 2)));
    }
    /**
     * Compute the height of a given spot on the map following an algorithm.
     * If it needs a gradient that has not been computed yet, it first
     * computes this gradient.
     * @param position The position of the height to compute
     * @return The wanted height
     */
    private int computeHeight(Position2D position) {
        int i = position.getX();
        int k = position.getY();
        int baseXGradPos = i / gradientsInterval; // X offset of one of the four surrounding gradients
        int baseZGradPos = k / gradientsInterval; // z offset of one of the four surrounding gradients
        double height = 0;
        for (int countX = 0; countX < 2; countX++)
            for (int countZ = 0; countZ < 2; countZ++) {
                Position2D currGradPos = new Position2D(
                        baseXGradPos + countX, baseZGradPos + countZ);
                if(!gradientsMap.containsKey(currGradPos))
                    generateGradient(currGradPos);

                double dist = planDistance(new Position2D(i, k), currGradPos);
                height += (gradientsInterval - dist) *
                        gradientsMap.get(currGradPos);
            }
        height = 30 + Math.random()*2;
        //TODO: Faire une vraie surface qui marche
        return (int)height;
    }
    /**
     * Generate a random gradient and insert it in the table
      * @param gradientPosition The position of the gradient to generate
     */
    private void generateGradient(Position2D gradientPosition){
        double minHeight = minMaxHeights.getFirst();
        double maxHeight = minMaxHeights.getSecond();
        double height = minHeight + Math.random() * (maxHeight - minHeight);
        gradientsMap.put(gradientPosition, height);
    }
}
