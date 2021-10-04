package com.livelyspark.ludumdare49.systems.ui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.managers.FloatFormatter;
import com.livelyspark.ludumdare49.managers.FormatManager;

import java.text.NumberFormat;

public class ReactorUiSystem extends EntitySystem {

    private final PowerStation ps;
    private final ScreenState state;
    private final AssetManager assetManager;

    private Stage stage;

    private Table table;
    private ProgressBar reactorTempPb;
    private ProgressBar coolantLevelPb;
    private Label reactorTempText;
    private Label coolantLevelText;
    private Label reactorTempDeltaText;
    private Label coolantLevelDeltaText;

    public ReactorUiSystem(ScreenState state, PowerStation powerStation, AssetManager assetManager) {
        this.state = state;
        this.ps = powerStation;
        this.assetManager = assetManager;
    }

    @Override
    public void addedToEngine(Engine engine) {
        stage = new Stage();
        ps.isPaused = true;
        state.isDebug = true;
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");

        table = new Table(uiSkin);
        table.setBackground(tableBackground);
        //debugTable.setDebug(true);
        table.columnDefaults(0).pad(5).center();
        table.columnDefaults(1).pad(5).center();

        table.add("Reactor\r\n Temp", "small", Color.BLACK);
        table.add("Coolant\r\n Level", "small", Color.BLACK);
        table.row();

        //table.add("ReactorHeat:", "small", Color.BLACK);
        reactorTempPb = new ProgressBar(0, ps.REACTOR_TEMP_BOOM, 1, true, uiSkin);
        table.add(reactorTempPb);

        coolantLevelPb = new ProgressBar(0f, ps.coolantLevelMax, 0.01f, true, uiSkin);
        table.add(coolantLevelPb);
        table.row();

        reactorTempText = table.add("", "small", Color.BLACK).getActor();
        coolantLevelText = table.add("", "small", Color.BLACK).getActor();
        table.row();

        reactorTempDeltaText = table.add("", "small", Color.BLACK).getActor();
        coolantLevelDeltaText = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.pack();

        table.setPosition(stage.getWidth() - table.getWidth(),
                stage.getHeight() - table.getHeight());

        stage.addActor(table);
    }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        reactorTempPb.setValue(ps.reactorTemp);
        reactorTempText.setText(FormatManager.floatFormatter.getFormattedString(ps.reactorTemp) + "K");
        reactorTempDeltaText.setText(FormatManager.floatFormatter.getFormattedString(ps.reactorTempDelta) + "K\\S");

        if (ps.reactorTemp < ps.REACTOR_TEMP_LOW) {
            reactorTempPb.setColor(Color.LIGHT_GRAY);
        } else if (ps.reactorTemp >= ps.REACTOR_TEMP_LOW && ps.reactorTemp < ps.REACTOR_TEMP_OK) {
            reactorTempPb.setColor(Color.SKY);
        } else if (ps.reactorTemp >= ps.REACTOR_TEMP_OK && ps.reactorTemp < ps.REACTOR_TEMP_HIGH) {
            reactorTempPb.setColor(Color.GREEN);
        } else {
            reactorTempPb.setColor(Color.RED);
        }

        coolantLevelPb.setValue(ps.coolantLevel);
        coolantLevelText.setText(Integer.toString((int) ((ps.coolantLevel / ps.coolantLevelMax) * 100)) + "%");
        coolantLevelDeltaText.setText(FormatManager.floatFormatter.getFormattedString(ps.coolantLevelDelta) + "%\\S");

        coolantLevelPb.setColor(0, 0, ps.coolantLevel, 1.0f);

        stage.act();
        stage.draw();
    }

    private Drawable backgroundDrawable() {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

}
