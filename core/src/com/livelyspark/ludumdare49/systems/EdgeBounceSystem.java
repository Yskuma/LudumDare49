package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.livelyspark.ludumdare49.components.EdgeBounceComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;


public class EdgeBounceSystem extends IteratingSystem {

    private Rectangle bounds;

    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<VelocityComponent> mm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<EdgeBounceComponent> eb = ComponentMapper.getFor(EdgeBounceComponent.class);

    public EdgeBounceSystem(Rectangle bounds) {
        super(Family.all(PositionComponent.class, VelocityComponent.class, EdgeBounceComponent.class).get());
        this.bounds = bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        SpriteComponent sprite = sm.get(entity);
        VelocityComponent velocity = mm.get(entity);

        Rectangle sRect = sprite.sprite.getBoundingRectangle();

        if(velocity.x < 0 && sRect.x < 0)
        {
            velocity.x *= -1;
        }

        if(velocity.y < 0 && sRect.y < 0)
        {
            velocity.y *= -1;
        }

        if(velocity.x > 0 && sRect.x + sRect.width > bounds.width)
        {
            velocity.x *= -1;
        }

        if(velocity.y > 0 && sRect.y + sRect.height > bounds.height)
        {
            velocity.y *= -1;
        }
    }
}
