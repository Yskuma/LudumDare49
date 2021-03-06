package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> mm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem () {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        VelocityComponent velocity = mm.get(entity);

        position.add(new Vector2(velocity).scl(deltaTime));
    }
}
