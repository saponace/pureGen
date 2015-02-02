package GlobalPackage;


import com.jme3.app.SimpleApplication;

import jme3tools.optimize.GeometryBatchFactory;

import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;




public class Main extends SimpleApplication {

	public static void main(String[] args){
		Main app = new Main();
		app.start();
	}




	@Override
	public void simpleInitApp() {

		World world = new World(30, 6, 30);
		WorldDrawer.drawWorld(world, assetManager, rootNode);

		GeometryBatchFactory.optimize(rootNode);
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