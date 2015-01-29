package GlobalPackage;

//import java.lang.reflect.Array;
//
//import jme3tools.optimize.GeometryBatchFactory;

import com.jme3.app.SimpleApplication;
//import com.jme3.material.Material;
//import com.jme3.math.ColorRGBA;
//import com.jme3.math.Vector3f;
//import com.jme3.scene.Geometry;
//import com.jme3.scene.Node;
//import com.jme3.scene.shape.Box;
 
/** Sample 2 - How to use nodes as handles to manipulate objects in the scene.
 * You can rotate, translate, and scale objects by manipulating their parent nodes.
 * The Root Node is special: Only what is attached to the Root Node appears in the scene. */
public class Main extends SimpleApplication {

	
	
    
    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }
 
   World world = new World(1, 2, 3);

    @Override
    public void simpleInitApp() {
    	

        for(int i = 0; i < world.xSize(); i+=1){
            for(int j = 0; j < world.ySize(); j+=1){
            	for(int k = 0; k < world.zSize(); k+=1){
            		System.out.printf(" " + world.getBlock(i, j, k));
            	}
            }
        }
    }
}