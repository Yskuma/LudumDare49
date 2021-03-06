package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.livelyspark.ludumdare49.components.SpriteComponent;

public class SpriteRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    public SpriteRenderSystem (OrthographicCamera camera) {
        batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(camera == null){
            return;
        }

        SpriteComponent sprite;
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            sprite = sm.get(e);
            sprite.sprite.draw(batch);
        }

        batch.end();
    }
}
