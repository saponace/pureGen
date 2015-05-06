package GlobalPackage;

// Hashmaps
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

// Chunks are nodes
import com.jme3.scene.Node;

// Rendering optimization
import jme3tools.optimize.GeometryBatchFactory;

// Cam location (choosing which chunks to display)
import com.jme3.math.Vector3f;

public class Chunks {

	// Hashmap containing chunks
	private static HashMap<Couple, Node> chunksHashMap = new HashMap<Couple, Node>();
	// Chunk size
	private static int chunkSize = 16;
	// Render distance (in chunks)
	private static int renderDistance = 30;
	// Does the algorithm check every chunk at every simpleUpdate loop to detach
	// them ? Useful when teleporting (for instance
	private static boolean aggressiveChunkUnloading = false;
	// Do we attach every chunk to the rotNode at startup ?
	private static boolean loadEveryChunkAtStartup = false;

	
	
	// Constructor -- needs a world (to get his dimensions)
	public Chunks(World world) {
		int xmin = world.xMin();
		int zmin = world.zMin();
		int xmax = world.xMax();
		int zmax = world.zMax();
		Couple maxChunks = getChunkPosOf(xmax - 1, zmax - 1);
		Couple minChunks = getChunkPosOf(xmin, zmin);

		for (int i = minChunks.i(); i <= maxChunks.i(); i++)
			for (int k = minChunks.k(); k <= maxChunks.k(); k++) {
				Couple chunkPos = new Couple(i, k);
				Node node = new Node("node");
				chunksHashMap.put(chunkPos, node);
			}
	}

	// Get a couple representating the position of the chunk in which the block
	// is
	private static Couple getChunkPosOf(int i, int k) {
		int iNbChunk = i / chunkSize;
		int kNbChunk = k / chunkSize;
		Couple chunkPos = new Couple(iNbChunk, kNbChunk);
		return chunkPos;

	}

	// Get a chunk and create one if does not exist
	public Node getChunk(int i, int k) {
		Couple chunkPos = getChunkPosOf(i, k);
		if (chunksHashMap.containsKey(chunkPos)) {
			Node node = chunksHashMap.get(chunkPos);
			return node;
		} else
			throw new RuntimeException("Chunk " + chunkPos.toString()
					+ "does not exist");

	}

	// Attach a chunk (of type Node) to an anchor
	private static void attachChunk(Node chunk, Node anchor) {
		anchor.attachChild(chunk);
	}

	// Detach a chunk (of type Node) of an anchor
	private static void detachChunk(Node chunk, Node anchor) {
		anchor.detachChild(chunk);
	}

	// attach every chunk to the rootNode
	public static void loadEveryChunk(World world, Node anchor) {
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

				if (Main.debug)
					System.out.printf("chunk %s filled\n", chunkPos.toString());
			}
	}

	// Only display close chunks
	public static void displayCloseChunks(Vector3f camLocation, Node anchor) {
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
		// Only display chunks in the cam frustum (angle de vue)
		public static void displayChunksInFrustum(Vector3f camLocation){
			
		}

}
