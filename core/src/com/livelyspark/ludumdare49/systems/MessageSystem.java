package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare49.components.MessageComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class MessageSystem extends EntitySystem {
    private final ScreenState state;
    private ImmutableArray<Entity> entities;
    private ComponentMapper<MessageComponent> mm = ComponentMapper.getFor(MessageComponent.class);

    public MessageSystem(ScreenState state) {
        this.state = state;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(MessageComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        MessageComponent m = null;
        long firstTime = Long.MAX_VALUE;

        for(Entity e : entities)
        {
            MessageComponent mc = mm.get(e);

            if(mc.isRead)
            {
                this.getEngine().removeEntity(e);
            }
            else if(mc.addedTime < firstTime)
            {
                m = mc;
                firstTime = m.addedTime;
            }
        }

        state.activeMessage = m;
    }
}