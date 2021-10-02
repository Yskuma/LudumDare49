package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class ActionableComponent implements Component {

    //Time spent using needed to activate (can be zero)
    public final float timeToActivate;
    public final float decayRate;

    float timeActivated = 0.0f;

    public ActionableComponent(float timeToActivate, float decayRate)
    {
        this.timeToActivate = timeToActivate;
        this.decayRate = decayRate;
    }


}
