package com.livelyspark.ludumdare49.systems.stages;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.stages.Stage;

public abstract class StageSystemBase extends IntervalSystem {

    public double currentPowerGeneration;

    protected Stage thisStage;
    private ActionableComponent actionableComponent;

    public StageSystemBase(ScreenState screenState) {
        super( 1.0f);
        GenerateStage();
    }

    protected abstract void GenerateStage();

    @Override
    protected void updateInterval() {
        UpdateProgress();
        TriggerEvents();
    }

    protected void UpdateProgress(){
        thisStage.currentPower += currentPowerGeneration;
        thisStage.completionPercent = 100 * (thisStage.currentPower / thisStage.powerTarget);
    }

    private void TriggerEvents(){
        for (int i = 0; i < thisStage.events.length; i++) {
            Event event = thisStage.events[i];
            if(!event.triggered
                    && thisStage.completionPercent >= event.triggerPercent){

                event.triggered = true;

                switch (event.events){
                    case StartNote:
                        DoStartNote();
                        break;
                    case EarthQuake:
                        DoRubble();
                        break;
                    case CoolantLeak:
                        DoCoolantLeak();
                        break;
                    case GloriousLeader:
                        break;
                    case OppositionHacker:
                        DoOppositionHacker();
                        break;
                    case RebelInvasion:
                        break;
                    case PartBreakdownOne:
                        DoPartBreakdownOne();
                        break;
                    case PartBreakdownTwo:
                        DoPartBreakdownTwo();
                        break;
                    case PartBreakdownThree:
                        break;
                    case Bombers:
                        break;
                }
            }
        }
    }

    private void DoStartNote(){
        actionableComponent = new ActionableComponent(1f, 2.0f,30, Color.BLUE, Effects.ReadNote);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(338,332))
                .add(actionableComponent));
    }

    private void DoRubble() {
        actionableComponent = new ActionableComponent(1f, 2.0f,30, Color.YELLOW, Effects.Rubble);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(446,512))
                .add(actionableComponent));
    }

    private void DoCoolantLeak() {
        actionableComponent = new ActionableComponent(1f, 2.0f,25, Color.RED, Effects.CoolantLeak);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(27,855))
                .add(actionableComponent));
    }

    private void DoOppositionHacker() {
        actionableComponent = new ActionableComponent(1f, 2.0f,32, Color.RED, Effects.HackedComputer);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(224,638))
                .add(actionableComponent));
    }

    private void DoPartBreakdownOne(){
               actionableComponent = new ActionableComponent(10f, 2.0f,64, Color.RED, Effects.CoolantPumpBreakdown);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(70,860))
                .add(actionableComponent));
    }

    private void DoPartBreakdownTwo() {
        actionableComponent = new ActionableComponent(10f, 2.0f,64, Color.RED, Effects.WaterPumpBreakdown);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(48,640))
                .add(actionableComponent));
    }
}
