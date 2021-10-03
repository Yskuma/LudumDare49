package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare49.enums.Effects;

public class ActionableComponent implements Component {

    //Time spent using needed to activate (can be zero)
    public final float timeToActivate;
    public final float decayRate;

    public float activationDist;

    public Color color;
    public float size;

    public float timeActivated = 0.0f;
    public boolean isActive = false;
    public boolean isDone = false;

    public Effects effect;

    public ActionableComponent(float timeToActivate, float decayRate, float size, Color color, Effects effect)
    {
        this.timeToActivate = timeToActivate;
        this.decayRate = decayRate;
        this.size = size;
        this.color = color;
        this.activationDist = (size / 2) + 16;
        this.effect = effect;
    }


}
