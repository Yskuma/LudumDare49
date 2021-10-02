package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.livelyspark.ludumdare49.components.ActionableComponent;

public class ActionableDecaySystem extends IteratingSystem {

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);

    public ActionableDecaySystem() {
        super(Family.all(ActionableComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        ActionableComponent ac = am.get(entity);

        if(!ac.isDone && !ac.isActive)
        {
            ac.timeActivated -= (ac.decayRate * deltaTime);
            ac.timeActivated = Math.max(0, ac.timeActivated);
        }

        ac.isActive = false;
    }
}
