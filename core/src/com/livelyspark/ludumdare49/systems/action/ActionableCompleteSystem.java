package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.livelyspark.ludumdare49.components.ActionableComponent;

public class ActionableCompleteSystem  extends IteratingSystem {

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);

    public ActionableCompleteSystem()  {super(Family.all(ActionableComponent.class).get());}

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ActionableComponent ac = am.get(entity);

        if(ac.isDone){
            switch (ac.action){
                case ReadNote:
                    DoReadNote();
                    break;
                case CoolantLeak:
                    break;
                case WaterPumpBreakdown:
                    break;
                case CoolantPumpBreakdown:
                    break;
                case HackedComputer:
                    break;
                case OSReinstall:
                    break;
            }

            this.getEngine().removeEntity(entity);
        }
    }

    private void DoReadNote() {

    }


}
