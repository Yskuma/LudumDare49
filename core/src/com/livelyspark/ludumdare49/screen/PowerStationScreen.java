package com.livelyspark.ludumdare49.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare49.components.*;
import com.livelyspark.ludumdare49.enums.CameraModes;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.enums.AnimationLabels;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.input.MessageInputProcessor;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.*;
import com.livelyspark.ludumdare49.systems.action.*;
import com.livelyspark.ludumdare49.input.DebugInputProcessor;
import com.livelyspark.ludumdare49.systems.ui.*;
import com.livelyspark.ludumdare49.systems.render.*;
import com.livelyspark.ludumdare49.systems.stages.Stage01System;

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

        camera = new OrthographicCamera(520, 296);
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        //TiledMap tiledMap = assetManager.get("tilemaps/testmapsmall.tmx", TiledMap.class);
        TiledMap tiledMap = assetManager.get("tilemaps/powerstation.tmx", TiledMap.class);
        tiledRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        addEntities();

        screenState = new ScreenState();
        powerStation = new PowerStation();

        //Camera Systems
        engine.addSystem(new RumbleSystem(screenState));
        engine.addSystem(new CameraSystem(camera, screenState));

        //Movement/Position Systems
        engine.addSystem(new PlayerMovementSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpritePositionSystem());
        engine.addSystem(new WallCollisionSystem(tiledMap));

        //Action Systems
        engine.addSystem(new ActionableActivateSystem(screenState, playerPos));
        engine.addSystem(new ActionableDecaySystem());

        engine.addSystem(new ActionableEffectCompleteSystem(screenState));
        engine.addSystem(new ActionableEffectResolveSystem(screenState));

        engine.addSystem(new ActionableCommandCompleteSystem(screenState));
        engine.addSystem(new ActionableCommandResolveSystem(screenState, powerStation));

        engine.addSystem(new ActionableTiledLayerSystem(tiledMap, screenState));

        //Message Systems
        engine.addSystem(new MessageSystem(screenState));
        inputMultiplexer.addProcessor(new MessageInputProcessor(screenState));

        //Reactor Systems
        engine.addSystem(new ReactorSystem(powerStation));
        engine.addSystem(new TurbineSpeedSystem(powerStation));
        ControlRodAnimationSystem controlRodAnimationSystem = new ControlRodAnimationSystem(assetManager, powerStation);
        engine.addSystem(controlRodAnimationSystem);
        controlRodAnimationSystem.GenerateEntities();

        //Renderers
        engine.addSystem(new TiledRenderSystem(tiledRenderer, camera));
        engine.addSystem(new AnimationRenderSystem(camera, powerStation, screenState));
        engine.addSystem(new TurbineRenderSystem(camera, powerStation));
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(new ShapeRenderSystem(camera));
        engine.addSystem(new ActionableEffectRenderSystem(camera,assetManager));
        engine.addSystem(new ActionableEffectHintRenderSystem(camera, playerPos, assetManager));
        engine.addSystem(new ActionableCommandRenderSystem(camera, assetManager));

        //UI
        engine.addSystem(new MessageUiSystem(screenState, assetManager));
        engine.addSystem(new ReactorUiSystem(screenState, powerStation, assetManager));

        //Debug
        engine.addSystem(new DebugReactorUiSystem(screenState, powerStation));
        engine.addSystem(new DebugPlayerPosUiSystem(screenState, playerPos));
        engine.addSystem(new DebugScreenStateUiSystem(screenState));
        inputMultiplexer.addProcessor(new DebugInputProcessor(screenState, powerStation));

        //StageSystem
        engine.addSystem(new Stage01System(powerStation));
    }

    private void addEntities() {
        TextureAtlas atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);
        TextureAtlas actionablesAtlas = assetManager.get("textures/actionables.atlas", TextureAtlas.class);

        TextureAtlas.AtlasRegion dude = atlas.findRegion("dude");

        playerPos = new PositionComponent(340, 300);
        camera.position.x = playerPos.x;
        camera.position.y = playerPos.y;

        engine.addEntity((new Entity())
                .add(playerPos)
                .add(new VelocityComponent(0, 0))
                .add(new SpriteComponent(new Sprite(dude)))
                .add(new PlayerComponent())
                .add(new CameraTargetComponent())
                .add(new WallCollisionComponent())
        );

        //Add Actionables
        TextureAtlas.AtlasRegion[] turbine = new TextureAtlas.AtlasRegion[]{
                actionablesAtlas.findRegion("Turbine1"),
                actionablesAtlas.findRegion("Turbine2"),
                actionablesAtlas.findRegion("Turbine3"),
                actionablesAtlas.findRegion("Turbine4"),
                actionablesAtlas.findRegion("Turbine5")
        };

        engine.addEntity((new Entity())
                .add(new PositionComponent(64,208))
                .add(new AnimationComponent(new Animation<TextureRegion>(1.0f, turbine), 1.0f, AnimationLabels.Turbine))
                .add(new TurbineComponent())
        );

        TextureAtlas.AtlasRegion [] handPump = new TextureAtlas.AtlasRegion[]{
                actionablesAtlas.findRegion("HandPump1"),
                actionablesAtlas.findRegion("HandPump2"),
                actionablesAtlas.findRegion("HandPump3"),
                actionablesAtlas.findRegion("HandPump4"),
                actionablesAtlas.findRegion("HandPump5"),
                actionablesAtlas.findRegion("HandPump6"),
                actionablesAtlas.findRegion("HandPump7")
        };

        engine.addEntity((new Entity())
                .add(new PositionComponent(272,850))
                .add(new AnimationComponent(new Animation<TextureRegion>(0.2f, handPump), 0.2f, AnimationLabels.HandPump))
        );

        TextureAtlas.AtlasRegion [] reactor = new TextureAtlas.AtlasRegion[]{
                actionablesAtlas.findRegion("Reactor1"),
                actionablesAtlas.findRegion("Reactor2"),
                actionablesAtlas.findRegion("Reactor3")
        };

        engine.addEntity((new Entity())
                .add(new PositionComponent(192,808))
                .add(new AnimationComponent(new Animation<TextureRegion>(1.0f, reactor), 1.0f, AnimationLabels.Reactor))
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(152,632))
                .add(new ActionableComponent(1.0f, 5.0f, 16f, Color.PURPLE))
                .add(new CommandComponent(Commands.ControlRodDecrease))
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(296,632))
                .add(new ActionableComponent(1.0f, 5.0f, 16f, Color.PURPLE))
                .add(new CommandComponent(Commands.ControlRodIncrease))
        );

        engine.addEntity((new Entity())
                .add(new PositionComponent(288,858))
                .add(new ActionableComponent(1.0f, 5.0f, 32f, Color.PURPLE))
                .add(new CommandComponent(Commands.CoolantLevelIncrease))
        );

        /*
        engine.addEntity(new Entity()
                .add(new MessageComponent("Our country is strong and stable, no earthquakes here!!",  atlas.findRegion("kimmy32"))));

        engine.addEntity(new Entity()
                .add(new MessageComponent("You must build additional pylons!",  atlas.findRegion("kimmy32"))));
         */

        engine.addEntity(new Entity().add(new RumbleComponent(CameraModes.ShakeLarge, 3.0f)));


    }

    @Override
    public void hide() {
    }
}
