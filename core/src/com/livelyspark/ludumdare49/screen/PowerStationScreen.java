package com.livelyspark.ludumdare49.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ActiveActions;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.*;
import com.livelyspark.ludumdare49.systems.action.ActionableActivateSystem;
import com.livelyspark.ludumdare49.systems.action.ActionableCompleteSystem;
import com.livelyspark.ludumdare49.systems.action.ActionableDecaySystem;
import com.livelyspark.ludumdare49.input.DebugControlSystem;
import com.livelyspark.ludumdare49.systems.debug.DebugReactorSystem;
import com.livelyspark.ludumdare49.systems.render.ActionRenderSystem;
import com.livelyspark.ludumdare49.systems.render.ShapeRenderSystem;
import com.livelyspark.ludumdare49.systems.render.SpriteRenderSystem;
import com.livelyspark.ludumdare49.systems.render.TiledRenderSystem;
import com.livelyspark.ludumdare49.systems.stages.Stage01System;

public class PowerStationScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;
    private OrthogonalTiledMapRenderer tiledRenderer;
    private Label posLabel;
    private PositionComponent playerPos;
    private ActionableComponent actionableComponent;
    private Label actionLabel;
    private PowerStation powerStation;
    private InputMultiplexer inputMultiplexer;

    public PowerStationScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

        posLabel.setText(playerPos.x + ", " + playerPos.y);
        //actionLabel.setText("Act: " + actionableComponent.timeActivated);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        camera = new OrthographicCamera(320,240);
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        posLabel = new Label("a,a", uiSkin, "font", Color.WHITE);
        actionLabel = new Label("a", uiSkin, "font", Color.WHITE);
        actionLabel.setPosition(0, 40);

        stage.addActor(posLabel);
        stage.addActor(actionLabel);

        //TiledMap tiledMap = assetManager.get("tilemaps/testmapsmall.tmx", TiledMap.class);
        TiledMap tiledMap = assetManager.get("tilemaps/powerstation.tmx", TiledMap.class);
        tiledRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        addEntities();

        powerStation = new PowerStation();

        ActiveActions activeActions = new ActiveActions();

        //Camera Systems
        engine.addSystem(new CameraSystem(camera));

        //Movement/Position Systems
        engine.addSystem(new PlayerMovementSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpritePositionSystem());
        engine.addSystem(new WallCollisionSystem(tiledMap));

        //Action Systems
        engine.addSystem(new ActionableActivateSystem(playerPos));
        engine.addSystem(new ActionableDecaySystem());
        engine.addSystem(new ActionableCompleteSystem(activeActions));

        //Renderers
        engine.addSystem(new TiledRenderSystem(tiledRenderer, camera, activeActions));
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(new ShapeRenderSystem(camera));
        engine.addSystem(new ActionRenderSystem(camera));

        //Debug
        engine.addSystem(new DebugReactorSystem(powerStation, camera.viewportWidth, camera.viewportHeight));
        inputMultiplexer.addProcessor(new DebugControlSystem(powerStation));

        //StageSystem
        engine.addSystem(new Stage01System(activeActions));
    }

    private void addEntities() {
        TextureAtlas atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);
        TextureAtlas actionablesAtlas = assetManager.get("textures/actionables.atlas", TextureAtlas.class);

        TextureAtlas.AtlasRegion dude = atlas.findRegion("dude");

        playerPos = new PositionComponent(340,300);
        engine.addEntity((new Entity())
                .add(playerPos)
                .add(new VelocityComponent(0,0))
                .add(new SpriteComponent(new Sprite(dude)))
                .add(new PlayerComponent())
                .add(new CameraTargetComponent())
                .add(new ShapeComponent(Shapes.CIRCLE, Color.RED, 32))
                .add(new WallCollisionComponent())
        );

        //Add Actionables

        TextureAtlas.AtlasRegion desk = actionablesAtlas.findRegion("desk");

        engine.addEntity((new Entity())
                .add(new PositionComponent(340,330))
                .add(new SpriteComponent(new Sprite(desk)))
        );

        TextureAtlas.AtlasRegion pump = actionablesAtlas.findRegion("pump");

        engine.addEntity((new Entity())
                .add(new PositionComponent(70,860))
                .add(new SpriteComponent(new Sprite(pump)))
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(48,640))
                .add(new SpriteComponent(new Sprite(pump)))
        );


        TextureAtlas.AtlasRegion pipe = actionablesAtlas.findRegion("pipe");

        engine.addEntity((new Entity())
                .add(new PositionComponent(22,860))
                .add(new SpriteComponent(new Sprite(pipe)))
        );

        TextureAtlas.AtlasRegion computer = actionablesAtlas.findRegion("computer");

        engine.addEntity((new Entity())
                .add(new PositionComponent(224,640))
                .add(new SpriteComponent(new Sprite(computer)))
        );
/*
        actionableComponent = new ActionableComponent(10f, 2.0f,32, Color.RED, Actions.CoolantLeak);
        engine.addEntity((new Entity())
                .add(new PositionComponent(150,150))
                .add(actionableComponent)
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(200,150))
                .add(new ActionableComponent(1f, 10.0f,32, Color.GREEN, Actions.CoolantPumpBreakdown))
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(300,150))
                .add(new ActionableComponent(10f, 0.5f,64, Color.BLUE, Actions.HackedComputer))
        );
*/
    }

    @Override
    public void hide() {
    }
}
