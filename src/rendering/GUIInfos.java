package rendering;

// Text & font 
import com.jme3.font.BitmapText;
import com.jme3.font.BitmapFont;
// AssetManager
import com.jme3.asset.AssetManager;
// guiNode
import com.jme3.scene.Node;
// Vector3f
import com.jme3.math.Vector3f;

public class GUIInfos {

	 private BitmapFont guiFont;
	 private AssetManager assetManager;
	private Node guiNode;

	// private int winWidth;
	// private int winHeight;

	public GUIInfos(AssetManager assetManager,
			Node guiNode/*, int winWidth, int winHeight*/) {
		// this.guiFont = guiFont;
        this.assetManager = assetManager;
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        this.guiNode = guiNode;
//		this.winWidth = winWidth;
//		this.winHeight = winHeight;
	}

	// Print the given text on the given location
	private void printText(String textName, String str, int x, int y) {

		// Remove previous text pinted here
		guiNode.detachChildNamed(textName);

		BitmapText camLocationText = new BitmapText(guiFont, false);
		camLocationText.setName(textName);
		camLocationText.setSize(guiFont.getCharSet().getRenderedSize());
		camLocationText.setText(str);
		camLocationText.setLocalTranslation(x, y, 0);
		guiNode.attachChild(camLocationText);

	}

	// Print "Hyperspeed On" or "Hyperspeed Off" on the screen
	public void printHyperSpeed(String onOrOff){
		printText("hyperspeed", "Hyperspeed " + onOrOff, 0, 300);
	}

	// Print "Hyperspeed On" or "Hyperspeed Off" on the screen
	public void printflyCamLocation(Vector3f camLocation) {
		printText("camLocation", camLocation.toString(), 0, 400);
	}
}
