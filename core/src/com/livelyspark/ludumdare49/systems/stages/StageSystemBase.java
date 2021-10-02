package com.livelyspark.ludumdare49.systems.stages;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.enums.Actions;
import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.stages.Stage;

public abstract class StageSystemBase extends IntervalSystem {

    public double currentPowerGeneration;

    protected Stage thisStage;
    private ActionableComponent actionableComponent;

    public StageSystemBase() {
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
                        StartNote();
                        break;
                    case EarthQuake:
                        break;
                    case CoolantLeak:
                        break;
                    case GloriousLeader:
                        break;
                    case OppositionHacker:
                        break;
                    case RebelInvasion:
                        break;
                    case PartBreakdownOne:
                        PartBreakdownOne();
                        break;
                    case PartBreakdownTwo:
                        break;
                    case PartBreakdownThree:
                        break;
                    case Bombers:
                        break;
                }
            }
        }
    }

    private void StartNote(){
        actionableComponent = new ActionableComponent(1f, 2.0f,30, Color.BLUE, Actions.ReadNote);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(338,332))
                .add(actionableComponent));
    }

    private void PartBreakdownOne(){
        actionableComponent = new ActionableComponent(10f, 2.0f,32, Color.RED, Actions.CoolantPumpBreakdown);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(300,150))
                .add(actionableComponent));
    }



}
