package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.ShapeComponent;

public class ActionRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    private ShapeRenderer renderer;

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ActionRenderSystem(OrthographicCamera camera) {
        renderer = new ShapeRenderer();

        //TODO: Fix so shapes are sorted by Type and we manually flush between types
        renderer.setAutoShapeType(true);
        this.camera = camera;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ActionableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(camera == null){
            return;
        }

        camera.update();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin();

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            ActionableComponent a = am.get(e);
            PositionComponent p = pm.get(e);

            renderer.setColor(a.color);

            renderer.set(ShapeRenderer.ShapeType.Line);
            renderer.rect(p.x - (a.size/2),p.y - (a.size/2),a.size,a.size);

            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rect(p.x - (a.size/2), p.y - (a.size/2) + a.size + 5, a.size * (a.timeActivated/a.timeToActivate), 10);
        }

        renderer.end();
    }
}
