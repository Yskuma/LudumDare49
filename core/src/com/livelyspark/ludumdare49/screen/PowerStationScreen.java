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
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare49.components.*;
import com.livelyspark.ludumdare49.enums.Shapes;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.input.MessageInputProcessor;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.*;
import com.livelyspark.ludumdare49.systems.action.*;
import com.livelyspark.ludumdare49.input.DebugInputProcessor;
import com.livelyspark.ludumdare49.systems.ui.DebugReactorUiSystem;
import com.livelyspark.ludumdare49.systems.render.*;
import com.livelyspark.ludumdare49.systems.stages.Stage01System;
import com.livelyspark.ludumdare49.systems.ui.MessageUiSystem;

public class PowerStationScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;
    private OrthogonalTiledMapRenderer tiledRenderer;
    private PositionComponent playerPos;
    private ActionableComponent actionableComponent;
    private PowerStation powerStation;
    private InputMultiplexer inputMultiplexer;
    private ScreenState screenState;

    public PowerStationScreen(IScreenManager screenManager, AssetManager assetManager) {
        super(screenManager, assetManager);
        engine = new Engine();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);

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

        camera = new OrthographicCamera(520,296);
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        //TiledMap tiledMap = assetManager.get("tilemaps/testmapsmall.tmx", TiledMap.class);
        TiledMap tiledMap = assetManager.get("tilemaps/powerstation.tmx", TiledMap.class);
        tiledRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        addEntities();

        screenState = new ScreenState();
        powerStation = new PowerStation();

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
        engine.addSystem(new ActionableCompleteSystem(screenState));
        engine.addSystem(new ActionableResolveSystem(screenState));
        engine.addSystem(new ActionableTiledLayerSystem(tiledMap, screenState));

        //Message Systems
        engine.addSystem(new MessageSystem(screenState));
        inputMultiplexer.addProcessor(new MessageInputProcessor(screenState));

        //Reactor Systems
        engine.addSystem(new ReactorSystem(powerStation));

        //Renderers
        engine.addSystem(new TiledRenderSystem(tiledRenderer, camera));
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(new ShapeRenderSystem(camera));
        engine.addSystem(new ActionRenderSystem(camera,assetManager));
        engine.addSystem(new ActionHintRenderSystem(camera, playerPos, assetManager));

        //UI
        engine.addSystem(new MessageUiSystem(screenState, assetManager));

        //Debug
        engine.addSystem(new DebugReactorUiSystem(powerStation));
        inputMultiplexer.addProcessor(new DebugInputProcessor(powerStation));

        //StageSystem
        engine.addSystem(new Stage01System(screenState));
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

        engine.addEntity(new Entity()
                .add(new MessageComponent("Our country is strong and stable, no earthquakes here!!",  atlas.findRegion("kimmy32"))));

        engine.addEntity(new Entity()
                .add(new MessageComponent("You must build additional pylons!",  atlas.findRegion("kimmy32"))));

    }

    @Override
    public void hide() {
    }
}
