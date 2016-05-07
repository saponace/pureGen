package rendering.nodeManagers;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

public class Chunk3DNodeManager {
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
    public Chunk3DNodeManager(String nodeName) {
        checkIfParentNodeSetted();
        node = new Node(nodeName);
    }


    /**
     * Statically set the parent node the entity should be children of
     * @param parentNodeToSet The node to set as parent
     */
    public static void setParentNode(Node parentNodeToSet) {
        parentNode = parentNodeToSet;
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
     * Attach the geometry of a block to this nodeManager
     * @param blockGeometry The geometry of the block
     */
    public void addBlockGeometry(Geometry blockGeometry){
        this.node.attachChild(blockGeometry);
    }
    /**
     * Attach the anchor node of the chunk to the parent node
     */
    public void attachChunk(){
        parentNode.attachChild(this.node);
    }
    /**
     * Detach the anchor node of the chunk from the parent node
     */
    public void detachChunk(){
        parentNode.detachChild(this.node);
    }
}
