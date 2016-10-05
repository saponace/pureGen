package com.saponace.pureGen.Main;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.saponace.pureGen.generation.World;
import com.saponace.pureGen.motion.CamMotion;
import com.saponace.pureGen.rendering.BlockRenderer;
import com.saponace.pureGen.rendering.GUIInfos;
import com.saponace.pureGen.rendering.RenderingProperties;
import com.saponace.pureGen.rendering.WorldRenderer;
import com.saponace.pureGen.rendering.nodeManagers.WorldNodeManager;
import com.saponace.pureGen.utils.Position3D;

import java.io.FileNotFoundException;


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
	/**
	 * General properties
	 */
	private static GeneralProperties generalProperties;
	/**
	 * Rendering properties
	 */
	private static RenderingProperties renderingProperties;


	public static void main(String[] args) {
		Main app = new Main();


		try {
			generalProperties = new GeneralProperties(
					new PropertiesFileParser().parse("general.properties"));
			renderingProperties = new RenderingProperties(
					new PropertiesFileParser().parse("rendering.properties"));

			app.setShowSettings(false);
			AppSettings settings = setAppSettings();
			app.start();
			app.setSettings(settings);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void simpleInitApp() {

        guiInfos = new GUIInfos(assetManager, guiNode);

        WorldNodeManager.setParentNode(rootNode);
        world = new World();
		WorldRenderer.init(world, assetManager, rootNode, viewPort, renderingProperties);
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
        world.generateChunksAround(intCamLocation);
        world.getNodeManager().attachChunksAround(intCamLocation,
				renderingProperties.nearChunksDisplayRadius);
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