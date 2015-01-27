package GlobalPackage;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
 
/** Sample 2 - How to use nodes as handles to manipulate objects in the scene.
 * You can rotate, translate, and scale objects by manipulating their parent nodes.
 * The Root Node is special: Only what is attached to the Root Node appears in the scene. */
public class Main extends SimpleApplication {
 
    
    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }
 
    
    private Geometry createCube(ColorRGBA color, int size, Vector3f position){
        Box box = new Box(size, size, size);             // Create a shape
        Material mat = new Material(assetManager,        // Create a material (texture)
                "Common/MatDefs/Misc/Unshaded.j3md");
        Geometry geom = new Geometry("Box", box);        // Create a gemetry
        
        mat.setColor("Color", color);                    // Set the material color
        geom.setMaterial(mat);                           // Attach the material to the geometry
        geom.setLocalTranslation(position);              // Set the geometry position

        return geom;
    }   

    private Geometry makeWater(float x, float y, float z){
        float r = (float)0.1;
        float g = (float)0.1;
        float b = (float)1;
        float a = (float)0.1;
        ColorRGBA color = new ColorRGBA(r, g, b, a);
        Vector3f position = new Vector3f(x, y, z);
        return createCube(color, 1, position);
    }
    
    private Geometry makeGrass(float x, float y, float z){
        float r = (float)0.07;
        float g = (float)0.63;
        float b = (float)0.11;
        float a = (float)1;
        return createCube(new ColorRGBA(r, g, b, a), 1, new Vector3f(x, y, z));
    }
    
    
    
    @Override
    public void simpleInitApp() {
 
        /** Create a pivot node at (0,0,0) and attach it to the root node */
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene
 
        for(int i = -100; i < 100; i+=4){
            for(int j = -100; j < 100; j+=2){
                /** Attach the two boxes to the *pivot* node. (And transitively to the root node.) */
                // pivot.attachChild(createCube(ColorRGBA.randomColor(), 1, new Vector3f(i, -20, j)));
            	if(i%2 == 0)
            		pivot.attachChild(makeWater(i, -20, j));
            	else pivot.attachChild(makeGrass(i, -20, j));
            	if(j%2 == 0)
            		pivot.attachChild(makeGrass(i, -20, j));
            	pivot.attachChild(makeWater(i, -20, j));
            }
        }
    }
}