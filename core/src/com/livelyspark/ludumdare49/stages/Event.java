package com.livelyspark.ludumdare49.stages;

public class Event {
    public EventType Type;
    public double TriggerPercent;
    public boolean Triggered;

    public Event (EventType type, double triggerPercent){
        Type = type;
        TriggerPercent = triggerPercent;
        Triggered = false;
    }
}
