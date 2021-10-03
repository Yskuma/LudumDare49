package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.CommandComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;

public class ActionableCommandRenderSystem extends EntitySystem {
    private final SpriteBatch batch;
    private final NinePatch borderNp;
    private final ShapeRenderer renderer;
    private final NinePatch progressNp;
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    //private ShapeRenderer renderer;

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ActionableCommandRenderSystem(OrthographicCamera camera, AssetManager assetManager) {
        this.batch = new SpriteBatch();
        this.camera = camera;
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        TextureAtlas atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);
        borderNp = new NinePatch(atlas.findRegion("border"), 8, 8, 8, 8);
        progressNp = new NinePatch(atlas.findRegion("progress2"), 2, 2, 2, 2);

        //renderer = new ShapeRenderer();

        //TODO: Fix so shapes are sorted by Type and we manually flush between types
        //renderer.setAutoShapeType(true);

    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ActionableComponent.class, PositionComponent.class, CommandComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        if (camera == null) {
            return;
        }
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        //renderer.begin();

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            ActionableComponent a = am.get(e);
            PositionComponent p = pm.get(e);

            borderNp.setColor(a.color);
            borderNp.draw(batch, p.x - (a.size/2), p.y - (a.size/2), a.size, a.size);

            if(a.size * (a.timeActivated/a.timeToActivate) > 2) {
                progressNp.setColor(a.color);
                progressNp.draw(batch, p.x - (a.size / 2), p.y - (a.size / 2) + a.size + 2, a.size * (a.timeActivated / a.timeToActivate), 6);
            }

        }
        //renderer.end();
        batch.end();
    }
}