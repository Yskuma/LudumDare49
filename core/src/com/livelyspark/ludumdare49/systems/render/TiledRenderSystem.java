package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.livelyspark.ludumdare49.components.CameraTargetComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;


public class TiledRenderSystem extends EntitySystem {

    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer renderer;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public TiledRenderSystem(OrthogonalTiledMapRenderer renderer, OrthographicCamera camera) {
        super();
        this.camera = camera;
        this.renderer = renderer;
    }

    @Override
    public void update (float deltaTime) {
        renderer.setView(camera);
        renderer.render();
    }
}
