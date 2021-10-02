package com.livelyspark.ludumdare49.systems.stages;

import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.enums.Events;
import com.livelyspark.ludumdare49.stages.Stage;

public class Stage01System extends StageSystemBase {
    @Override
    protected void GenerateStage() {
        thisStage = new Stage(
                new Event[]{
                        new Event(Events.CoolantLeak, 25.0),
                        new Event(Events.PartBreakdownOne, 50.0),
                        new Event(Events.GloriousLeader, 75.0)
                },
                1000.0
        );
    }
}
