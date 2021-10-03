package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.livelyspark.ludumdare49.components.AnimationComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.enums.AnimationLabels;
import com.livelyspark.ludumdare49.gameobj.PowerStation;

public class TurbineSpeedSystem extends IteratingSystem {

    private PowerStation powerStation;
    private static final int maxPower = 200;

    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);

    public TurbineSpeedSystem (PowerStation powerStation) {

        super(Family.all(AnimationComponent.class).get());
        this.powerStation = powerStation;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = am.get(entity);
        if (animation.animationLabel == AnimationLabels.Turbine){
                SetTurbineSpeed(animation);
        }
    }

    private void SetTurbineSpeed(AnimationComponent animation) {
        float powerFraction = powerStation.power / maxPower;
        if(powerFraction > 1){
            powerFraction = 1;
        }
        animation.frameDuration = 0.025f / powerFraction;
    }
}
