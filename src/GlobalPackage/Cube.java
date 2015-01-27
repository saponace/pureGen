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
public class Cube extends SimpleApplication {
    
	// Juste pour ne pas avoir de problème avec la déclaration de new Mterial dans createCube
    public void simpleInitApp() {}

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

    public Geometry water(float x, float y, float z){
        float r = (float)0.1;
        float g = (float)0.1;
        float b = (float)1;
        float a = (float)0.1;
        ColorRGBA color = new ColorRGBA(r, g, b, a);
        Vector3f position = new Vector3f(x, y, z);
        return createCube(color, 1, position);
    }
    
    public Geometry grass(float x, float y, float z){
        float r = (float)0.07;
        float g = (float)0.63;
        float b = (float)0.11;
        float a = (float)1;
        return createCube(new ColorRGBA(r, g, b, a), 1, new Vector3f(x, y, z));
    }
    
    
}