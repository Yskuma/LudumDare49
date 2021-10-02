package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.livelyspark.ludumdare49.components.*;


public class ActionableActivateSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    private PositionComponent playerPosition;
    private float actionableDist = 32;

    public ActionableActivateSystem(PositionComponent playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ActionableComponent.class, PositionComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)){return;}

        ActionableComponent closestAction = null;
        float closest = 9999999999f;

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            PositionComponent pos = pm.get(e);

            float dist = pos.dst(playerPosition);
            ActionableComponent ac = am.get(e);

            if(dist < ac.activationDist && dist < closest)
            {
                closest = dist;
                closestAction = ac;
            }
        }

        if(closestAction != null)
        {
            closestAction.isActive = true;
            closestAction.timeActivated += deltaTime;
            closestAction.timeActivated = Math.min(closestAction.timeActivated, closestAction.timeToActivate);

            if(closestAction.timeActivated >= closestAction.timeToActivate)
            {
                closestAction.isDone = true;
            }
        }

    }

}
