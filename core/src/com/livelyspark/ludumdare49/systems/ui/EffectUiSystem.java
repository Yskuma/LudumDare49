package com.livelyspark.ludumdare49.systems.ui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.managers.FormatManager;

public class EffectUiSystem extends EntitySystem {

    private final PowerStation ps;
    private final ScreenState state;
    private final AssetManager assetManager;

    private Stage stage;

    private Table table;


    private final Color DARK_GREEN = new Color(0.0f, 0.7f, 0.0f, 1.0f);
    private Table coolantLeakAlert;
    private TextureAtlas atlas;
    private Table waterPumpAlert;
    private Table coolantPumpAlert;
    private Table heatExchangerAlert;
    private Table hackedComputerAlert;
    private Table osReinstallAlert;
    private Table rubbleAlert;
    private Table[] tables;
    private Table coolantLevelAlert;
    private Table reactorTempAlert;
    private Table powerProductionAlert;


    public EffectUiSystem(ScreenState state, PowerStation powerStation, AssetManager assetManager) {
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
        atlas = assetManager.get("textures/sprites.atlas", TextureAtlas.class);

        coolantLeakAlert = getTable(atlas.findRegion("dude"), "Coolant Leak", uiSkin);
        waterPumpAlert = getTable(atlas.findRegion("dude"), "Water Pump", uiSkin);
        coolantPumpAlert = getTable(atlas.findRegion("dude"), "Coolant Pump", uiSkin);
        heatExchangerAlert = getTable(atlas.findRegion("dude"), "Heat Exchanger", uiSkin);
        hackedComputerAlert = getTable(atlas.findRegion("dude"), "HackedComputer", uiSkin);
        osReinstallAlert = getTable(atlas.findRegion("dude"), "OS Reinstall", uiSkin);
        rubbleAlert = getTable(atlas.findRegion("dude"), "Rubble", uiSkin);

        coolantLevelAlert = getTable(atlas.findRegion("dude"), "Coolant Level", uiSkin);
        reactorTempAlert = getTable(atlas.findRegion("dude"), "Reactor Temp", uiSkin);
        powerProductionAlert = getTable(atlas.findRegion("dude"), "Power Production", uiSkin);

        tables = new Table[]{coolantLeakAlert, waterPumpAlert, coolantPumpAlert, heatExchangerAlert,
        hackedComputerAlert, osReinstallAlert, rubbleAlert, coolantLevelAlert, reactorTempAlert, powerProductionAlert};

        for (Table t : tables)
        {
            stage.addActor(t);
        }

    }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        coolantLeakAlert.setVisible(state.activeEffects.contains(Effects.CoolantLeak));
        waterPumpAlert.setVisible(state.activeEffects.contains(Effects.WaterPumpBreakdown));
        coolantPumpAlert.setVisible(state.activeEffects.contains(Effects.CoolantPumpBreakdown));
        heatExchangerAlert.setVisible(state.activeEffects.contains(Effects.HeatExchangerBreakdown));
        hackedComputerAlert.setVisible(state.activeEffects.contains(Effects.HackedComputer));
        osReinstallAlert.setVisible(state.activeEffects.contains(Effects.OSReinstall));
        rubbleAlert.setVisible(state.activeEffects.contains(Effects.Rubble));

        coolantLevelAlert.setVisible(ps.coolantLevel < 0.5f);
        reactorTempAlert.setVisible(ps.reactorTemp > ps.REACTOR_TEMP_HIGH);
        powerProductionAlert.setVisible(ps.power < ps.targetPower);

        float y = stage.getHeight();
        for (Table t : tables)
        {
            if(t.isVisible()) {
                y -= t.getHeight();
                t.setPosition(0, y);
            }
        }

        stage.act();
        stage.draw();
    }

    private Table getTable(TextureRegion tr, String label, Skin uiSkin)
    {
        Drawable tableBackground = uiSkin.getDrawable("textfield");

        Table t = new Table();
        //t.setDebug(true);
        t.setBackground(tableBackground);
        t.add(new Image(tr)).left().width(32);
        t.add(new Label(label, uiSkin, "small", Color.BLACK)).left().expandX();
        t.pack();

        t.setWidth(170);
        t.setHeight(40);

        return t;
    }


}
