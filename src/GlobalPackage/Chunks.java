package GlobalPackage;



import java.util.HashMap;
import com.jme3.scene.Node;

// Rendering optimization
import jme3tools.optimize.GeometryBatchFactory;




public class Chunks {

	// Hashmap containing chunks
	private static HashMap<Couple, Node> chunksHashMap = new HashMap<Couple, Node>();
	// Chunk size
	private static int chunkSize = 32;




	// Constructor -- needs a world (to get his dimensions)
	public Chunks(World world){
		int z = world.xSize();
		int x = world.zSize();
		Couple nbOfChunks = getChunkPosOf(x-1, z-1);

		for(int i=0; i <= nbOfChunks.i(); i++)
			for(int k=0; k <= nbOfChunks.k(); k++){
				Couple chunkPos = new Couple(i, k);
				Node node = new Node("node");
				chunksHashMap.put(chunkPos, node); 
			}
	}


	// Get a couple representating the position of the chunk in which the block is
	private static Couple getChunkPosOf(int i, int k){
		int iNbChunk = i/chunkSize;
		int kNbChunk = k/chunkSize;
		Couple chunkPos = new Couple(iNbChunk, kNbChunk);
		return chunkPos;

	}

	// Get a chunk and create one if does not exist
	public Node getChunk(int i, int k){
		Couple chunkPos = getChunkPosOf(i, k);
		if(chunksHashMap.containsKey(chunkPos)){
			Node node = chunksHashMap.get(chunkPos); 
			return node;
		}
		else 
			throw new RuntimeException("Chunk " + chunkPos.toString() + "does not exist");

	}

	// Attach a chunk (of type Node) to an anchor
	private static void attachChunk(Node chunk, Node anchor){
		anchor.attachChild(chunk);
	}

	// Detach a chunk (of type Node) of an anchor
	//	static private void detachChunk(Node chunk, Node anchor){
	//		anchor.detachChild(chunk);
	//	}



	// attach every chunk to the rootNode
	public static void attachEveryChunkToRootNode(World world, Node anchor){
		int z = world.xSize();
		int x = world.zSize();
		Couple nbOfChunks = getChunkPosOf(x, z);
		for(int i=0; i < nbOfChunks.i(); i++)
			for(int k=0; k < nbOfChunks.k(); k++){
				Couple chunkPos = new Couple(i, k);
				Node chunk = chunksHashMap.get(chunkPos);
				attachChunk(chunk, anchor);
				// Rendering optimizations
				GeometryBatchFactory.optimize(chunk);
			}
	}
}
