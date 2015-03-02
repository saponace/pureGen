package GlobalPackage;

import com.jme3.app.SimpleApplication;

// Window settings
import com.jme3.system.AppSettings;


// Hyperspeed
import com.jme3.input.controls.ActionListener;

// Camera motion
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import com.jme3.input.controls.AnalogListener;

// Water shaders tests
import com.jme3.math.Plane;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.water.SimpleWaterProcessor;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;

public class Main extends SimpleApplication {

	// If set to true, details about the generation will be printed in the
	// console
	static boolean debug = true;
	
	// GUIInfo, infos displayed on the screen
	GUIInfos guiInfos = new GUIInfos(guiFont, assetManager, guiNode);

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
		World world = new World(128, 64, 128);
		if (debug)
			System.out.println("World generated");

		// World drawing
		WorldDrawer.drawWorld(world, assetManager, rootNode, viewPort);
		if (debug)
			System.out.println("World Displayed");

		
		
		
		
		// we create a water processor
		SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(
				assetManager);
		waterProcessor.setReflectionScene(rootNode);

		int waterX = -200;
		int waterY = 0;
		int waterZ = 250;

		// we set the water plane
		Vector3f waterLocation = new Vector3f(waterX, waterY, waterZ);
		waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation
				.dot(Vector3f.UNIT_Y)));
		viewPort.addProcessor(waterProcessor);

		// we set wave properties
		waterProcessor.setWaterDepth(40); // transparency of water
		waterProcessor.setDistortionScale(0.05f); // strength of waves
		waterProcessor.setWaveSpeed(0.02f); // speed of waves

		// we define the wave size by setting the size of the texture
		// coordinates
		Quad quad = new Quad(400, 400);
		quad.scaleTextureCoordinates(new Vector2f(6f, 6f));

		// we create the water geometry from the quad
		Geometry water = new Geometry("water", quad);
		water.setLocalRotation(new Quaternion().fromAngleAxis(
				-FastMath.HALF_PI, Vector3f.UNIT_X));
		water.setLocalTranslation(waterX, waterY, waterZ);
		water.setShadowMode(ShadowMode.Receive);
		water.setMaterial(waterProcessor.getMaterial());
		// rootNode.attachChild(water);
	}

	
	
	

	// Used to remap flycam controls
	private static boolean firstLoopOfSimpleUpdate = true;
	// Boolean that says is hyperspeed mode is enabled
	private static boolean isHyperspeedOn = false;

	@Override
	public void simpleUpdate(float tpf) {
		if (firstLoopOfSimpleUpdate) {
			setFlycam();
			firstLoopOfSimpleUpdate = false;
		}
		guiInfos.printflyCamLocation(cam.getLocation(), guiFont);
		Chunks.displayCloseChunks(cam.getLocation(), rootNode);
		// Chunks.displayChunksInFrustum(cam.getLocation());
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

	// Key mapping
	private void setFlycam() {
		// Flycam controls
		inputManager.deleteMapping("FLYCAM_Forward");
		inputManager.deleteMapping("FLYCAM_Backward");
		inputManager.deleteMapping("FLYCAM_StrafeLeft");
		inputManager.deleteMapping("FLYCAM_StrafeRight");
		inputManager.deleteMapping("FLYCAM_Rise");
		inputManager.deleteMapping("FLYCAM_Lower");
		inputManager.addMapping("FLYCAM_Forward",
				new KeyTrigger(KeyInput.KEY_Z));
		inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(
				KeyInput.KEY_S));
		inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(
				KeyInput.KEY_Q));
		inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(
				KeyInput.KEY_D));
		inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(
				KeyInput.KEY_SPACE));
		inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(
				KeyInput.KEY_LSHIFT));
		inputManager.addMapping("Hyperspeed", new KeyTrigger(KeyInput.KEY_E));

		inputManager.addListener(flyCam, new String[] { "FLYCAM_StrafeLeft",
				"FLYCAM_StrafeRight", "FLYCAM_Rise", "FLYCAM_Lower" });
		inputManager.addListener(analogListener, "FLYCAM_Forward",
				"FLYCAM_Backward");
		inputManager.addListener(actionListener, "Hyperspeed");

		// Move speed
		flyCam.setMoveSpeed(100);
		flyCam.setRotationSpeed(2);
		// View distance
		cam.setFrustumFar(50000);
		// Begining location
		cam.setLocation(new Vector3f(0, 50, 0));
		cam.lookAtDirection(new Vector3f(1, -0.4f, 1), Vector3f.UNIT_Y);
	}

	// Hyperspeed switch
	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("Hyperspeed") && !keyPressed) {
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
				if (isHyperspeedOn == true){
					flyCam.setMoveSpeed(flyCam.getMoveSpeed() / 10);
					guiInfos.printHyperSpeed("Off", guiFont);
				}
				else{
					flyCam.setMoveSpeed(flyCam.getMoveSpeed() * 10);
					guiInfos.printHyperSpeed("On", guiFont);
				}
				isHyperspeedOn = !isHyperspeedOn;
			}
		}
	};
	
	// FlyCam forward & backward

	// Flycam moving forward ad backward
	private AnalogListener analogListener = new AnalogListener() {
		public void onAnalog(String name, float value, float tpf) {
			if (name.equals("FLYCAM_Forward") || name.equals("FLYCAM_Backward")) {
				Vector3f currentCamLocation = cam.getLocation();
				Vector3f camDirection = cam.getDirection();
				Vector3f speedArray = camDirection.add(cam.getUp());
				Vector3f projectedSpArray = speedArray.project(Vector3f.UNIT_X);
				projectedSpArray.addLocal(speedArray.project(Vector3f.UNIT_Z));
				double prSpArNorm = Math.sqrt(Math.pow(projectedSpArray.getX(),
						2) + Math.pow(projectedSpArray.getZ(), 2));
				projectedSpArray.multLocal((float) prSpArNorm / 100
						* flyCam.getMoveSpeed());

				if (name.equals("FLYCAM_Backward"))
					projectedSpArray.multLocal(-1);

				Vector3f newCamLocation = currentCamLocation
						.add(projectedSpArray);
				cam.setLocation(new Vector3f(newCamLocation));
			}
		}
	};

}