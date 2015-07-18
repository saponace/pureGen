package rendering;

import enumerations.Orientation;
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
    /**
     * The asset manager of the sky
     */
    AssetManager assetManager;
    /**
     * The Node containing the sky
     */
    Node anchor;
    /**
     * The distance of the sky from the origin (0, 0, 0)
     * The bottom pane of the sky is not affected by this option
     */
    int skyDistance;


    /**
     * Create an instance of sky and add it to the anchor Node
     * @param anchor The node to which the sky should be attached
     * @param assetManager The assetManager of the sky
     */
    public Sky(Node anchor, AssetManager assetManager){
        this.assetManager = assetManager;
        this.anchor = anchor;
        this.skyDistance = 5000;
        draw();
    }


    /**
     * Set the color of a corner of a vertex
     * @param colors The array containing all the component of the colors of
     *               the corners of a vertex
     * @param col The color to set
     * @param startIndex The starting offset of the color in the array.
     *                   Vertexes actually use one array for the four
     *                   components (red, green, blue and alpha) of the four
     *                   corners
     */
    private void setCornerColor(float[] colors, ColorRGBA col, int startIndex){
        colors[startIndex++] = col.getRed();
        colors[startIndex++] = col.getGreen();
        colors[startIndex++] = col.getBlue();
        colors[startIndex] = col.getAlpha();
    }
    /**
     * Set the color of the bottom-left corner of a vertex
     * @param colors The array containing the components of the colors of the
     *               vertex
     * @param col The color to set
     */
    private void setBottomLeftColor(float[] colors, ColorRGBA col){
        setCornerColor(colors, col, 0);
    }
    /**
     * Set the color of the bottom-right corner of a vertex
     * @param colors The array containing the components of the colors of the
     *               vertex
     * @param col The color to set
     */
    private void setBottomRightColor(float[] colors, ColorRGBA col){
        setCornerColor(colors, col, 4);
    }
    /**
     * Set the color of the top-left corner of a vertex
     * @param colors The array containing the components of the colors of the
     *               vertex
     * @param col The color to set
     */
    private void setTopLeftColor(float[] colors, ColorRGBA col){
        setCornerColor(colors, col, 8);
    }
    /**
     * Set the color of the top-right corner of a vertex
     * @param colors The array containing the components of the colors of the
     *               vertex
     * @param col The color to set
     */
    private void setTopRightColor(float[] colors, ColorRGBA col){
        setCornerColor(colors, col, 12);
    }


    /**
     * Create a new vertice
     * @param x The offset of the center of the vertice on the X axis
     * @param Y The offset of the center of the vertice on the Y axis
     * @param z The offset of the center of the vertice on the Z axis
     * @param orientation The orientation of the vertice
     * @return The vertice
     */
    private Vector3f[] newVertex(int x, int y, int z, Orientation orientation){
        Vector3f[] vertices = new Vector3f[4];
        int sd = skyDistance;
        int downSkyh = -10;

        switch(orientation){
            case SOUTH:
                vertices[0] = new Vector3f(sd, downSkyh, sd);
                vertices[1] = new Vector3f(-sd, downSkyh, sd);
                vertices[2] = new Vector3f(sd, sd, sd);
                vertices[3] = new Vector3f(-sd, sd, sd);
                break;
            case EAST:
                vertices[0] = new Vector3f(sd, downSkyh, -sd);
                vertices[1] = new Vector3f(sd, downSkyh, sd);
                vertices[2] = new Vector3f(sd, sd, -sd);
                vertices[3] = new Vector3f(sd, sd, sd);
                break;
            case NORTH:
                vertices[0] = new Vector3f(-sd, downSkyh, -sd);
                vertices[1] = new Vector3f(sd, downSkyh, -sd);
                vertices[2] = new Vector3f(-sd, sd, -sd);
                vertices[3] = new Vector3f(sd, sd, -sd);
                break;
            case WEST:
                vertices[0] = new Vector3f(-sd, downSkyh, sd);
                vertices[1] = new Vector3f(-sd, downSkyh, -sd);
                vertices[2] = new Vector3f(-sd, sd, sd);
                vertices[3] = new Vector3f(-sd, sd, -sd);
                break;
            case DOWN:
                vertices[0] = new Vector3f(-sd, downSkyh, sd);
                vertices[1] = new Vector3f(sd, downSkyh, sd);
                vertices[2] = new Vector3f(-sd, downSkyh, -sd);
                vertices[3] = new Vector3f(sd, downSkyh, -sd);
                break;
            case UP:
                vertices[0] = new Vector3f(-sd, sd, -sd);
                vertices[1] = new Vector3f(sd, sd, -sd);
                vertices[2] = new Vector3f(-sd, sd, sd);
                vertices[3] = new Vector3f(sd, sd, sd);
                break;
            default:
                throw new RuntimeException("Orientation " + orientation + "does not exist");
        }
        return vertices;
    }
    /**
     * Create a quad representing a pane of the sky
     * @param orientation The orientation of the pane
     * @param ulCol The Top-Left color
     * @param urCol The Top-Right color
     * @param drCol The Bottom-Right color
     * @param dlCol The Bottom-Left color
     * @return
     */
    private Geometry createQuad(Orientation orientation,
                                ColorRGBA ulCol, ColorRGBA urCol,
                                ColorRGBA drCol, ColorRGBA dlCol){

        Mesh mesh = new Mesh();
        Vector3f [] vertices = newVertex(2, 2, 2, orientation);

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
        setTopLeftColor(colors, ulCol);
        setTopRightColor(colors, urCol);
        setBottomRightColor(colors, drCol);
        setBottomLeftColor(colors, dlCol);

        mesh.setBuffer(Type.Color, 4, colors);
        geo.setMaterial(mat);
        return geo;

    }
    /**
     * Create the quad on the top of the world
     * @param col The color of the quad
     * @return The geometry of the quad
     */
    private Geometry createTopQuad(ColorRGBA col){
        return createQuad(Orientation.UP, col, col, col, col);
    }
    /**
     * Create the quad on the bottom of the world
     * @param col The color of the quad
     * @return The geometry of the quad
     */
    private Geometry createBottomQuad(ColorRGBA col){
        return createQuad(Orientation.DOWN, col, col, col, col);
    }
    /**
     * Create the quad at the far south of the world
     * @param upCol The color of the top quad (up in the sky)
     * @param downCol The color of the bottom quad (under the horizon)
     * @return The geometry of the quad
     */
    private Geometry createSouthQuad(ColorRGBA upCol, ColorRGBA downCol){
        return createQuad(Orientation.SOUTH, upCol, upCol, downCol, downCol);
    }
    /**
     * Create the quad at the far north of the world
     * @param upCol The color of the top quad (up in the sky)
     * @param downCol The color of the bottom quad (under the horizon)
     * @return The geometry of the quad
     */
    private Geometry createNorthQuad(ColorRGBA upCol, ColorRGBA downCol){
        return createQuad(Orientation.NORTH, upCol, upCol, downCol, downCol);
    }
    /**
     * Create the quad at the far east of the world
     * @param upCol The color of the top quad (up in the sky)
     * @param downCol The color of the bottom quad (under the horizon)
     * @return The geometry of the quad
     */
    private Geometry createEastQuad(ColorRGBA upCol, ColorRGBA downCol){
        return createQuad(Orientation.EAST, upCol, upCol, downCol, downCol);
    }
    /**
     * Create the quad at the far west of the world
     * @param upCol The color of the top quad (up in the sky)
     * @param downCol The color of the bottom quad (under the horizon)
     * @return The geometry of the quad
     */
    private Geometry createWestQuad(ColorRGBA upCol, ColorRGBA downCol){
        return createQuad(Orientation.WEST, upCol, upCol, downCol, downCol);
    }


    /**
     * Draw the six quads representing the sky surrounding the player
     */
    private void draw(){
        Node sky = new Node("sky");

        sky.attachChild(createTopQuad(ColorRGBA.Cyan));
        sky.attachChild(createBottomQuad(ColorRGBA.White));
        sky.attachChild(createSouthQuad(ColorRGBA.Cyan, ColorRGBA.White));
        sky.attachChild(createNorthQuad(ColorRGBA.Cyan, ColorRGBA.White));
        sky.attachChild(createWestQuad(ColorRGBA.Cyan, ColorRGBA.White));
        sky.attachChild(createEastQuad(ColorRGBA.Cyan, ColorRGBA.White));

        sky.setShadowMode(ShadowMode.Off);
        anchor.attachChild(sky);
    }
}
