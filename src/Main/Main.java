package Main;

import rendering.CamMotion;
import rendering.GUIInfos;
import rendering.WorldDrawer;
import com.jme3.app.SimpleApplication;

// Window settings
import com.jme3.system.AppSettings;

// Hyperspeed

// Camera motion
import generation.Chunks;
import generation.World;

import static Main.DebugUtils.printDebug;


public class Main extends SimpleApplication {


	// GUIInfo, infos displayed on the screen
	GUIInfos guiInfos;

    Chunks chunks;

	public static void main(String[] args) {
		Main app = new Main();

        app.setShowSettings(false);
		AppSettings settings = setAppSettings();
		app.start();
		app.setSettings(settings);
	}

	@Override
	public void simpleInitApp() {

		// World generation
		printDebug("Generating world ...");
		World world = new World(128, 64, 128);
		printDebug("World generated !");
        guiInfos = new GUIInfos(assetManager, guiNode);

        chunks = world.chunks;

		// World drawing
		printDebug("\nDisplaying world ...");
		WorldDrawer.drawWorld(world, assetManager, rootNode, viewPort);
		printDebug("World Displayed !");


	}

	// Used to remap flycam controls
	private static boolean firstLoopOfSimpleUpdate = true;
	// Boolean that says is hyperspeed mode is enabled
	private static boolean isHyperspeedOn = false;

	@Override
	public void simpleUpdate(float tpf) {
		if (firstLoopOfSimpleUpdate) {
            CamMotion camMotion = new CamMotion(inputManager, flyCam, cam, guiInfos);
			camMotion.setFlyCam(inputManager);
			firstLoopOfSimpleUpdate = false;
		}
		guiInfos.printflyCamLocation(cam.getLocation());
		chunks.displayCloseChunks(cam.getLocation(), rootNode);
		// Chunks.displayChunksInFrustum(cam.getLocation());
		// System.out.println(cam.getFrustumBottom());
	}


	// Application settings (window size, vsync ...
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