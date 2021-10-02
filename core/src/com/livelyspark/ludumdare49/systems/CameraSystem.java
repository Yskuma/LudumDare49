package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.livelyspark.ludumdare49.components.CameraTargetComponent;
import com.livelyspark.ludumdare49.components.PlayerComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;


public class CameraSystem extends IteratingSystem {

    private final OrthographicCamera camera;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(PositionComponent.class, CameraTargetComponent.class).get());
        this.camera = camera;
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        PositionComponent p = pm.get(entity);
        camera.position.x = p.x;
        camera.position.y = p.y;
        camera.update();
    }
}
