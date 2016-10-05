package com.saponace.pureGen.rendering;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.saponace.pureGen.generation.World;


public abstract class WorldRenderer {

    /**
     * Set the light sources qnd the shadows
     * @param assetManager The asset manager of the world
     * @param anchor The Node which should host the lights and shadows
     * @param viewPort The viewport of the app
     */
    private static void setLight(AssetManager assetManager, Node anchor,
                                 ViewPort viewPort) {

        anchor.setShadowMode(ShadowMode.CastAndReceive);

        // Ambient light
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.5f));
        anchor.addLight(al);

        // Directionnal light
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-0.5f, -2, -1));
        anchor.addLight(sun);

        // Directionnal light shadows
        DirectionalLightShadowRenderer dlsr;
        dlsr = new DirectionalLightShadowRenderer(assetManager, 2048, 4);
        dlsr.setLight(sun);
        dlsr.setShadowIntensity(0.6f);
        viewPort.addProcessor(dlsr);

        // Ambient Occlusion
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        SSAOFilter ssaoFilter = new SSAOFilter(0.5f, 2f, 1f, 0.1f);
        fpp.addFilter(ssaoFilter);
        viewPort.addProcessor(fpp);
    }
    /**
     * Draw the world
     * @param world The world to draw
     * @param assetManager The asetManager to use
     * @param anchor The Node which should host the world
     * @param viewPort The viewPort of the app
     */
    public static void init(World world, AssetManager assetManager,
                            Node anchor, ViewPort viewPort,
                            RenderingProperties renderingProperties) {
        setLight(assetManager, anchor, viewPort);
        SkyRenderer skyRenderer = new SkyRenderer(anchor, assetManager,
                renderingProperties.skyDistance);
        skyRenderer.draw();
    }
}
