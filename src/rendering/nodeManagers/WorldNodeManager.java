package rendering.nodeManagers;

import com.jme3.scene.Node;
import generation.chunks.Chunk3DCollection;
import generation.chunks.ChunkDoesNotExistException;
import utils.Position3D;

public class WorldNodeManager {
    /**
     * The chunks of the world
     */
    Chunk3DCollection chunks;
    /**
     * The size of the chunks
     */
    int chunkSize;
    /**
     * The node of the entity
     */
    private Node node;
    /**
     * The parent node
     */
    public static Node parentNode;


    /**
     * Create an instance of chunk3DManager
     * @param nodeName The name of the node that will allow the chunk to be
     *                   linked to any other node
     */
    public WorldNodeManager(String nodeName, int chunkSize,
                            Chunk3DCollection chunks) {
        checkIfParentNodeSetted();
        node = new Node(nodeName);
        parentNode.attachChild(this.node);
        this.chunkSize = chunkSize;
        this.chunks = chunks;
    }

    /**
     * Statically set the parent node the entity should be children of
     * @param parentNodeToSet The node to set as parent
     */
    public static void setParentNode(Node parentNodeToSet) {
        parentNode = parentNodeToSet;
    }

    /**
     * Get the node of the node manager
     * @return The node
     */
    public Node getNode() {
        return node;
    }


    /**
     * Check if the parentNode has been initialized with the setParentNode()
     * method
     */
    public void checkIfParentNodeSetted() {
        if(parentNode == null)
            throw new RuntimeException("The parentNode has to be statically " +
                    "initialized with setParentNode() before instantiation of" +
                    " the class");
    }
    /**
     * Display all the adjacent chunks and hide the more distant chunks.
     * Actually load all the chunks in the sphere and unload all the chunks
     * outside of the sphere but close to it (in the cube in which the sphere
     * is incircled, plus 1 chunk in every direction). So if the player is
     * teleported, unloading all the chunks of the sphere will be needed before
     * the teleportation (elsewise they won't be unloaded)
     * @param position The position around which the chunks have to be loaded
     *                 (center of the sphere)
     * @param radius The Radius of the sphere in which the chunks will be
     *               displayed
     */
    public void attachChunksAround(Position3D position, int
            radius){
        int xCenter = position.getX() / chunkSize;
        int yCenter = position.getY() / chunkSize;
        int zCenter = position.getZ() / chunkSize;
        for (int i = xCenter - radius; i < xCenter + 1 + radius; i++)
            for (int j = yCenter - radius; j < yCenter + 1 + radius; j++)
                for (int k = zCenter - radius; k < zCenter + 1 + radius; k++) {
                    Position3D currChunkPos = new Position3D(i, j, k);
                    try {
                        if((Math.pow(i - xCenter, 2)
                                + Math.pow(j - yCenter, 2)
                                + Math.pow(k - zCenter, 2))
                                <= Math.pow(radius, 2))
                            chunks.getChunk(currChunkPos).getNodeManager().attachChunk();
                        else
                            chunks.getChunk(currChunkPos).getNodeManager()
                                    .detachChunk();
                    } catch (ChunkDoesNotExistException e) {
                        e.printStackTrace();
                    }
                }
    }
}
