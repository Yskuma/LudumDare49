package com.livelyspark.ludumdare49.systems.debug;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
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

    private Table debugTable;

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

        debugTable = new Table(uiSkin);
        debugTable.setDebug(true);
        debugTable.columnDefaults(0).pad(5).right();
        debugTable.columnDefaults(1).pad(5).width(50);

        debugTable.add("ReactorHeat:", "small", Color.WHITE);
        reactorHeatLabel = debugTable.add("", "small", Color.WHITE).getActor();
        debugTable.row();

        debugTable.add("ReactorTemp:", "small", Color.WHITE);
        reactorTempLabel  = debugTable.add("", "small", Color.WHITE).getActor();
        debugTable.row();

        debugTable.add("ControlRodPos:", "small", Color.WHITE);
        controlRodPosLabel  = debugTable.add("", "small", Color.WHITE).getActor();
        debugTable.row();

        debugTable.add("CoolantPumpSpeed:", "small", Color.WHITE);
        coolantPumpSpeedLabel  = debugTable.add("", "small", Color.WHITE).getActor();
        debugTable.row();

        debugTable.add("IsDebug:", "small", Color.WHITE);
        isDebugLabel  = debugTable.add("", "small", Color.WHITE).getActor();
        debugTable.row();

        debugTable.add("IsPaused:", "small", Color.WHITE);
        isPausedLabel  = debugTable.add("", "small", Color.WHITE).getActor();
        debugTable.row();

        debugTable.pack();

        debugTable.setPosition(stage.getWidth() - debugTable.getWidth(),
                stage.getHeight() - debugTable.getHeight());

        stage.addActor(debugTable);
   }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        if(ps.isDebug) {
            reactorHeatLabel.setText(Float.toString(ps.reactorHeat));
            reactorTempLabel.setText(Float.toString(ps.reactorTemp));

            controlRodPosLabel.setText(Float.toString(ps.controlRodPosition));
            coolantPumpSpeedLabel.setText(Float.toString(ps.coolantPumpSpeed));

            isPausedLabel.setText((ps.isPaused ? "true" : "false"));
            isDebugLabel.setText((ps.isDebug ? "true" : "false"));

            stage.act();
            stage.draw();
        }
    }

}
