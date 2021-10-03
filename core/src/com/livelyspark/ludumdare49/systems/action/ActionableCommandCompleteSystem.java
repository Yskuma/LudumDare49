package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.CommandComponent;
import com.livelyspark.ludumdare49.components.EffectComponent;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

import java.util.EnumSet;

public class ActionableCommandCompleteSystem extends EntitySystem {

    private final ScreenState state;
    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);
    private ComponentMapper<CommandComponent> em = ComponentMapper.getFor(CommandComponent.class);
    private ImmutableArray<Entity> entities;

    public ActionableCommandCompleteSystem(ScreenState state)  {
        this.state = state;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ActionableComponent.class, CommandComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        EnumSet<Commands> completedCommands = EnumSet.noneOf(Commands.class);

        for(Entity e : entities)
        {
            ActionableComponent ac = am.get(e);
            CommandComponent cc = em.get(e);

            if(ac.isDone){
                completedCommands.add(cc.command);
                ac.timeActivated = 0;
                ac.isDone = false;
            }
        }

        state.completedCommands.addAll(completedCommands);
    }

}
