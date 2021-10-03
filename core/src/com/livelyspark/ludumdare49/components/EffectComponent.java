package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.livelyspark.ludumdare49.enums.Effects;

public class EffectComponent implements Component {

    public Effects effect;

    public EffectComponent(Effects effect)
    {
        this.effect = effect;
    }

}
