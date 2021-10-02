package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.livelyspark.ludumdare49.components.CameraTargetComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.enums.Actions;
import com.livelyspark.ludumdare49.gameobj.ActiveActions;


public class TiledRenderSystem extends EntitySystem {

    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer renderer;
    private ActiveActions activeActions;
    private MapLayer coolantLayer;
    private boolean coolantLayerRendering;

    public TiledRenderSystem(OrthogonalTiledMapRenderer renderer, OrthographicCamera camera, ActiveActions activeActions) {
        super();
        this.camera = camera;
        this.renderer = renderer;
        this.activeActions = activeActions;
        coolantLayerRendering = false;
        coolantLayer = renderer.getMap().getLayers().get("CoolantLeak1");
    }

    @Override
    public void update (float deltaTime) {
        CheckCoolantLayer();

        renderer.setView(camera);
        renderer.render();
    }

    private void CheckCoolantLayer() {
        if(activeActions.activeActions.contains(Actions.CoolantLeak)
        && !coolantLayerRendering){
            renderer.getMap().getLayers().add(coolantLayer);
        }
        else if(!activeActions.activeActions.contains(Actions.CoolantLeak)
                && coolantLayerRendering){
            renderer.getMap().getLayers().remove(coolantLayer);
        }
    }
}
