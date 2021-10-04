package com.livelyspark.ludumdare49.systems.sound;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SoundComponent;

public class SoundListener implements EntityListener {

    private ComponentMapper<SoundComponent> sm = ComponentMapper.getFor(SoundComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public SoundListener()
    {

    }

    @Override
    public void entityAdded(Entity entity) {
        SoundComponent s = sm.get(entity);
        if(s != null)
        {
            if(s.isLooped)
            {
                s.soundId = s.sound.loop();
            }
            else
            {
                s.soundId = s.sound.play();
            }
        }
    }

    @Override
    public void entityRemoved(Entity entity) {
        //SoundComponent s = sm.get(entity);
        //s.sound.dispose();
    }
}
