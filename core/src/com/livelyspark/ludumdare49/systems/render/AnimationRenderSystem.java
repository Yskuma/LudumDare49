package com.livelyspark.ludumdare49.systems.render;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.livelyspark.ludumdare49.components.AnimationComponent;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.components.TurbineComponent;
import com.livelyspark.ludumdare49.enums.AnimationLabels;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class AnimationRenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private PowerStation powerStation;
    private ScreenState screenState;

    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private float statetime = 0.0f;

    public AnimationRenderSystem(OrthographicCamera camera, PowerStation powerStation, ScreenState screenState) {
        batch = new SpriteBatch();
        this.camera = camera;
        this.powerStation = powerStation;
        this.screenState = screenState;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class, PositionComponent.class).exclude(TurbineComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(camera == null){
            return;
        }

        AnimationComponent animation;
        PositionComponent position;

        camera.update();
        statetime += deltaTime;

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            animation = am.get(e);
            position = pm.get(e);

            TextureRegion currentFrame;
            if(animation.animationLabel == AnimationLabels.Reactor){
                currentFrame = GenerateReactorFrame(animation.animation);
            }
            else {
                if(!screenState.actioningCommands.contains(Commands.CoolantLevelIncrease)){
                    statetime = 0.0f;
                }

                // Get current frame of animation for the current stateTime
                currentFrame = animation.animation.getKeyFrame(statetime, true);
            }

            batch.draw(currentFrame, position.x, position.y);
        }

        batch.end();
    }

    private TextureRegion GenerateReactorFrame(Animation<TextureRegion> animation) {
        if(powerStation.reactorTemp < powerStation.REACTOR_TEMP_OK){
            return animation.getKeyFrames()[0];
        }
        else if(powerStation.reactorTemp >= powerStation.REACTOR_TEMP_OK && powerStation.reactorTemp < powerStation.REACTOR_TEMP_HIGH){
            return animation.getKeyFrames()[1];
        }
        else{
            return animation.getKeyFrames()[2];
        }
    }


}
