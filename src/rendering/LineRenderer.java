package rendering;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

public class LineRenderer {

    Node parentNode;
    AssetManager assetManager;


    public LineRenderer(Node parentNode, AssetManager assetManager) {
        this.parentNode = parentNode;
        this.assetManager = assetManager;
    }

    public void render(Vector3f start, Vector3f end, ColorRGBA color) {
        Line line = new Line(start, end);
        line.setLineWidth(2);
        Geometry geometry = new Geometry("Bullet", line);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", color);
        geometry.setMaterial(material);
        parentNode.attachChild(geometry);
    }
}
