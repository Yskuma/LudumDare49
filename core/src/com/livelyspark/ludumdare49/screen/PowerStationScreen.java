package com.livelyspark.ludumdare49.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare49.components.*;
import com.livelyspark.ludumdare49.enums.Shapes;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.*;
import com.livelyspark.ludumdare49.systems.render.ShapeRenderSystem;
import com.livelyspark.ludumdare49.systems.render.SpriteRenderSystem;
import com.livelyspark.ludumdare49.systems.render.TiledRenderSystem;

public class PowerStationScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;
    private OrthogonalTiledMapRenderer tiledRenderer;
    private Label posLabel;
    private PositionComponent playerPos;

    public PowerStationScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

        posLabel.setText(playerPos.x + ", " + playerPos.y);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2, height / 2, 0);
        camera.update();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(640,480);
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        posLabel = new Label("a,a", uiSkin, "font", Color.WHITE);
        stage.addActor(posLabel);
        //stage.addActor(titleLabel);

        tiledRenderer = new OrthogonalTiledMapRenderer(assetManager.get("tilemaps/testmapsmall.tmx", TiledMap.class));
        addEntities();

        engine.addSystem(new CameraSystem(camera));
        engine.addSystem(new PlayerMovementSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpritePositionSystem());

        //Renderers
        engine.addSystem(new TiledRenderSystem(tiledRenderer, camera));
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(new ShapeRenderSystem(camera));
    }

    private void addEntities() {
        TextureAtlas atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);

        TextureAtlas.AtlasRegion dude = atlas.findRegion("dude");

        playerPos = new PositionComponent(0,0);
        engine.addEntity((new Entity())
                .add(playerPos)
                .add(new VelocityComponent(0,0))
                .add(new SpriteComponent(new Sprite(dude)))
                .add(new PlayerComponent())
                .add(new CameraTargetComponent())
                .add(new ShapeComponent(Shapes.CIRCLE, Color.RED, 32))
        );

    }

    @Override
    public void hide() {
    }
}
