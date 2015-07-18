package generation;

// Hashmaps
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
// Chunks are nodes
import Main.DebugUtils;
import com.jme3.scene.Node;
// Rendering optimization
import jme3tools.optimize.GeometryBatchFactory;
// Cam location (choosing which chunks to display)
import com.jme3.math.Vector3f;
import utils.Couple;


public class Chunks {
    /**
     * Hashmap that contains chunks
     */
    private HashMap<Couple, Node> chunksHashMap = new HashMap<Couple, Node>();
    /**
     * The size of the chunks (length on a side of the chunks)
     */
    private int chunkSize = 16;
    /**
     * Render distance (in chunks unit)
     */
    private int renderDistance = 30;
    /**
     * Does the algorithm check every chunk at every simpleUpdate loop to detach
     * them ? Useful notably useful when teleporting
     */
    private boolean aggressiveChunkUnloading = false;
    /**
     * Do we attach every chunk to the rotNode at startup ?
     */
    private boolean loadEveryChunkAtStartup = false;


    /**
     * Create an ensemble of chunks to divide the world
     * @param xmin The minimum offset of the world on th x-axis
     * @param zmin The minimum offset of the world on th z-axis
     * @param xmax The maximum offset of the world on th x-axis
     * @param zmax The maximum offset of the world on th z-axis
     */
    public Chunks(int xmin, int zmin, int xmax, int zmax) {
        Couple maxChunks = getChunkPosOf(xmax - 1, zmax - 1);
        Couple minChunks = getChunkPosOf(xmin, zmin);

        for (int i = minChunks.i(); i <= maxChunks.i(); i++)
            for (int k = minChunks.k(); k <= maxChunks.k(); k++) {
                Couple chunkPos = new Couple(i, k);
                Node node = new Node("node");
                chunksHashMap.put(chunkPos, node);
            }
    }


    /**
     * Get the position of the chunk the block at the given position is.
     * @param i The x axis of the block
     * @param k The z axis of the block
     * @return A couple representing the position of the chunk in the world (in chunks units, not in block units)
     */
    private Couple getChunkPosOf(int i, int k) {
        int iNbChunk = i / chunkSize;
        int kNbChunk = k / chunkSize;
        Couple chunkPos = new Couple(iNbChunk, kNbChunk);
        return chunkPos;

    }
    /**
     * Get the chunk in which the block at given position is
     * @param i The x axis of the block
     * @param k The z axis of the block
     * @return The chunk 'actually a Node)
     */
    public Node getChunk(int i, int k) {
        Couple chunkPos = getChunkPosOf(i, k);
        if (chunksHashMap.containsKey(chunkPos)) {
            Node node = chunksHashMap.get(chunkPos);
            return node;
        } else
            throw new RuntimeException("Chunk " + chunkPos.toString()
                    + "does not exist");

    }
    /**
     * Attach a chunk (Node) to an anchor
     * @param chunk The Node representing the chunk
     * @param anchor The anchor to which we should attach the Node
     */
    private void attachChunk(Node chunk, Node anchor) {
        anchor.attachChild(chunk);
    }
    /**
     * Detach a chunk (Node) to an anchor
     * @param chunk The Node representing the chunk
     * @param anchor The anchor from which we should detatch the Node
     */
    private void detachChunk(Node chunk, Node anchor) {
        anchor.detachChild(chunk);
    }
    /**
     * Attach every chunks of a world to an anchor
     * @param world The world from which the chunks should be attached
     * @param anchor The anchor to which the chunks should be attached
     */
    public void loadEveryChunk(World world, Node anchor) {
        int xmin = world.xMin();
        int zmin = world.zMin();
        int xmax = world.xMax();
        int zmax = world.zMax();
        Couple minChunks = getChunkPosOf(xmin, zmin);
        Couple maxChunks = getChunkPosOf(xmax, zmax);
        for (int i = minChunks.i(); i < maxChunks.i(); i++)
            for (int k = minChunks.k(); k < maxChunks.k(); k++) {
                Couple chunkPos = new Couple(i, k);
                Node chunk = chunksHashMap.get(chunkPos);
                if (loadEveryChunkAtStartup)
                    attachChunk(chunk, anchor);

                // Rendering optimizations
                GeometryBatchFactory.optimize(chunk);

                DebugUtils.printDebug("chunk " + chunkPos.toString() + " " +
                        "filled");
            }
    }
    /**
     * Display only the chunks close to the cam
     * @param camLocation The location of the cam
     * @param anchor The Node containing the chunks to display
     */
    public void displayCloseChunks(Vector3f camLocation, Node anchor) {
        Couple camChunkPos = getChunkPosOf((int) camLocation.x,
                (int) camLocation.z);
        int diameterToCheckToDetach = 4;
        if (aggressiveChunkUnloading) {
            Iterator<Map.Entry<Couple, Node>> it = chunksHashMap.entrySet()
                    .iterator();
            while (it.hasNext()) {
                Map.Entry<Couple, Node> chunk = (Map.Entry<Couple, Node>) it
                        .next();
                detachChunk((Node) chunk.getValue(), anchor);
            }
        }

        for (int i = -renderDistance - diameterToCheckToDetach; i < renderDistance
                + diameterToCheckToDetach; i++)
            for (int k = -renderDistance - diameterToCheckToDetach; k < renderDistance
                    + diameterToCheckToDetach; k++) {
                Couple currentChunkPos = new Couple(i + camChunkPos.i(), k
                        + camChunkPos.k());
                Node currentChunk = chunksHashMap.get(currentChunkPos);
                if (currentChunk != null)
                    if (i < -renderDistance || i > renderDistance
                            || k < -renderDistance || k > renderDistance)
                        detachChunk(currentChunk, anchor);
                    else
                        attachChunk(currentChunk, anchor);
            }
    }
    /**
     * Display only the chunks in the cam frustum
     * @param camLocation The location of the cam
     * @param anchor The Node containing the chunks to display
     */
    public void displayChunksInFrustum(Vector3f camLocation, Node anchor) {

    }
}
