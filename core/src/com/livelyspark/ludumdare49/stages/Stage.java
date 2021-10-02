package com.livelyspark.ludumdare49.stages;

import java.util.ArrayList;
import java.util.Collection;

public class Stage {

    public int Index;
    public Event[] Events;
    public double PowerTarget;
    public double CurrentPower;
    public double CompletionPercent;

    public Stage(int index, Event[] events, double powerTarget){
        Index = index;
        Events = events;
        PowerTarget = powerTarget;
        CurrentPower = 0.0;
        CompletionPercent = 0.0;
    }
}
