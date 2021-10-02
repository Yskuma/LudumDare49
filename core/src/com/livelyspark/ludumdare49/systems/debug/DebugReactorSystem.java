package com.livelyspark.ludumdare49.systems.debug;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.livelyspark.ludumdare49.gameobj.PowerStation;

public class DebugReactorSystem extends EntitySystem {

    private final PowerStation ps;
    private final float vpWidth;
    private final float vpHeight;

    private Stage stage;

    private Label reactorTempLabel;
    private Label reactorHeatLabel;
    private Label controlRodPosLabel;
    private Label coolantPumpSpeedLabel;
    private Label isDebugLabel;
    private Label isPausedLabel;

    public DebugReactorSystem(PowerStation powerStation, float viewportWidth, float viewportHeight) {
        this.ps = powerStation;
        this.vpWidth = viewportWidth;
        this.vpHeight = viewportHeight;
    }

    @Override
    public void addedToEngine(Engine engine) {
        stage = new Stage();
        ps.isPaused = true;
        ps.isDebug = true;
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));

        reactorHeatLabel = new Label("", uiSkin, "small", Color.WHITE);
        reactorTempLabel = new Label("", uiSkin, "small", Color.WHITE);

        controlRodPosLabel = new Label("", uiSkin, "small", Color.WHITE);
        coolantPumpSpeedLabel = new Label("", uiSkin, "small", Color.WHITE);

        isDebugLabel = new Label("", uiSkin, "small", Color.WHITE);
        isPausedLabel = new Label("", uiSkin, "small", Color.WHITE);

        Label[] labels = {reactorHeatLabel, reactorTempLabel, controlRodPosLabel, coolantPumpSpeedLabel, isDebugLabel, isPausedLabel};
        for (int i = 0; i < labels.length; i++)
        {
            labels[i].setPosition(stage.getWidth() - 200f, stage.getHeight() - (i * 12) - 12);
            stage.addActor(labels[i]);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        if(ps.isDebug) {
            reactorHeatLabel.setText(       "ReactorHeat:      " + ps.reactorHeat);
            reactorTempLabel.setText(       "ReactorTemp:      " + ps.reactorTemp);

            controlRodPosLabel.setText(     "ControlRodPos:    " + ps.controlRodPosition);
            coolantPumpSpeedLabel.setText(  "CoolantPumpSpeed: " + ps.coolantPumpSpeed);

            isPausedLabel.setText(          "IsPaused:         " + (ps.isPaused ? "true" : "false"));
            isDebugLabel.setText(           "IsDebug:          " + (ps.isDebug ? "true" : "false"));

            stage.act();
            stage.draw();
        }
    }

}
