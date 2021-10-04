package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.CommandComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;

import java.util.Objects;

public class ActionableCommandRenderSystem extends EntitySystem {
    private final SpriteBatch batch;
    private final NinePatch borderNp;
    private final NinePatch progressNp;
    private final Skin uiSkin;
    private final BitmapFont font;
    private ImmutableArray<Entity> entities;

    private OrthographicCamera camera;
    //private ShapeRenderer renderer;

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public ActionableCommandRenderSystem(OrthographicCamera camera, AssetManager assetManager) {
        this.batch = new SpriteBatch();
        this.camera = camera;

        TextureAtlas atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);
        borderNp = new NinePatch(atlas.findRegion("border2"), 8, 8, 8, 8);
        progressNp = new NinePatch(atlas.findRegion("progress2"), 2, 2, 2, 2);

        uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        font = uiSkin.getFont("small");
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

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            ActionableComponent a = am.get(e);
            PositionComponent p = pm.get(e);

            borderNp.setColor(a.color);
            borderNp.draw(batch, p.x - (a.size/2), p.y - (a.size/2), a.size, a.size);

            if(a.size * (a.timeActivated/a.timeToActivate) > 2) {
                progressNp.setColor(a.color);
                progressNp.draw(batch, p.x - (a.size / 2), p.y + (a.size / 2) - a.size - 8, a.size * (a.timeActivated / a.timeToActivate), 6);
            }

            if(!Objects.equals(a.label, ""))
            {
                GlyphLayout layout = new GlyphLayout(font, a.label, Color.WHITE, a.size, Align.center, false);
                font.draw(batch, a.label, p.x - (layout.width / 2) , p.y + a.size, a.size, Align.center, false);
            }

        }

        batch.end();
    }
}
