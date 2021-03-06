package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;

public class SpritePositionSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    public SpritePositionSystem () {
        super(Family.all(PositionComponent.class, SpriteComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        PositionComponent position = pm.get(entity);
        SpriteComponent sprite = sm.get(entity);

        sprite.sprite.setCenter(position.x, position.y);
    }
}
