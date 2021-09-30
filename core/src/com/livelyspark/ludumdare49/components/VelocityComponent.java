package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float velocityX;
    public float velocityY;

    public VelocityComponent (float velocityX, float velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }
}
