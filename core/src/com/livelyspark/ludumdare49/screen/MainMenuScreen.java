package com.livelyspark.ludumdare49.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.livelyspark.ludumdare49.components.EdgeBounceComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.EdgeBounceSystem;
import com.livelyspark.ludumdare49.systems.MovementSystem;
import com.livelyspark.ludumdare49.systems.SpritePositionSystem;
import com.livelyspark.ludumdare49.systems.SpriteRenderSystem;

import java.util.Random;

public class MainMenuScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;
    private Label titleLabel;
    private EdgeBounceSystem ebs;

    public MainMenuScreen(IScreenManager screenManager, AssetManager assetManager) {
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
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2, height / 2, 0);
        camera.update();

        titleLabel.setX((stage.getWidth() - titleLabel.getWidth()) / 2);
        titleLabel.setY((stage.getHeight() - titleLabel.getHeight()) / 2);

        ebs.setBounds(new Rectangle(0, 0, width, height));
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(200, 400);
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        titleLabel = new Label("Main Menu", uiSkin, "bigTitleBitmap", Color.WHITE);
        stage.addActor(titleLabel);

        addEntities();

        ebs = new EdgeBounceSystem(new Rectangle(0, 0, 200, 200));

        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpritePositionSystem());
        engine.addSystem(new SpriteRenderSystem(camera));
        engine.addSystem(ebs);

    }

    private void addEntities() {
        TextureAtlas atlas = assetManager.get("textures/dummy.atlas", TextureAtlas.class);

        TextureAtlas.AtlasRegion redBall = atlas.findRegion("RedBall");
        TextureAtlas.AtlasRegion blueBall = atlas.findRegion("BlueBall");
        TextureAtlas.AtlasRegion greenBall = atlas.findRegion("GreenBall");
        TextureAtlas.AtlasRegion yellowBall = atlas.findRegion("YellowBall");

        engine.addEntity((new Entity())
                .add(randomPositionComp())
                .add(randomVelocityComp())
                .add(new SpriteComponent(new Sprite(redBall)))
                .add(new EdgeBounceComponent()));

        engine.addEntity((new Entity())
                .add(randomPositionComp())
                .add(randomVelocityComp())
                .add(new SpriteComponent(new Sprite(blueBall)))
                .add(new EdgeBounceComponent()));

        engine.addEntity((new Entity())
                .add(randomPositionComp())
                .add(randomVelocityComp())
                .add(new SpriteComponent(new Sprite(greenBall)))
                .add(new EdgeBounceComponent()));

        engine.addEntity((new Entity())
                .add(randomPositionComp())
                .add(randomVelocityComp())
                .add(new SpriteComponent(new Sprite(yellowBall)))
                .add(new EdgeBounceComponent()));
    }

    private VelocityComponent randomVelocityComp() {
        Random r = new Random();

        float speed = 100;

        float x = MathUtils.cos(r.nextFloat() * MathUtils.PI * 2) * speed;
        float y = MathUtils.sin(r.nextFloat() * MathUtils.PI * 2) * speed;

        return new VelocityComponent(x, y);
    }

    private PositionComponent randomPositionComp() {
        Random r = new Random();

        float x = (r.nextFloat() * 400);
        float y = (r.nextFloat() * 400);

        return new PositionComponent(x, y);
    }

    @Override
    public void hide() {
    }
}
