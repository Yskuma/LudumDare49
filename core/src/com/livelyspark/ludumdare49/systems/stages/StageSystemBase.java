package com.livelyspark.ludumdare49.systems.stages;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare49.components.*;
import com.livelyspark.ludumdare49.enums.*;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.stages.Stage;

public abstract class StageSystemBase extends IntervalSystem {

    protected Stage thisStage;
    protected PowerStation powerStation;
    private IScreenManager screenManager;
    private ScreenState screenState;
    private AssetManager assetManager;

    private int lowPowerTime;

    public StageSystemBase(PowerStation powerStation, IScreenManager screenManager, ScreenState screenState, AssetManager assetManager) {
        super(1.0f);
        GenerateStage();
        this.powerStation = powerStation;
        powerStation.targetPower = 100;
        this.screenManager = screenManager;
        this.screenState = screenState;
        this.assetManager = assetManager;
    }

    protected abstract void GenerateStage();

    @Override
    protected void updateInterval() {
        UpdateProgress();
        TriggerEvents();
        if(thisStage.currentPower >= thisStage.powerTarget){
            DoStageWin();
        }

        if (powerStation.power < powerStation.targetPower){
            lowPowerTime++;
            screenState.timoutCountdown = 30 - lowPowerTime;
        }
        else{
            screenState.timoutCountdown = 30;
            lowPowerTime = 0;
        }
        CheckGameOver();
    }

    protected void UpdateProgress(){
        thisStage.currentPower += powerStation.power;
        thisStage.completionPercent = 100 * (thisStage.currentPower / thisStage.powerTarget);
    }

    private void TriggerEvents(){
        for (int i = 0; i < thisStage.events.length; i++) {
            Event event = thisStage.events[i];
            if(!event.triggered
                    && thisStage.completionPercent >= event.triggerPercent){

                event.triggered = true;

                if(event.message != ""){
                    this.getEngine().addEntity(new Entity()
                            .add(new MessageComponent(event.message, event.texture))
                            .add(new SoundComponent(assetManager.get("sound/blip.mp3", Sound.class),false,0.5f)));
                }

                switch (event.events){
                    case StartNote:
                        DoStartNote();
                        break;
                    case EarthQuake:
                        DoRubble();
                        DoCoolantLeak();
                        break;
                    case CoolantLeak:
                        DoCoolantLeak();
                        break;
                    case OppositionHacker:
                        DoOppositionHacker();
                        break;
                    case BreakdownCoolantPump:
                        DoCoolantPumpBreakdown();
                        break;
                    case BreakdownWaterPump:
                        DoWaterPumpBreakdown();
                        break;
                    case BreakdownHeatExchange:
                        DoHeatExchangerBreakdown();
                        break;
                    case Bombers:
                        DoRubble();
                        DoCoolantPumpBreakdown();
                        DoHeatExchangerBreakdown();
                        DoCoolantLeak();
                        break;
                    case MoarPowah:
                        powerStation.targetPower = 110.0f;
                        break;
                    case EvenMoarPowah:
                        powerStation.targetPower = 120.0f;
                        break;
                    case SheCantTakeMuchMoreCaptain:
                        DoCoolantPumpBreakdown();
                        DoWaterPumpBreakdown();
                        DoCoolantLeak();
                        break;
                }
            }
        }
    }

    private void DoStartNote(){
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(352,320))
                .add(new ActionableComponent(1f, 2.0f,32, Color.BLUE, "Read\nNote"))
                .add(new EffectComponent(Effects.ReadNote))
        );
    }

    private void DoRubble() {
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(336,512))
                .add(new ActionableComponent(1f, 2.0f,32, Color.YELLOW))
                .add(new EffectComponent(Effects.Rubble))
                .add(new RumbleComponent(CameraModes.ShakeLarge, 3.0f))
        );
        this.getEngine().addEntity((new Entity())
                .add(new SoundComponent(
                        assetManager.get("sound/earthquake.mp3", Sound.class),
                        false,
                        0.5f)));
    }

    private void DoCoolantLeak() {
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(37,859))
                .add(new ActionableComponent(1f, 2.0f,25, Color.RED))
                .add(new EffectComponent(Effects.CoolantLeak))
        );
    }

    private void DoOppositionHacker() {
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(224,638))
                .add(new ActionableComponent(10.0f, 2.0f,32, Color.RED))
                .add(new EffectComponent(Effects.HackedComputer))
        );

        screenState.disabledCommands.add(Commands.ControlRodDecrease);
        screenState.disabledCommands.add(Commands.ControlRodIncrease);
    }

    private void DoCoolantPumpBreakdown(){
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(80,864))
                .add(new ActionableComponent(3f, 2.0f,64, Color.RED))
                .add(new EffectComponent(Effects.CoolantPumpBreakdown))
        );
    }

    private void DoWaterPumpBreakdown() {
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(48,640))
                .add(new ActionableComponent(3f, 2.0f,64, Color.RED))
                .add(new EffectComponent(Effects.WaterPumpBreakdown))
        );
    }

    private void DoHeatExchangerBreakdown() {
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(48, 560))
                .add(new ActionableComponent(4f, 1.0f,64, Color.RED))
                .add(new EffectComponent(Effects.HeatExchangerBreakdown))
        );
    }

    private void CheckGameOver(){
        if(lowPowerTime > 30){
            DoGameOver(LoseConditions.LowPower);
        }
        else if(powerStation.reactorTemp > powerStation.REACTOR_TEMP_BOOM){
            DoGameOver(LoseConditions.Meltdown);
        }
        else if(powerStation.coolantLevel <= 0){
            DoGameOver(LoseConditions.NoCoolant);
        }
    };

    private void DoGameOver(LoseConditions loseCondition){
        switch (loseCondition){

            case LowPower:
                screenManager.setGameOverMessage("You didn't meet your target." +
                        "\nOur Glorious Leader is displeased.");
                break;
            case Meltdown:
                screenManager.setGameOverMessage("Meltdown!" +
                        "\nThe reactor got too hot!");
                break;
            case NoCoolant:
                screenManager.setGameOverMessage("Meltdown!" +
                        "\nThe reactor ran out of coolant.");
                break;
        }

        screenManager.switchScreen(Screens.GameOver);
    };

    private void DoStageWin(){
        screenManager.switchScreen(Screens.YouWin);
    };
}
