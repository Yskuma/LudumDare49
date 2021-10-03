package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

import java.util.EnumSet;

public class ActionableCompleteSystem extends EntitySystem {

    private final ScreenState state;
    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ImmutableArray<Entity> entities;

    public ActionableCompleteSystem(ScreenState state)  {
        this.state = state;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ActionableComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        EnumSet<Effects> currentEffects = EnumSet.noneOf(Effects.class);
        EnumSet<Effects> completedEffects = EnumSet.noneOf(Effects.class);

        for(Entity e : entities)
        {
            ActionableComponent ac = am.get(e);

            if(ac.isDone){
                this.getEngine().removeEntity(e);
            }
            else
            {
                currentEffects.add(ac.effect);
            }
        }

        for(Effects eff : state.activeEffects) {
            if(!currentEffects.contains(eff))
            {
                completedEffects.add(eff);
            }
        }

        state.activeEffects = currentEffects;
        state.completedEffects = completedEffects;
    }

}
