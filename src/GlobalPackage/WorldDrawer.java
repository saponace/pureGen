package GlobalPackage;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;

import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue.Bucket;


import GlobalPackage.World.BlockType;

public class WorldDrawer {

	// Enum of every possible orientation 
	public static enum Orientation {NORTH, WEST, SOUTH, EAST, UP, DOWN};


	// Vertex positions in space
	private static Vector3f[] newVertice(int x, int y, int z, Orientation orientation){
		Vector3f[] vertices = new Vector3f[4];

		switch(orientation){
		case NORTH:
			vertices[0] = new Vector3f(x, y, z);
			vertices[1] = new Vector3f(x-1, y, z);
			vertices[2] = new Vector3f(x, y+1, z);
			vertices[3] = new Vector3f(x-1, y+1, z);
			break;
		case WEST:
			vertices[0] = new Vector3f(x-1, y, z);
			vertices[1] = new Vector3f(x-1, y, z+1);
			vertices[2] = new Vector3f(x-1, y+1, z);
			vertices[3] = new Vector3f(x-1, y+1, z+1);
			break;
		case SOUTH:
			vertices[0] = new Vector3f(x-1, y, z+1);
			vertices[1] = new Vector3f(x, y, z+1);
			vertices[2] = new Vector3f(x-1, y+1, z+1);
			vertices[3] = new Vector3f(x, y+1, z+1);
			break;
		case EAST:
			vertices[0] = new Vector3f(x, y, z+1);
			vertices[1] = new Vector3f(x, y, z);
			vertices[2] = new Vector3f(x, y+1, z+1);
			vertices[3] = new Vector3f(x, y+1, z);
			break;
		case UP:
			vertices[0] = new Vector3f(x-1, y+1, z+1);
			vertices[1] = new Vector3f(x, y+1, z+1);
			vertices[2] = new Vector3f(x-1, y+1, z);
			vertices[3] = new Vector3f(x, y+1, z);
			break;
		case DOWN:
			vertices[0] = new Vector3f(x, y, z+1);
			vertices[1] = new Vector3f(x-1, y, z+1);
			vertices[2] = new Vector3f(x, y, z);
			vertices[3] = new Vector3f(x-1, y, z);
			break;
		default:
			throw new RuntimeException("Orientation " + orientation + "does not exist");
		}
		return vertices;	
	}

	// Create a quad and attach it to the anchor node
	public static void drawQuad(int x, int y, int z, Orientation orientation, BlockType btype, AssetManager assetManager, Node anchor){

		Mesh m = new Mesh();

		// Texture coordinates
		Vector2f[] texCoord = new Vector2f[4];
		texCoord[0] = new Vector2f(0, 0);
		texCoord[1] = new Vector2f(1, 0);
		texCoord[2] = new Vector2f(0, 1);
		texCoord[3] = new Vector2f(1, 1);

		// Indexes. Order in which mesh should be constructed
		int[] indexes = { 2, 0, 1, 1, 3, 2 };

		// Setting buffers
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(newVertice(x, y, z, orientation)));
		m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
		m.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indexes));
		m.updateBound();

		// Creating a geometry, and apply the color to it 
		Geometry geom = new Geometry("OurMesh", m);
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", colorOf(btype));
		geom.setMaterial(mat);
		
		// Use transparency if water
		if(btype == BlockType.WATER){
			mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			geom.setQueueBucket(Bucket.Transparent); 
		}

		// Attaching the geometry to the root node.
		anchor.attachChild(geom);
	}

	// Determine if we need to draw a quad on a given position on a given orientation
	private static boolean isQuadNeeded(World world,int x, int y, int z, Orientation orientation){

		if(world.getBlock(x, y, z) == BlockType.AIR)
				return false;
		else{
			World.BlockType blockNextTo;
			switch(orientation){
			case UP:
				blockNextTo = world.getBlock(x, y+1, z);
			break;
			case DOWN:
				blockNextTo = world.getBlock(x, y-1, z);
			break;
			case WEST:
				blockNextTo = world.getBlock(x-1, y, z);
			break;
			case EAST:
				blockNextTo = world.getBlock(x+1, y, z);
			break;
			case NORTH:
				blockNextTo = world.getBlock(x, y, z-1);
			break;
			case SOUTH:
				blockNextTo = world.getBlock(x, y, z+1);
			break;
			default:
				throw new RuntimeException("Orientation " + orientation + "does not exist");
			}
			if(world.getBlock(x, y, z) == BlockType.WATER && blockNextTo == BlockType.WATER)
				return false;
			else
				return (blockNextTo == BlockType.OUT_OF_BOUNDS ||
                        blockNextTo == BlockType.AIR ||
                        blockNextTo == BlockType.WATER); 
		}
	}
	
	// Determine the color of a block given its BlockType
	private static ColorRGBA colorOf(BlockType btype){
		ColorRGBA color = new ColorRGBA();
		double r=1, g=1, b=1, a=1;
		
		switch(btype){
			case AIR:
				// Never used
			break;
			case GRASS:
				r = 0.175; g = 0.640; b = 0.101; a = 1;
			break;
			case DIRT:
				r = 0.578; g = 0.226; b = 0.096; a = 1;
			break;
			case STONE:
				r = 0.400; g = 0.400; b = 0.400; a = 1;
			break;
			case WATER:
				r = 0; g = 0.246; b = 0.910; a = 0.5;
			break;
			case OUT_OF_BOUNDS:
				// Never used
			break;
			default:
				throw new RuntimeException("BlockType " + btype + "does not exist");
		}
		color.set((float)r, (float)g, (float)b, (float)a);
		return color;
	}
	
	// Draw the world
	public static void drawWorld(World world, AssetManager assetManager, Node anchor){
		int maxx = world.xSize();
		int maxy = world.ySize();
		int maxz = world.zSize();

		for(int x = 0; x < maxx; x++)
			for(int y = maxy-1; y >= 0; y--)
				for(int z = 0; z < maxz; z++){
					for(Orientation orientation : Orientation.values())
						if(isQuadNeeded(world, x, y, z, orientation))
							drawQuad(x, y-10, z-5, orientation, world.getBlock(x, y, z), assetManager, anchor);
				}
	}
}
