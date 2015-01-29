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
 
public class Main extends SimpleApplication {

	
	
   World world = new World(10, 10, 10);
    
    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }
 

    @Override
    public void simpleInitApp() {
    	
    world.printInConsole();

    }
}