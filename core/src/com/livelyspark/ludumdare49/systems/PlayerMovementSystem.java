package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.*;


public class PlayerMovementSystem extends IteratingSystem {

    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PlayerComponent>pm = ComponentMapper.getFor(PlayerComponent.class);

    public PlayerMovementSystem() {
        super(Family.all(VelocityComponent.class, PlayerComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {

        VelocityComponent velocity = vm.get(entity);

        float speed = 100f;
        Vector2 velVec = new Vector2();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
        {
            velVec.x -= 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
        {
            velVec.x += 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
        {
            velVec.y -= 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
        {
            velVec.y += 1;
        }

        velVec = velVec.nor().scl(speed);
        velocity.x = velVec.x;
        velocity.y = velVec.y;
    }
}
