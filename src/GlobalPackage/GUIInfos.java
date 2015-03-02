package GlobalPackage;

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
	
//	private BitmapFont guiFont;
//	private AssetManager assetManager;
	private Node guiNode;
	
	
	public GUIInfos(BitmapFont guiFont, AssetManager assetManager, Node guiNode) {
//		this.guiFont = guiFont;
//		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//		this.assetManager = assetManager;
		this.guiNode = guiNode;
	}
	
	
	// Print "Hyperspeed On" or "Hyperspeed Off" on the screen
	public void printHyperSpeed(String onOrOff, BitmapFont guiFont){

		// Remove precedent hyperspeed state
		guiNode.detachChildNamed("hyperspeedText");

		BitmapText hyperspeedText = new BitmapText(guiFont, false);
		hyperspeedText.setName("hyperspeedText");
		hyperspeedText.setSize(guiFont.getCharSet().getRenderedSize());
		hyperspeedText.setText("Hyperspeed " + onOrOff);
		hyperspeedText.setLocalTranslation(300, hyperspeedText.getLineHeight(), 0);
		guiNode.attachChild(hyperspeedText);	
	}
	

}
