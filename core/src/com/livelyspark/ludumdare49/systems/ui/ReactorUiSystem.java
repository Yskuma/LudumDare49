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
import com.badlogic.gdx.utils.Align;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.managers.FormatManager;

public class ReactorUiSystem extends EntitySystem {

    private final PowerStation ps;
    private final ScreenState state;
    private final AssetManager assetManager;

    private Stage stage;

    private Table tableStatus;
    private ProgressBar reactorTempPb;
    private ProgressBar coolantLevelPb;
    private Label reactorTempText;
    private Label coolantLevelText;
    private Label reactorTempDeltaText;
    private Label coolantLevelDeltaText;
    private Label powerText;
    private Label powerTargetText;

    private final Color DARK_GREEN = new Color(0.0f, 0.7f, 0.0f, 1.0f);
    private Table tablePower;
    private Table tableControl;
    private Label controlRodText;
    private Label coolantPumpText;

    public ReactorUiSystem(ScreenState state, PowerStation powerStation, AssetManager assetManager) {
        this.state = state;
        this.ps = powerStation;
        this.assetManager = assetManager;
    }

    @Override
    public void addedToEngine(Engine engine) {
        stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");

        tableStatus = new Table(uiSkin);
        tableStatus.setBackground(tableBackground);
        //debugTable.setDebug(true);
        tableStatus.columnDefaults(0).pad(5).padLeft(15).padRight(15).center();
        tableStatus.columnDefaults(1).pad(5).padLeft(15).padRight(15).center();

        tableStatus.add("Reactor\r\n Temp", "small", Color.BLACK);
        tableStatus.add("Coolant\r\n Level", "small", Color.BLACK);
        tableStatus.row();

        //table.add("ReactorHeat:", "small", Color.BLACK);
        reactorTempPb = new ProgressBar(0, ps.REACTOR_TEMP_BOOM, 1, true, uiSkin);
        tableStatus.add(reactorTempPb);

        coolantLevelPb = new ProgressBar(0f, ps.coolantLevelMax, 0.01f, true, uiSkin);
        tableStatus.add(coolantLevelPb);
        tableStatus.row();

        reactorTempText = tableStatus.add("", "small", Color.BLACK).getActor();
        coolantLevelText = tableStatus.add("", "small", Color.BLACK).getActor();
        tableStatus.row();

        reactorTempDeltaText = tableStatus.add("", "small", Color.WHITE).getActor();
        coolantLevelDeltaText = tableStatus.add("", "small", Color.WHITE).getActor();
        tableStatus.pack();

        tableStatus.setPosition(stage.getWidth() - tableStatus.getWidth(),
                stage.getHeight() - tableStatus.getHeight());

        stage.addActor(tableStatus);

        tablePower = new Table(uiSkin);
        tablePower.setBackground(tableBackground);
        //tablePower.setDebug(true);
        tablePower.columnDefaults(0).pad(5).expandX().right();
        tablePower.columnDefaults(1).pad(5).width(50f).right();

        tablePower.add("Power:", "small", Color.BLACK).getActor();
        powerText = tablePower.add("", "small", Color.BLACK).getActor();
        powerText.setAlignment(Align.right);
        tablePower.row();

        tablePower.add("Target:", "small", Color.BLACK).getActor();
        powerTargetText = tablePower.add("", "small", Color.WHITE).getActor();
        powerTargetText.setAlignment(Align.right);

        tablePower.pack();
        tablePower.setWidth(tableStatus.getWidth());

        tablePower.setPosition(stage.getWidth() - tablePower.getWidth(),
                stage.getHeight() - tablePower.getHeight() - tableStatus.getHeight());

        stage.addActor(tablePower);

        tableControl = new Table(uiSkin);
        tableControl.setBackground(tableBackground);
        //tableControl.setDebug(true);
        tableControl.columnDefaults(0).pad(5).expandX().right();
        tableControl.columnDefaults(1).pad(5).width(50f).right();

        tableControl.add("Control Rods:", "small", Color.BLACK).getActor();
        controlRodText = tableControl.add("", "small", Color.BLACK).getActor();
        controlRodText.setAlignment(Align.right);
        tableControl.row();

        tableControl.add("Coolant Pump:", "small", Color.BLACK).getActor();
        coolantPumpText = tableControl.add("", "small", Color.BLACK).getActor();
        coolantPumpText.setAlignment(Align.right);

        tableControl.pack();
        tableControl.setWidth(tableStatus.getWidth());

        tableControl.setPosition(stage.getWidth() - tableControl.getWidth(),
                stage.getHeight() - tableControl.getHeight() - tablePower.getHeight() - tableStatus.getHeight());

        stage.addActor(tableControl);
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
        reactorTempDeltaText.setColor(deltaColour(ps.reactorTempDelta));

        coolantLevelPb.setValue(ps.coolantLevel);
        coolantLevelText.setText(FormatManager.floatFormatter.getFormattedString(ps.coolantLevel * 100)  + "%");
        coolantLevelDeltaText.setText(FormatManager.floatFormatter.getFormattedString(ps.coolantLevelDelta) + "%\\S");

        coolantLevelPb.setColor(0, 0, ps.coolantLevel, 1.0f);
        coolantLevelDeltaText.setColor(deltaColour(ps.coolantLevelDelta));

        powerText.setText(FormatManager.floatFormatter.getFormattedString(ps.power) + "MW");
        powerTargetText.setText(FormatManager.floatFormatter.getFormattedString(ps.targetPower) + "MW");
        powerTargetText.setColor(ps.power > ps.targetPower ? DARK_GREEN : Color.RED);

        controlRodText.setText(FormatManager.floatFormatter.getFormattedString(ps.controlRodPosition * 100) + "%");
        coolantPumpText.setText(FormatManager.floatFormatter.getFormattedString(ps.coolantPumpSpeed * 100)  + "%");

        stage.act();
        stage.draw();
    }

    private Drawable backgroundDrawable() {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

    private Color deltaColour(float d)
    {
        if(d<0){return Color.RED;}
        if(d>0){return DARK_GREEN;}
        return Color.DARK_GRAY;
    }

}
