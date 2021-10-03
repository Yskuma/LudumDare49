package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.livelyspark.ludumdare49.components.CameraTargetComponent;
import com.livelyspark.ludumdare49.components.PlayerComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

import java.util.Random;


public class CameraSystem extends IteratingSystem {

    private final OrthographicCamera camera;
    private final ScreenState state;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public CameraSystem(OrthographicCamera camera, ScreenState state) {
        super(Family.all(PositionComponent.class, CameraTargetComponent.class).get());
        this.camera = camera;
        this.state = state;
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        float rumbleAmount = 0.0f;
        float camSpeed = 100.0f;

        switch (state.cameraMode) {
            case Lock:
                rumbleAmount = 0.0f;
                break;
            case ShakeSmall:
                rumbleAmount = 1.5f * camSpeed;
                break;
            case ShakeMedium:
                rumbleAmount = 1.75f * camSpeed;
                break;
            case ShakeLarge:
                rumbleAmount = 2f * camSpeed;
                break;
        }
        Random rand = new Random();
        camera.position.x += rand.nextFloat() * rumbleAmount * deltaTime;
        camera.position.y += rand.nextFloat() * rumbleAmount * deltaTime;

        PositionComponent p = pm.get(entity);
        camera.position.x += MathUtils.clamp(p.x - camera.position.x,-camSpeed * deltaTime,camSpeed* deltaTime);
        camera.position.y += MathUtils.clamp(p.y - camera.position.y,-camSpeed* deltaTime,camSpeed* deltaTime);;
        camera.update();
    }
}
