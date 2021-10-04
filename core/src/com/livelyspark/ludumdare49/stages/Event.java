package com.livelyspark.ludumdare49.stages;

import com.livelyspark.ludumdare49.enums.Events;
import com.livelyspark.ludumdare49.enums.MessageTextures;

public class Event {
    public Events events;
    public double triggerPercent;
    public boolean triggered;
    public String message;
    public MessageTextures texture;

    public Event (Events events, double triggerPercent){
        this.events = events;
        this.triggerPercent = triggerPercent;
        this.triggered = false;
        this.message = "";
        texture = MessageTextures.None;
    }

    public Event (Events events, double triggerPercent, String message, MessageTextures texture){
        this.events = events;
        this.triggerPercent = triggerPercent;
        this.triggered = false;
        this.message = message;
        this.texture = texture;
    }
}
