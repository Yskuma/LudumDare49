package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {
    public Animation<TextureRegion> animation;

    public AnimationComponent(Animation<TextureRegion> animation){
        this.animation = animation;
    }
}