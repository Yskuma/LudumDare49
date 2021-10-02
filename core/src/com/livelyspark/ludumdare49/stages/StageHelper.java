package com.livelyspark.ludumdare49.stages;

import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class StageHelper {

    public double CurrentPowerGeneration;
    public EnumSet<EventType> PendingEvents = EnumSet.noneOf(EventType.class);

    private Stage CurrentStage;
    private StageGenerator _generator = new StageGenerator();

    public void SetStage(int stageNumber){
        switch(stageNumber){
            case 1:
                CurrentStage = _generator.Stage1;
                break;
            case 21:
                CurrentStage = _generator.Stage2;
                break;
            case 3:
                CurrentStage = _generator.Stage3;
                break;
            case 4:
                CurrentStage = _generator.Stage4;
                break;
            case 5:
                CurrentStage = _generator.Stage5;
                break;
        }
        throw new InvalidParameterException("Stage "+stageNumber+" does not exist.");
    }

    public void UpdateProgress(){
        CurrentStage.CurrentPower += CurrentPowerGeneration;
        CurrentStage.CompletionPercent = 100 * (CurrentStage.CurrentPower / CurrentStage.PowerTarget);
    }

    public void GenerateEvents(){
        for (int i = 0; i < CurrentStage.Events.length; i++) {
            Event event = CurrentStage.Events[i];
            if(!event.Triggered
            && event.TriggerPercent >= CurrentStage.CompletionPercent){
                PendingEvents.add(event.Type);
            }
        }
    }


}
