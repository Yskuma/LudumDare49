package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.livelyspark.ludumdare49.enums.CameraModes;

public class RumbleComponent implements Component {
    public CameraModes cameraMode;
    public float timeRemaining;

    public RumbleComponent(CameraModes cameraMode, float time) {
        this.cameraMode = cameraMode;
        this.timeRemaining = time;
    }
}