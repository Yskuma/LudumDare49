package com.livelyspark.ludumdare49.stages;

public class Stage {

    public Event[] events;
    public double powerTarget;
    public double currentPower;
    public double completionPercent;

    public Stage(Event[] events, double powerTarget){
        this.events = events;
        this.powerTarget = powerTarget;
        this.currentPower = 0.0;
        this.completionPercent = 0.0;
    }
}
