package rendering;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import enumerations.BlockType;
import enumerations.Orientation;
import generation.Block;
import generation.World;
import rendering.nodeManagers.Chunk3DNodeManager;
import utils.Position3D;

public abstract class BlockRenderer {

    private static AssetManager assetManager;
    private static World world;


    /**
     * Set the assetManager of the class
     * @param assetManager The assetManager to set
     */
    public static void setAssetManager(AssetManager assetManager) {
        BlockRenderer.assetManager = assetManager;
    }

    /**
     * Set the world of the class
     * @param world The world to set
     */
    public static void setWorld(World world) {
        BlockRenderer.world = world;
    }

    /**
     * Compute the array used to create a vertex in the space
     *
     * @param position    The position of the vertex to create
     * @param orientation The orientation of the vertex
     * @return An array containing the positions of the corners
     */
    private static Vector3f[] newVertex(Position3D position,
                                        Orientation orientation) {
        Vector3f[] vertices = new Vector3f[4];
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();

        switch (orientation) {
            case NORTH:
                vertices[0] = new Vector3f(x, y, z);
                vertices[1] = new Vector3f(x - 1, y, z);
                vertices[2] = new Vector3f(x, y + 1, z);
                vertices[3] = new Vector3f(x - 1, y + 1, z);
                break;
            case WEST:
                vertices[0] = new Vector3f(x - 1, y, z);
                vertices[1] = new Vector3f(x - 1, y, z + 1);
                vertices[2] = new Vector3f(x - 1, y + 1, z);
                vertices[3] = new Vector3f(x - 1, y + 1, z + 1);
                break;
            case SOUTH:
                vertices[0] = new Vector3f(x - 1, y, z + 1);
                vertices[1] = new Vector3f(x, y, z + 1);
                vertices[2] = new Vector3f(x - 1, y + 1, z + 1);
                vertices[3] = new Vector3f(x, y + 1, z + 1);
                break;
            case EAST:
                vertices[0] = new Vector3f(x, y, z + 1);
                vertices[1] = new Vector3f(x, y, z);
                vertices[2] = new Vector3f(x, y + 1, z + 1);
                vertices[3] = new Vector3f(x, y + 1, z);
                break;
            case UP:
                vertices[0] = new Vector3f(x - 1, y + 1, z + 1);
                vertices[1] = new Vector3f(x, y + 1, z + 1);
                vertices[2] = new Vector3f(x - 1, y + 1, z);
                vertices[3] = new Vector3f(x, y + 1, z);
                break;
            case DOWN:
                vertices[0] = new Vector3f(x, y, z + 1);
                vertices[1] = new Vector3f(x - 1, y, z + 1);
                vertices[2] = new Vector3f(x, y, z);
                vertices[3] = new Vector3f(x - 1, y, z);
                break;
            default:
                throw new RuntimeException("Orientation " + orientation
                        + "does not exist");
        }
        return vertices;
    }

    /**
     * Give a normal of a quad given its orientation
     *
     * @param orientation The orientation of the quad
     * @return The normal of the quad
     */
    private static float[] getNormal(Orientation orientation) {
        float[] normal = new float[3];
        switch (orientation) {
            case NORTH:
                normal = new float[]{0, 0, -1};
                break;
            case WEST:
                normal = new float[]{-1, 0, 0};
                break;
            case SOUTH:
                normal = new float[]{0, 0, 1};
                break;
            case EAST:
                normal = new float[]{1, 0, 0};
                break;
            case UP:
                normal = new float[]{0, 1, 0};
                break;
            case DOWN:
                normal = new float[]{0, -1, 0};
                break;
            default:
                throw new RuntimeException("Orientation " + orientation
                        + "does not exist");
        }
        float[] normalX3 = new float[12];
        System.arraycopy(normal, 0, normalX3, 0, 3);
        System.arraycopy(normal, 0, normalX3, 3, 3);
        System.arraycopy(normal, 0, normalX3, 6, 3);
        System.arraycopy(normal, 0, normalX3, 9, 3);
        return normalX3;
    }

    /**
     * Draw a quad and attach it to its parent node
     *
     * @param position     The position of the quad to draw
     * @param orientation  The orientation of the block
     * @param btype        The block type
     */
    private static Geometry getQuadGeometry(Position3D position, Orientation
            orientation, BlockType btype) {

        Mesh mesh = new Mesh();

        // Texture coordinates
        Vector2f[] texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0, 0);
        texCoord[1] = new Vector2f(1, 0);
        texCoord[2] = new Vector2f(0, 1);
        texCoord[3] = new Vector2f(1, 1);

        // Indexes. Order in which mesh should be constructed
        int[] indexes = {2, 0, 1, 1, 3, 2};

        // Setting buffers
        mesh.setBuffer(Type.Position, 3,
                BufferUtils.createFloatBuffer(newVertex(position, orientation)));
        mesh.setBuffer(Type.Index, 1, BufferUtils.createIntBuffer(indexes));
        mesh.setBuffer(Type.Normal, 3,
                BufferUtils.createFloatBuffer(getNormal(orientation)));
        mesh.updateBound();

