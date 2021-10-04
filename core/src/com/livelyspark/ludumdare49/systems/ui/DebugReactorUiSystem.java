package com.livelyspark.ludumdare49.systems.ui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
    private Label leakActiveLabel;
    private Label isDebugLabel;
    private Label isPausedLabel;

    private Table table;
    private Label coolantLevelLabel;
    private Label deltaFuelAtomsLabel;
    private Label heatPerFissionLabel;
    private Label deltaArtificialNeutronsLabel;
    private Label deltaSlowNeutronsLabel;
    private Label powerLabel;
    private Label deltaTimeLabel;

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

        table = new Table(uiSkin);
        table.setBackground(tableBackground);
        //debugTable.setDebug(true);
        table.columnDefaults(0).pad(5).right();
        table.columnDefaults(1).pad(5).width(100);

        table.add("ReactorHeat:", "small", Color.BLACK);
        reactorHeatLabel = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("ReactorTemp:", "small", Color.BLACK);
        reactorTempLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("ControlRodPos:", "small", Color.BLACK);
        controlRodPosLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("CoolantPumpSpeed:", "small", Color.BLACK);
        coolantPumpSpeedLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("LeakActive:", "small", Color.BLACK);
        leakActiveLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add().colspan(2);
        table.row();

        table.add("CoolantLevel:", "small", Color.BLACK);
        coolantLevelLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("DeltaFuelAtoms:", "small", Color.BLACK);
        deltaFuelAtomsLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("HeatPerFission:", "small", Color.BLACK);
        heatPerFissionLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("DeltaArtificialNeutrons:", "small", Color.BLACK);
        deltaArtificialNeutronsLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("DeltaSlowNeutrons:", "small", Color.BLACK);
        deltaSlowNeutronsLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add().colspan(2);
        table.row();

        table.add("Power:", "small", Color.BLACK);
        powerLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add().colspan(2);
        table.row();

        table.add("IsDebug:", "small", Color.BLACK);
        isDebugLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add("IsPaused:", "small", Color.BLACK);
        isPausedLabel  = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.add().colspan(2);
        table.row();

        table.add("[ ] - Move Control Rods", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        table.row();

        table.add(", . - Change Pump Speed", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        table.row();

        table.add("K L - Change Coolant Level", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        table.row();

        table.add("; - Enable/Disable Leak", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        table.row();

        table.add("O - Enable/Disable Debug", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        table.row();

        table.add("P - Pause Reactor Sim", "small", Color.BLACK)
                .colspan(2).align(Align.center);
        table.row();

        table.add("deltaTime:", "small", Color.BLACK);
        deltaTimeLabel  = table.add("", "small", Color.BLACK).getActor();
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
        if(state.isDebug) {
            reactorHeatLabel.setText(Float.toString(ps.reactorHeat));
            reactorTempLabel.setText(Float.toString(ps.reactorTemp));

            controlRodPosLabel.setText(Float.toString(ps.controlRodPosition));
            coolantPumpSpeedLabel.setText(Float.toString(ps.coolantPumpSpeed));
            leakActiveLabel.setText((ps.coolantLeakActive ? "true" : "false"));

            coolantLevelLabel.setText(Float.toString(ps.coolantLevel));
            deltaFuelAtomsLabel.setText(Float.toString(ps.deltaFuelAtoms));
            heatPerFissionLabel.setText(Float.toString(ps.heatPerFission));
            deltaArtificialNeutronsLabel.setText(Float.toString(ps.deltaArtificialNeutrons));
            deltaSlowNeutronsLabel.setText(Float.toString(ps.deltaSlowNeutrons));

            powerLabel.setText(Float.toString(ps.power));

            isPausedLabel.setText((ps.isPaused ? "true" : "false"));
            isDebugLabel.setText((state.isDebug ? "true" : "false"));

            deltaTimeLabel.setText(Float.toString(deltaTime));

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
