package com.livelyspark.ludumdare49.systems.sound;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.EffectComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SoundComponent;

public class SoundLocalSystem extends EntitySystem {
    private final Vector2 playerPos;
    private ImmutableArray<Entity> entities;

    private ComponentMapper<SoundComponent> sm = ComponentMapper.getFor(SoundComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public SoundLocalSystem(Vector2 playerPos) {
        this.playerPos = playerPos;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SoundComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        for (Entity e : entities) {
            SoundComponent s = sm.get(e);
            PositionComponent p = pm.get(e);

            float dist = p.dst(playerPos);

            float vol = dist == 0 ? 1.0f : 1/((dist/32)*(dist/32));
            if(dist > 176){
                vol = 0;
            }
            s.sound.setVolume(s.soundId, vol);
        }
    }
}
