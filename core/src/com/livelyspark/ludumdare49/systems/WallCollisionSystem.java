package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;
import com.livelyspark.ludumdare49.components.WallCollisionComponent;

public class WallCollisionSystem extends IteratingSystem {

    private final TiledMapTileLayer tileLayer;
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<VelocityComponent> mm = ComponentMapper.getFor(VelocityComponent.class);

    public WallCollisionSystem(TiledMapTileLayer tileLayer) {
        super(Family.all(SpriteComponent.class, VelocityComponent.class, WallCollisionComponent.class).get());
        this.tileLayer = tileLayer;
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        SpriteComponent sprite = sm.get(entity);
        VelocityComponent velocity = mm.get(entity);

        Rectangle br = sprite.sprite.getBoundingRectangle();

        if(velocity.y > 0)
        {
            int tileX = (int)(br.x / 16);
            int tileY = (int)((br.y + br.height + (velocity.y * deltaTime)) / 16);
            boolean hasWall = tileLayer.getCell(tileX, tileY) != null;
            if(hasWall){
                velocity.y = 0;
            }
        }

        if(velocity.y < 0)
        {
            int tileX = (int)(br.x / 16);
            int tileY = (int)((br.y + (velocity.y * deltaTime)) / 16);
            boolean hasWall = tileLayer.getCell(tileX, tileY) != null;
            if(hasWall){
                velocity.y = 0;
            }
        }

        if(velocity.x > 0)
        {
            int tileX = (int)((br.x + br.width + (velocity.x * deltaTime)) / 16);
            int tileY = (int)(br.y / 16);
            boolean hasWall = tileLayer.getCell(tileX, tileY) != null;
            if(hasWall){
                velocity.x = 0;
            }
        }

        if(velocity.x < 0)
        {
            int tileX = (int)((br.x + (velocity.x * deltaTime))  / 16);
            int tileY = (int)(br.y / 16);
            boolean hasWall = tileLayer.getCell(tileX, tileY) != null;
            if(hasWall){
                velocity.x = 0;
            }
        }
    }
}
