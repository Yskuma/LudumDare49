package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.EffectComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.RumbleComponent;
import com.livelyspark.ludumdare49.enums.CameraModes;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class RumbleSystem extends EntitySystem {

    private final ScreenState state;
    private ImmutableArray<Entity> entities;

    private ComponentMapper<RumbleComponent> rm = ComponentMapper.getFor(RumbleComponent.class);

    public RumbleSystem(ScreenState state) {
        this.state = state;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(RumbleComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {

        state.cameraMode = CameraModes.Lock;

        for (Entity e : entities) {
            RumbleComponent rumble = rm.get(e);
            rumble.timeRemaining -= deltaTime;

            if (rumble.timeRemaining < 0) {
                e.remove(RumbleComponent.class);
                if (e.getComponents().size() == 0) {
                    this.getEngine().removeEntity(e);
                }
            } else {
                if (rumble.cameraMode.ordinal() > state.cameraMode.ordinal()) {
                    state.cameraMode = rumble.cameraMode;
                }
            }
        }

    }
}
