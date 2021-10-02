package com.livelyspark.ludumdare49.stages;

import com.livelyspark.ludumdare49.enums.Events;

public class Event {
    public Events events;
    public double triggerPercent;
    public boolean triggered;

    public Event (Events events, double triggerPercent){
        this.events = events;
        this.triggerPercent = triggerPercent;
        this.triggered = false;
    }
}
