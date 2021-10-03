package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.gameobj.PowerStation;

public class ControlRodAnimationSystem extends EntitySystem {

    private Entity controlRods;
    private TextureAtlas atlas;
    private PowerStation powerStation;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ControlRodAnimationSystem(AssetManager assetManager, PowerStation powerStation){
        this.powerStation = powerStation;
        atlas = assetManager.get("textures/actionables.atlas", TextureAtlas.class);
    }

    public void GenerateEntities(){
        TextureAtlas.AtlasRegion controlRod = atlas.findRegion("ControlRods");

        controlRods = new Entity()
                .add(new PositionComponent(224,848))
                .add(new SpriteComponent(new Sprite(controlRod)));

        this.getEngine().addEntity(controlRods);
    }

    public void update(float deltaTime) {
        PositionComponent controlRodPosition = pm.get(controlRods);

        controlRodPosition.y = 888 - powerStation.controlRodPosition * 40;
    }

}
