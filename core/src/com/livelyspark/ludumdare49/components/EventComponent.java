package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.livelyspark.ludumdare49.stages.EventType;

public class EventComponent implements Component {
    public EventType Type;
    public boolean InProgress;

    public EventComponent (EventType type){
        Type = type;
    }
}
