package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;

public class ActionHintRenderSystem extends EntitySystem {
    private final SpriteBatch batch;
    private final Vector2 playerPos;
    private final Sprite sprite;
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ActionHintRenderSystem(OrthographicCamera camera, Vector2 playerPos, AssetManager assetManager) {
        batch = new SpriteBatch();
        this.camera = camera;
        this.playerPos = playerPos;

        TextureAtlas atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);
        this.sprite = new Sprite(atlas.findRegion("arrow"));
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ActionableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {

    }

    @Override
    public void update(float deltaTime) {
        if (camera == null) {
            return;
        }
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            Vector2 pos = pm.get(e);
            Color c = am.get(e).color;

            Vector2 pointVec = (new Vector2(pos)).sub(playerPos).nor();
            float dir = pointVec.angleDeg();

            sprite.setCenter((pointVec.x * 16) + playerPos.x, (pointVec.y * 16) + playerPos.y);
            sprite.setRotation(dir-90);
            sprite.setColor(c);
            sprite.setAlpha(0.3f);
            sprite.draw(batch);
        }

        batch.end();
    }
}
