package GlobalPackage;

import jme3tools.optimize.GeometryBatchFactory;

import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;


//import com.jme3.material.Material;
//import com.jme3.material.RenderState.BlendMode;
//import com.jme3.math.ColorRGBA;
//import com.jme3.renderer.queue.RenderQueue.Bucket;
//import com.jme3.scene.Geometry;
//import com.jme3.scene.shape.Box;


public class Main extends SimpleApplication {

    public static void main(String[] args){
        Main app = new Main();
        app.start();
    }


    
    
    @Override
    public void simpleInitApp() {
//        Box cube2Mesh = new Box( 1f,1f,0.01f);
//        Geometry cube2Geo = new Geometry("window frame", cube2Mesh);
//        Material cube2Mat = new Material(assetManager, 
//        "Common/MatDefs/Misc/Unshaded.j3md");
//        cube2Mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);  // !
//        cube2Geo.setQueueBucket(Bucket.Transparent);                        // !
//        cube2Geo.setMaterial(cube2Mat);
//        cube2Mat.setColor("Color", new ColorRGBA(0, 0, 1, (float)0.5));
//        rootNode.attachChild(cube2Geo);	


    	
    	
    	
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