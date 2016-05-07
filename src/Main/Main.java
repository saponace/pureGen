package Main;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import generation.World;
import motion.CamMotion;
import rendering.BlockRenderer;
import rendering.GUIInfos;
import rendering.WorldRenderer;
import rendering.nodeManagers.WorldNodeManager;
import utils.Position3D;

// Window settings
// Hyperspeed
// Camera motion


public class Main extends SimpleApplication {


    /**
     * Informations displayed on the screen (cam position, etc.)
     */
	GUIInfos guiInfos;
    /**
     * The world
     */
    World world;
    /**
     * A flag that allows to execute code in the simpleUpdate loop only once
     */
    private static boolean firstLoopOfSimpleUpdate = true;


	public static void main(String[] args) {
		Main app = new Main();

        app.setShowSettings(false);
		AppSettings settings = setAppSettings();
		app.start();
		app.setSettings(settings);

	}

	@Override
	public void simpleInitApp() {

        guiInfos = new GUIInfos(assetManager, guiNode);

        WorldNodeManager.setParentNode(rootNode);
        world = new World(GlobalParameters.chunkSize);
		WorldRenderer.init(world, assetManager, rootNode, viewPort);
        BlockRenderer.setAssetManager(assetManager);
        BlockRenderer.setWorld(world);
	}


	@Override
	public void simpleUpdate(float tpf) {
		if (firstLoopOfSimpleUpdate) {
			firstLoopOfSimpleUpdate = false;
            CamMotion camMotion = new CamMotion(inputManager, flyCam, cam, guiInfos);
            camMotion.setFlyCam(inputManager);
		}
        Vector3f floatCamLocation = cam.getLocation();
        Position3D intCamLocation = new Position3D(
                (int) floatCamLocation.x,
                (int) floatCamLocation.y,
                (int) floatCamLocation.z);
		guiInfos.printflyCamLocation(floatCamLocation);
        world.generateChunksAround(intCamLocation, GlobalParameters
                .nearChunkDisplayingRadius);
        world.getNodeManager().attachChunksAround(intCamLocation,
                GlobalParameters.nearChunkDisplayingRadius);
	}


    /**
     * Allows to set the settings of the application (window size, vsync, etc.)
     * @return The settings of the app
     */
	private static AppSettings setAppSettings() {
		AppSettings settings = new AppSettings(true);
		settings.put("Width", 800);
		settings.put("Height", 600);
		settings.put("Title", "3DGen");
		settings.put("VSync", true);
		settings.put("Samples", 4); // Anti-Aliasing
		return settings;
	}


}