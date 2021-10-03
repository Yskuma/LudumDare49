package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.livelyspark.ludumdare49.enums.AnimationLabels;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation;
    public Float frameDuration;
    public AnimationLabels animationLabel;

    public AnimationComponent(Animation<TextureRegion> animation, Float frameDuration, AnimationLabels animationLabel){
        this.animation = animation;
        this.frameDuration = frameDuration;
        this.animationLabel = animationLabel;
    }
}