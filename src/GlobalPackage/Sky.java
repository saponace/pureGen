
package GlobalPackage;


import GlobalPackage.WorldDrawer.Orientation;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.material.Material;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;








public class Sky {

	AssetManager assetManager;
	Node anchor;
	int skyDistance;
	
	
	
	public Sky(Node anchor, AssetManager assetManager){
		this.assetManager = assetManager;
		this.anchor = anchor;
		this.skyDistance = 500;
	}
	
	// Set colors for the corners of the vertexes
	private void setCornerColor(float[] colors, ColorRGBA col, int startIndex){
		colors[startIndex++] = col.getRed();
		colors[startIndex++] = col.getGreen();
		colors[startIndex++] = col.getBlue();
		colors[startIndex++] = col.getAlpha();
	}
	private void setDownLeftColor(float[] colors, ColorRGBA col){
		setCornerColor(colors, col, 0);
	}
	private void setDownRightColor(float[] colors, ColorRGBA col){
		setCornerColor(colors, col, 4);
	}
	private void setUpLeftColor(float[] colors, ColorRGBA col){
		setCornerColor(colors, col, 8);
	}
	private void setUpRightColor(float[] colors, ColorRGBA col){
		setCornerColor(colors, col, 12);
	}
	
	
	private Vector3f[] newVertice(int x, int y, int z, Orientation position){
		Vector3f[] vertices = new Vector3f[4];
		int sd = skyDistance;

		switch(position){
		case SOUTH:
			vertices[0] = new Vector3f(sd, -sd, sd);
			vertices[1] = new Vector3f(-sd, -sd, sd);
			vertices[2] = new Vector3f(sd, sd, sd);
			vertices[3] = new Vector3f(-sd, sd, sd);
			break;
		case EAST:
			vertices[0] = new Vector3f(sd, -sd, -sd);
			vertices[1] = new Vector3f(sd, -sd, sd);
			vertices[2] = new Vector3f(sd, sd, -sd);
			vertices[3] = new Vector3f(sd, sd, sd);
			break;
		case NORTH:
			vertices[0] = new Vector3f(-sd, -sd, -sd);
			vertices[1] = new Vector3f(sd, -sd, -sd);
			vertices[2] = new Vector3f(-sd, sd, -sd);
			vertices[3] = new Vector3f(sd, sd, -sd);
			break;
		case WEST:
			vertices[0] = new Vector3f(-sd, -sd, sd);
			vertices[1] = new Vector3f(-sd, -sd, -sd);
			vertices[2] = new Vector3f(-sd, sd, sd);
			vertices[3] = new Vector3f(-sd, sd, -sd);
			break;
		case DOWN:
			vertices[0] = new Vector3f(-sd, -sd, sd);
			vertices[1] = new Vector3f(sd, -sd, sd);
			vertices[2] = new Vector3f(-sd, -sd, -sd);
			vertices[3] = new Vector3f(sd, -sd, -sd);
			break;
		case UP:
			vertices[0] = new Vector3f(-sd, sd, -sd);
			vertices[1] = new Vector3f(sd, sd, -sd);
			vertices[2] = new Vector3f(-sd, sd, sd);
			vertices[3] = new Vector3f(sd, sd, sd);
			break;
		default:
			throw new RuntimeException("Orientation " + position + "does not exist");
		}
		return vertices;	
	}
	
	
	private Geometry createQuad(Orientation position, 
			ColorRGBA ulCol, ColorRGBA urCol, 
			ColorRGBA drCol, ColorRGBA dlCol){

		Mesh mesh = new Mesh();
		Vector3f [] vertices = newVertice(2, 2, 2, position);

		Vector2f[] texCoord = new Vector2f[4];
		texCoord[0] = new Vector2f(0,0);
		texCoord[1] = new Vector2f(1,0);
		texCoord[2] = new Vector2f(0,1);
		texCoord[3] = new Vector2f(1,1);
		int [] indexes = { 2,0,1, 1,3,2 };
		mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		mesh.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
		mesh.setBuffer(Type.Index,    3, BufferUtils.createIntBuffer(indexes));
		mesh.updateBound();
		

		Geometry geo = new Geometry ("ColoredMesh", mesh); 
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setBoolean("VertexColor", true);
		float[] colors = new float[4*4];
		setUpLeftColor(colors, ulCol);
		setUpRightColor(colors, urCol);
		setDownRightColor(colors, drCol);
		setDownLeftColor(colors, dlCol);
		
		mesh.setBuffer(Type.Color, 4, colors);
		geo.setMaterial(mat);
		return geo;
		
	}
	
	
	// Draw the uppper quad
	private Geometry createUpQuad(ColorRGBA col){
		return createQuad(Orientation.UP, col, col, col, col);
	}
	// Draw the lower quad
	private Geometry createDownQuad(ColorRGBA col){
		return createQuad(Orientation.DOWN, col, col, col, col);
	}
	// Draw the south quad
	private Geometry createSouthQuad(ColorRGBA upCol, ColorRGBA downCol){
		return createQuad(Orientation.SOUTH, upCol, upCol, downCol, downCol);
	}
	// Draw the north quad
	private Geometry createNorthQuad(ColorRGBA upCol, ColorRGBA downCol){
		return createQuad(Orientation.NORTH, upCol, upCol, downCol, downCol);
	}
	// Draw the east quad
	private Geometry createEastQuad(ColorRGBA upCol, ColorRGBA downCol){
		return createQuad(Orientation.EAST, upCol, upCol, downCol, downCol);
	}
	// Draw the west quad
	private Geometry createWestQuad(ColorRGBA upCol, ColorRGBA downCol){
		return createQuad(Orientation.WEST, upCol, upCol, downCol, downCol);
	}
	
	
	// Draw 6 quads for the sky
	public void draw(){
		Node sky = new Node("sky");

		sky.attachChild(createUpQuad(ColorRGBA.Cyan));	
		sky.attachChild(createDownQuad(ColorRGBA.White));
		sky.attachChild(createSouthQuad(ColorRGBA.Cyan, ColorRGBA.White));
		sky.attachChild(createNorthQuad(ColorRGBA.Cyan, ColorRGBA.White));
		sky.attachChild(createWestQuad(ColorRGBA.Cyan, ColorRGBA.White));
		sky.attachChild(createEastQuad(ColorRGBA.Cyan, ColorRGBA.White));
		sky.setShadowMode(ShadowMode.Off);		
		anchor.attachChild(sky);
	}
	
}