        // Creating a geometry, and apply the color to it
        Geometry geom = new Geometry("", mesh);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");

        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", colorOf(btype));
        mat.setColor("Diffuse", colorOf(btype)); // minimum material color
        mat.setColor("Specular", ColorRGBA.White); // for shininess
        mat.setFloat("Shininess", 1f); // [1,128] for shininess

        geom.setMaterial(mat);

        // Use transparency if water
        if (btype == BlockType.WATER) {
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            geom.setQueueBucket(RenderQueue.Bucket.Transparent);
            mat.setFloat("Shininess", 30f); // [1,128] for shininess
        }
        return geom;
    }


    private static Position3D getPosOfAdjacentBlock(Position3D position,
                                                    Orientation orientation){
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        Position3D positionOfAdjacent;
        switch (orientation) {
            case UP:
                positionOfAdjacent = new Position3D(x, y + 1, z);
                break;
            case DOWN:
                positionOfAdjacent = new Position3D(x, y - 1, z);
                break;
            case WEST:
                positionOfAdjacent = new Position3D(x - 1, y, z);
                break;
            case EAST:
                positionOfAdjacent = new Position3D(x + 1, y, z);
                break;
            case NORTH:
                positionOfAdjacent = new Position3D(x, y, z - 1);
                break;
            case SOUTH:
                positionOfAdjacent = new Position3D(x, y, z + 1);
                break;
            default:
                throw new RuntimeException("Orientation " + orientation
                        + "does not exist");
        }
        return positionOfAdjacent;

    }



    /**
     * Compute the need to draw a quad of a block
     *
     * @param position    The position of the block
     * @param orientation The orientation of the quad
     * @return A boolean saying if the quad is usefull or not
     */
    private static boolean isQuadNeeded(Position3D position,
                                        Orientation orientation) {
        //TODO: probleme ici: cette méthode est appelée pendant la génération du terrain, voir TODO du chunk3D
//        System.out.println("min : " + world.xMin() + " " + world.yMin() + " " + world.zMin() + " " );
//        System.out.println("max : " + world.xMax() + " " + world.yMax() + " " + world.zMax() + " " );
        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();
        if(y <= 25)
            return false; //TODO : c'est juste pour des tests ça, parce que tous les blocks non air ni out_of_bounds sont affichés. A enlever
        if (WorldRenderer.minimalRendering
                && (y == world.yMin() || x == world.xMin()
                || x == world.xMax() - 1 || z == world.zMin() || z == world
                .zMax() - 1))
            return false;
        else {
            if (world.getBlock(position).getBlockType() == BlockType.AIR)
                return false;
            else {
                Position3D positionOfAdjacent = getPosOfAdjacentBlock(position, orientation);
                BlockType adjacentBType = world.getBlock(positionOfAdjacent)
                        .getBlockType();
//                if(adjacentBType == BlockType.OUT_OF_BOUNDS)
//                    return false;
                if (world.getBlock(position).getBlockType() == BlockType.WATER
                        && adjacentBType == BlockType.WATER)
                    return false;
                else
                    return (adjacentBType == BlockType.OUT_OF_BOUNDS
                            || adjacentBType == BlockType.AIR
                            || adjacentBType == BlockType.WATER);
            }
        }
    }

    /**
     * Give the color of a block given its type
     *
     * @param btype The type of the block
     * @return The color of the block
     */
    private static ColorRGBA colorOf(BlockType btype) {
        ColorRGBA color = new ColorRGBA();
        double r = 1, g = 1, b = 1, a = 1;

        switch (btype) {
            case AIR:
                // Never used
                System.out.println("air");
                //TODO: lever exception ici
                break;
            case GRASS:
                r = 0.175;
                g = 0.640;
                b = 0.101;
                a = 1;
                break;
            case DIRT:
                r = 0.398;
                g = 0.195;
                b = 0.000;
                a = 1;
                break;
            case STONE:
                r = 0.400;
                g = 0.400;
                b = 0.400;
                a = 1;
                break;
            case WATER:
                r = 0;
                g = 0.246;
                b = 0.910;
                a = 0.5;
                break;
            case OUT_OF_BOUNDS:
                // Never used
                System.out.println("out_of_bounds");
                //TODO: lever exception ici
                break;
            default:
                throw new RuntimeException("BlockType " + btype + "does not exist");
        }
        color.set((float) r, (float) g, (float) b, (float) a);
        return color;
    }

    /**
     * Draw a block and attach it to a chunk
     * @param block The block to draw
     * @param chunk3DNodeManager The nodeManager of the chunk to which the
     *                           block should be attached
     */
    public static void drawBlockInChunk(Block block,
                                        Chunk3DNodeManager chunk3DNodeManager){
        Position3D position = block.getPosition();
        for (Orientation orientation : Orientation.values()) {
            boolean isQuadneeded = isQuadNeeded(position, orientation);
//            isQuadneeded = position.getY() == 30 && orientation == Orientation.UP;
            if (isQuadneeded){
                chunk3DNodeManager.addBlockGeometry(getQuadGeometry
                        (position, orientation,
                                block.getBlockType()));
                System.out.println(block.getPosition().toString());
            }
        }
    }
}
