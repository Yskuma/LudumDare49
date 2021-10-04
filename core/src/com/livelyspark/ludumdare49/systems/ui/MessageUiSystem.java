package com.livelyspark.ludumdare49.systems.ui;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.livelyspark.ludumdare49.components.MessageComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.enums.MessageTextures;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class MessageUiSystem extends EntitySystem {
    private final ScreenState screenState;
    private final AssetManager assetManager;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<MessageComponent> mm = ComponentMapper.getFor(MessageComponent.class);
    private Stage stage;
    private Label textLabel;
    private Image icon;
    private TextureAtlas atlas;
    private TextureAtlas actionablesAtlas;

    public MessageUiSystem(ScreenState screenState, AssetManager assetManager) {
        this.screenState = screenState;
        this.assetManager = assetManager;
        atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);
        actionablesAtlas = assetManager.get("textures/actionables.atlas", TextureAtlas.class);
    }

    @Override
    public void addedToEngine (Engine engine) {
        stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");

        float spaceSide = 20;
        float spaceBottom = 5;

        Table table = new Table(uiSkin);
        //table.setDebug(true);
        table.setWidth(stage.getWidth() - (spaceSide * 2));
        table.setHeight(50);
        table.setX(spaceSide);
        table.setY(spaceBottom);
        table.background(tableBackground);

        table.columnDefaults(0).width(32);
        table.columnDefaults(1).pad(5).left().expand();

        icon = new Image();
        icon.setWidth(32);
        icon.setHeight(32);
        table.add(icon);
        textLabel = table.add("This is player test!", "small", Color.BLACK).getActor();

        stage.addActor(table);
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(screenState.activeMessage != null) {
            textLabel.setText(screenState.activeMessage.message);
            icon.setDrawable(new TextureRegionDrawable(GetTextureRegion(screenState.activeMessage.icon)));
            stage.act();
            stage.draw();
        }
    }

    private TextureRegion GetTextureRegion(MessageTextures icon) {
        switch (icon){
            case Leader:
                return atlas.findRegion("kimmy32");
            case Note:
                return actionablesAtlas.findRegion("DeskWithPaper");
            case Player:
                return atlas.findRegion("dude");
        }

        return atlas.findRegion("kimmy32");
    }


}