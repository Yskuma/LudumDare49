package com.livelyspark.ludumdare49.systems.ui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class DebugReactorUiSystem extends EntitySystem {

    private final PowerStation ps;
    private final ScreenState state;

    private Stage stage;

    private Label reactorTempLabel;
    private Label reactorHeatLabel;
    private Label controlRodPosLabel;
    private Label coolantPumpSpeedLabel;
    private Label isDebugLabel;
    private Label isPausedLabel;

    private Table debugTable;
    private Label deltaFuelAtomsLabel;
    private Label deltaArtificialNeutronsLabel;
    private Label deltaSlowNeutronsLabel;
    private Label powerLabel;

    public DebugReactorUiSystem(ScreenState state, PowerStation powerStation) {
        this.state = state;
        this.ps = powerStation;
    }

    @Override
    public void addedToEngine(Engine engine) {
        stage = new Stage();
        ps.isPaused = true;
        state.isDebug = true;
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");

        debugTable = new Table(uiSkin);
        debugTable.setBackground(tableBackground);
        //debugTable.setDebug(true);
        debugTable.columnDefaults(0).pad(5).right();
        debugTable.columnDefaults(1).pad(5).width(100);

        debugTable.add("ReactorHeat:", "small", Color.BLACK);
        reactorHeatLabel = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add("ReactorTemp:", "small", Color.BLACK);
        reactorTempLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add("ControlRodPos:", "small", Color.BLACK);
        controlRodPosLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add("CoolantPumpSpeed:", "small", Color.BLACK);
        coolantPumpSpeedLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add().colspan(2);
        debugTable.row();

        debugTable.add("DeltaFuelAtoms:", "small", Color.BLACK);
        deltaFuelAtomsLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add("DeltaArtificialNeutrons:", "small", Color.BLACK);
        deltaArtificialNeutronsLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add("DeltaSlowNeutrons:", "small", Color.BLACK);
        deltaSlowNeutronsLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add().colspan(2);
        debugTable.row();

        debugTable.add("Power:", "small", Color.BLACK);
        powerLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add().colspan(2);
        debugTable.row();

        debugTable.add("IsDebug:", "small", Color.BLACK);
        isDebugLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add("IsPaused:", "small", Color.BLACK);
        isPausedLabel  = debugTable.add("", "small", Color.BLACK).getActor();
        debugTable.row();

        debugTable.add().colspan(2);
        debugTable.row();

        debugTable.add("[ ] - Move Control Rods", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        debugTable.row();

        debugTable.add(", . - Change Pump Speed", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        debugTable.row();

        debugTable.add("P - Pause Reactor Sim", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        debugTable.row();

        debugTable.add("O - Enable/Disable Debug", "small", Color.BLACK)
                .colspan(2).align(Align.center);
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
        if(state.isDebug) {
            reactorHeatLabel.setText(Float.toString(ps.reactorHeat));
            reactorTempLabel.setText(Float.toString(ps.reactorTemp));

            controlRodPosLabel.setText(Float.toString(ps.controlRodPosition));
            coolantPumpSpeedLabel.setText(Float.toString(ps.coolantPumpSpeed));

            deltaFuelAtomsLabel.setText(Float.toString(ps.deltaFuelAtoms));
            deltaArtificialNeutronsLabel.setText(Float.toString(ps.deltaArtificialNeutrons));
            deltaSlowNeutronsLabel.setText(Float.toString(ps.deltaSlowNeutrons));

            powerLabel.setText(Float.toString(ps.power));

            isPausedLabel.setText((ps.isPaused ? "true" : "false"));
            isDebugLabel.setText((state.isDebug ? "true" : "false"));

            stage.act();
            stage.draw();
        }
    }

    private Drawable backgroundDrawable()
    {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

}
