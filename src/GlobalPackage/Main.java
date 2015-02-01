package GlobalPackage;

import jme3tools.optimize.GeometryBatchFactory;
import com.jme3.app.SimpleApplication;


public class Main extends SimpleApplication {

    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }
    World world = new World(100, 6, 100);

    @Override
    public void simpleInitApp() {
	WorldDrawer.drawWorld(world, assetManager, rootNode);
	GeometryBatchFactory.optimize(rootNode);
    }
}