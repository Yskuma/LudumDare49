package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IntervalSystem;
import com.livelyspark.ludumdare49.components.EventComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.stages.Stage;
import com.livelyspark.ludumdare49.stages.StageHelper;

public class StageSystem extends IntervalIteratingSystem {

    private ComponentMapper<EventComponent> em = ComponentMapper.getFor(EventComponent.class);

    private StageHelper _stageHelper;

    public StageSystem(StageHelper stageHelper) {
        super(Family.all(EventComponent.class).get(), 1.0f);
        _stageHelper = stageHelper;
    }

    @Override
    protected void updateInterval() {
        _stageHelper.UpdateProgress();
        _stageHelper.GenerateEvents();
    }

    @Override
    protected void processEntity(Entity entity) {
        EventComponent event = em.get(entity);
        if(_stageHelper.PendingEvents.contains(event.Type)){
            event.InProgress = true;
            _stageHelper.PendingEvents.remove(event.Type);
        }
    }


}
