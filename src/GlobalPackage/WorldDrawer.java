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
import GlobalPackage.World.BlockType;

public class WorldDrawer {

	// Enum of every possible orientation 
	public static enum Orientation {NORTH, WEST, SOUTH, EAST, UP, DOWN};

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
	public static void drawQuad(int x, int y, int z, Orientation orientation, ColorRGBA color, AssetManager assetManager, Node anchor){

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
		mat.setColor("Color", color);
		geom.setMaterial(mat);

		// Attaching the geometry to the root node.
		anchor.attachChild(geom);
	}
}