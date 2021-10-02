package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.livelyspark.ludumdare49.enums.Events;

public class EventComponent implements Component {
    public Events Type;
    public boolean InProgress;

    public EventComponent (Events type){
        Type = type;
    }
}
