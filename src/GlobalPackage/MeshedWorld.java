package GlobalPackage;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;

public class MeshedWorld extends SimpleApplication {

	public static void main(String[] args) {
		MeshedWorld app = new MeshedWorld();
		app.start();
	}

	// Enum of every possible orientation 
	public static enum Orientation {NORTH, WEST, SOUTH, EAST, UP, DOWN};
	
	
	public void createMesh(int x, int y, int z, Orientation orientation) {

		Mesh m = new Mesh();

		// Vertex positions in space
		Vector3f[] vertices = new Vector3f[4];
		vertices[0] = new Vector3f(0, 0, 0);
		vertices[1] = new Vector3f(1, 0, 0);
		vertices[2] = new Vector3f(0, 1, 0);
		vertices[3] = new Vector3f(1, 1, 0);

		// Texture coordinates
		Vector2f[] texCoord = new Vector2f[4];
		texCoord[0] = new Vector2f(0, 0);
		texCoord[1] = new Vector2f(1, 0);
		texCoord[2] = new Vector2f(0, 1);
		texCoord[3] = new Vector2f(1, 1);

		// Indexes. We define the order in which mesh should be constructed
		int[] indexes = { 2, 0, 1, 1, 3, 2 };

		// Setting buffers
		m.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		m.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
		m.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indexes));
		m.updateBound();

		// Creating a geometry, and apply a single color material to it
		Geometry geom = new Geometry("OurMesh", m);
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Blue);
		geom.setMaterial(mat);

		// Attaching our geometry to the root node.
		rootNode.attachChild(geom);
	}

	@Override
	public void simpleInitApp() {
		createMesh();
	}
}