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
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public WallCollisionSystem(TiledMapTileLayer tileLayer) {
        super(Family.all(SpriteComponent.class, PositionComponent.class, VelocityComponent.class, WallCollisionComponent.class).get());
        this.tileLayer = tileLayer;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = sm.get(entity);
        PositionComponent pos = pm.get(entity);
        VelocityComponent vel = vm.get(entity);
        Rectangle br = sprite.sprite.getBoundingRectangle();

        int xleft = (int)((br.x) / 16);
        int xright = (int)((br.x + br.width)/ 16);
        int ytop = (int)((br.y + br.height) / 16);
        int ybottom= (int)((br.y) / 16);

        boolean walltl = (tileLayer.getCell(xleft, ytop) != null);
        boolean walltr = (tileLayer.getCell(xright, ytop) != null);
        boolean wallbl = (tileLayer.getCell(xleft, ybottom) != null);
        boolean wallbr = (tileLayer.getCell(xright, ybottom) != null);

        if((walltl || walltr) && vel.y > 0)
        {
            pos.y = (ytop * 16) - (br.height / 2) - 0.1f;
        }

        if((wallbl || wallbr) && vel.y < 0)
        {
            pos.y = (ytop * 16) + (br.height / 2) + 0.1f;
        }

        if((walltr || wallbr) && vel.x > 0)
        {
            pos.x = (xright * 16) - (br.width / 2) - 0.1f;
        }

        if((walltl || wallbl) && vel.x < 0)
        {
            pos.x = (xright * 16) + (br.width / 2) + 0.1f;
        }

        sprite.sprite.setCenter(pos.x, pos.y);
    }
}
