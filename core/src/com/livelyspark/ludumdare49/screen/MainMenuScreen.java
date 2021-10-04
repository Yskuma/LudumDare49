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
import com.livelyspark.ludumdare49.enums.Screens;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.systems.EdgeBounceSystem;
import com.livelyspark.ludumdare49.systems.MovementSystem;
import com.livelyspark.ludumdare49.systems.SpritePositionSystem;
import com.livelyspark.ludumdare49.systems.render.SpriteRenderSystem;

import java.util.Random;

public class MainMenuScreen extends AbstractScreen {

    private Engine engine;
    private OrthographicCamera camera;
    private Stage stage;
    private Label titleLabel;
    private Label clickContinueLabel;
    private Sprite sprite;
    private PositionComponent spritePos;
    private Label moveLabel;
    private Label useLabel;

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

        if (Gdx.input.isTouched()) { // If the screen is touched after the game is done loading, go to the main menu screen
            screenManager.switchScreen(Screens.PowerStation);
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.x = width/2;
        camera.position.y = height/2;
        camera.update();

        float midX = stage.getWidth() - (titleLabel.getWidth() / 2) - 30;

        titleLabel.setX(midX - (titleLabel.getWidth() / 2));
        titleLabel.setY((stage.getHeight() - titleLabel.getHeight()  - 300));

        spritePos.x = width/2;
        spritePos.y = height / 2;



        moveLabel.setX(midX - (moveLabel.getWidth() / 2));
        moveLabel.setY((stage.getHeight() - titleLabel.getHeight()  - 340));

        useLabel.setX(midX - (useLabel.getWidth() / 2));
        useLabel.setY((stage.getHeight() - titleLabel.getHeight()  - 360));

        clickContinueLabel.setX(midX - (clickContinueLabel.getWidth() / 2));
        clickContinueLabel.setY((stage.getHeight() - titleLabel.getHeight()  - 400));
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(1040, 592);

        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        stage = new Stage();

        titleLabel = new Label("Everything's Unstable", uiSkin, "title", Color.WHITE);
        clickContinueLabel = new Label("Click To Continue", uiSkin, "medium", Color.WHITE);

        moveLabel = new Label("W,A,S,D to Move", uiSkin, "medium", Color.WHITE);
        useLabel = new Label("Hold Space to Use", uiSkin, "medium", Color.WHITE);

        stage.addActor(titleLabel);
        stage.addActor(clickContinueLabel);
        stage.addActor(moveLabel);
        stage.addActor(useLabel);

        addEntities();

        engine.addSystem(new SpritePositionSystem());
        engine.addSystem(new SpriteRenderSystem(camera));
    }

    private void addEntities() {
        Texture nuclear = assetManager.get("textures/nuclear.png", Texture.class);
        sprite = new Sprite(nuclear);
        sprite.scale(1f);

        spritePos = new PositionComponent();

        engine.addEntity((new Entity())
                .add(new SpriteComponent(sprite))
                .add(spritePos)
        );

    }

    @Override
    public void hide() {
    }
}
