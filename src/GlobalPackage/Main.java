package GlobalPackage;


import com.jme3.app.SimpleApplication;
//import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
//import com.jme3.math.Vector3f;
//import com.jme3.scene.Geometry;
//import com.jme3.scene.Node;
//import com.jme3.scene.shape.Box;


public class Main extends SimpleApplication {

    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }
 

    @Override
    public void simpleInitApp() {
		MeshedWorld.createMesh(0, 0, 0, MeshedWorld.Orientation.DOWN, new ColorRGBA(1, 1, 1, 1));
		MeshedWorld.createMesh(0, 0, 0, MeshedWorld.Orientation.UP, new ColorRGBA(0, 0, 1, 1));
		MeshedWorld.createMesh(0, 0, 0, MeshedWorld.Orientation.SOUTH, new ColorRGBA(0, 1, 1, 1));
		MeshedWorld.createMesh(0, 0, 0, MeshedWorld.Orientation.WEST, new ColorRGBA(1, 1, 0, 1));
		MeshedWorld.createMesh(0, 0, 0, MeshedWorld.Orientation.EAST, new ColorRGBA(1, 0, 0, 1));
		MeshedWorld.createMesh(0, 0, 0, MeshedWorld.Orientation.NORTH, new ColorRGBA(1, 0, 0, 1));
    }
}