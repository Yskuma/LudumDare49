package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.enums.Actions;
import com.livelyspark.ludumdare49.gameobj.ActiveActions;
import com.sun.tools.javac.comp.Resolve;

public class ActionableCompleteSystem  extends IteratingSystem {

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);

    private ActiveActions activeActions;

    public ActionableCompleteSystem(ActiveActions activeActions)  {
        super(Family.all(ActionableComponent.class).get());
        this.activeActions = activeActions;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ActionableComponent ac = am.get(entity);

        if(ac.isDone){
            switch (ac.action){
                case ReadNote:
                    ResolveReadNote();
                    break;
                case CoolantLeak:
                    ResolveCoolantLeak();
                    break;
                case WaterPumpBreakdown:
                    ResolveWaterPumpBreakdown();
                    break;
                case CoolantPumpBreakdown:
                    ResolveCoolantPumpBreakdown();
                    break;
                case HackedComputer:
                   ResolveHackedComputer();
                    break;
                case OSReinstall:
                    ResolveOSReinstall();
                    break;
            }

            activeActions.activeActions.remove(ac.action);
            this.getEngine().removeEntity(entity);
        }
    }

    private void ResolveReadNote() {
    }

    private void ResolveCoolantLeak() {
    }

    private void ResolveWaterPumpBreakdown() {
    }

    private void ResolveCoolantPumpBreakdown() {
    }

    private void ResolveHackedComputer() {
    }

    private void ResolveOSReinstall() {
    }
}
