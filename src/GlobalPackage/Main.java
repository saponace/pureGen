package GlobalPackage;

// SimpleApplication 
import com.jme3.app.SimpleApplication;


// Camera motion
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
// Colors, used by background
import com.jme3.math.ColorRGBA;



//import com.jme3.scene.Node;
//import com.jme3.math.Plane;
//import com.jme3.math.FastMath;
//import com.jme3.math.Quaternion;
//import com.jme3.scene.Geometry;
//import com.jme3.scene.shape.Quad;
//import com.jme3.water.SimpleWaterProcessor;
//import com.jme3.math.Vector3f;
//import com.jme3.math.Vector2f;
//import com.jme3.renderer.queue.RenderQueue.ShadowMode;


public class Main extends SimpleApplication {
	public static void main(String[] args){
		Main app = new Main();
		app.start();
	}


	@Override
	public void simpleInitApp() {
		// World generation
		World world = new World(200, 30, 200);
		

		// World drawing
		WorldDrawer.drawWorld(world, assetManager, rootNode, viewPort);
		// Background color 
		viewPort.setBackgroundColor(new ColorRGBA(0f, 0.5f, 1f, 0f));

		
	}


	
	
	
	// Used to remap flycam controls
	boolean firstLoopOfSimpleUpdate = true;
	@Override
	public void simpleUpdate (float tpf) {
		if(firstLoopOfSimpleUpdate){
			inputManager.deleteMapping("FLYCAM_Forward");
			inputManager.deleteMapping("FLYCAM_Backward");
			inputManager.deleteMapping("FLYCAM_StrafeLeft");
			inputManager.deleteMapping("FLYCAM_StrafeRight");
			inputManager.deleteMapping("FLYCAM_Rise");	
			inputManager.deleteMapping("FLYCAM_Lower");
			inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_Z));
			inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_S));
			inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_Q));
			inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(KeyInput.KEY_D));
			inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(KeyInput.KEY_SPACE));
			inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(KeyInput.KEY_LSHIFT));
			inputManager.addListener(flyCam, new String[] {"FLYCAM_Forward", "FLYCAM_Backward", "FLYCAM_StrafeLeft", "FLYCAM_StrafeRight", "FLYCAM_Rise", "FLYCAM_Lower"});
			flyCam.setMoveSpeed(20);	
			firstLoopOfSimpleUpdate = false;
		}
	}
}