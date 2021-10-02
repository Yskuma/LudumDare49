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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare49.components.*;
import com.livelyspark.ludumdare49.enums.Shapes;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.*;
import com.livelyspark.ludumdare49.systems.action.ActionableActivateSystem;
import com.livelyspark.ludumdare49.systems.action.ActionableDecaySystem;
import com.livelyspark.ludumdare49.systems.render.ActionRenderSystem;
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
    private ActionableComponent actionableComponent;
    private Label actionLabel;

    public PowerStationScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

        posLabel.setText(playerPos.x + ", " + playerPos.y);
        actionLabel.setText("Act: " + actionableComponent.timeActivated);

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
        actionLabel = new Label("a", uiSkin, "font", Color.WHITE);
        actionLabel.setPosition(0, 40);

        stage.addActor(posLabel);
        stage.addActor(actionLabel);

        TiledMap tiledMap = assetManager.get("tilemaps/testmapsmall.tmx", TiledMap.class);
        TiledMapTileLayer wallLayer = (TiledMapTileLayer)tiledMap.getLayers().get("Walls");
        tiledRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        addEntities();

        //Camera Systems
        engine.addSystem(new CameraSystem(camera));

        //Movement/Position Systems
        engine.addSystem(new PlayerMovementSystem());
        engine.addSystem(new WallCollisionSystem(wallLayer));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpritePositionSystem());

        //Action Systems
        engine.addSystem(new ActionableActivateSystem(playerPos));
        engine.addSystem(new ActionableDecaySystem());

        //Renderers
        engine.addSystem(new TiledRenderSystem(tiledRenderer, camera));
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(new ShapeRenderSystem(camera));
        engine.addSystem(new ActionRenderSystem(camera));
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
                .add(new WallCollisionComponent())
        );

        actionableComponent = new ActionableComponent(10f, 2.0f,32, Color.RED);
        engine.addEntity((new Entity())
                .add(new PositionComponent(150,150))
                .add(actionableComponent)
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(200,150))
                .add(new ActionableComponent(1f, 10.0f,32, Color.GREEN))
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(300,150))
                .add(new ActionableComponent(10f, 0.5f,64, Color.BLUE))
        );

    }

    @Override
    public void hide() {
    }
}
