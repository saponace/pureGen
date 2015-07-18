package rendering;



// Camera motion
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import com.jme3.input.controls.AnalogListener;
import com.jme3.renderer.Camera;

public class CamMotion {


    private static boolean isHyperspeedOn = false;
    private static FlyByCamera flyCam;
    private static Camera cam;
    private static GUIInfos guiInfos;


    public CamMotion(InputManager inputManager, FlyByCamera flyCam0,
                     Camera cam0, GUIInfos guiInfos0){
        flyCam = flyCam0;
        cam = cam0;
        guiInfos = guiInfos0;

        setFlyCam(inputManager);
    }

    // Key mapping

    /**
     * Set the keymappings
     * @param inputManager The inputManager to modify
     */
   public static void setFlyCam(InputManager inputManager) {
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


    /**
     * Switch the hyperspeed mode. Basically just a x10 speed multiplier
     */
    private static ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Hyperspeed") && !keyPressed) {
//                guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
                if (isHyperspeedOn == true) {
                    flyCam.setMoveSpeed(flyCam.getMoveSpeed() / 10);
                    guiInfos.printHyperSpeed("Off");
                } else {
                    flyCam.setMoveSpeed(flyCam.getMoveSpeed() * 10);
                    guiInfos.printHyperSpeed("On");
                }
                isHyperspeedOn = !isHyperspeedOn;
            }
        }
    };


    /**
     * Allow the flycam to stay on a horizontal plan when going forward.
     * Basically just does the projection of the direction vector on the
     * horizontal plan
     */
    private static AnalogListener analogListener = new AnalogListener() {
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
