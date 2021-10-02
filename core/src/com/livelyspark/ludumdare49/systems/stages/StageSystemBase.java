package com.livelyspark.ludumdare49.systems.stages;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.enums.Actions;
import com.livelyspark.ludumdare49.gameobj.ActiveActions;
import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.stages.Stage;

public abstract class StageSystemBase extends IntervalSystem {

    public double currentPowerGeneration;

    protected Stage thisStage;
    private ActionableComponent actionableComponent;
    private ActiveActions activeActions;

    public StageSystemBase(ActiveActions activeActions) {
        super( 1.0f);
        this.activeActions = activeActions;
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
        Actions action = Actions.ReadNote;

        actionableComponent = new ActionableComponent(1f, 2.0f,30, Color.BLUE, action);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(338,332))
                .add(actionableComponent));

        activeActions.activeActions.add(action);
    }

    private void DoRubble() {
        Actions action = Actions.Rubble;

        actionableComponent = new ActionableComponent(1f, 2.0f,30, Color.YELLOW, action);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(338,332))
                .add(actionableComponent));

        activeActions.activeActions.add(action);
    }

    private void DoCoolantLeak() {
        Actions action = Actions.CoolantLeak;

        actionableComponent = new ActionableComponent(1f, 2.0f,25, Color.RED, action);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(27,855))
                .add(actionableComponent));

        activeActions.activeActions.add(action);
    }

    private void DoOppositionHacker() {
        Actions action = Actions.HackedComputer;

        actionableComponent = new ActionableComponent(1f, 2.0f,32, Color.RED, action);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(224,638))
                .add(actionableComponent));

        activeActions.activeActions.add(action);
    }

    private void DoPartBreakdownOne(){
        Actions action = Actions.CoolantPumpBreakdown;

        actionableComponent = new ActionableComponent(10f, 2.0f,64, Color.RED, action);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(70,860))
                .add(actionableComponent));

        activeActions.activeActions.add(action);
    }

    private void DoPartBreakdownTwo() {
        Actions action = Actions.WaterPumpBreakdown;

        actionableComponent = new ActionableComponent(10f, 2.0f,64, Color.RED, action);
        this.getEngine().addEntity((new Entity())
                .add(new PositionComponent(48,640))
                .add(actionableComponent));

        activeActions.activeActions.add(action);
    }
}
