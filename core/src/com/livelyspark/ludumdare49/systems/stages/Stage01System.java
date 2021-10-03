package com.livelyspark.ludumdare49.systems.stages;

import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.enums.Events;
import com.livelyspark.ludumdare49.stages.Stage;

public class Stage01System extends StageSystemBase {
    public Stage01System(ScreenState state) {
        super(state);
    }

    @Override
    protected void GenerateStage() {
        thisStage = new Stage(
                new Event[]{
                        new Event(Events.StartNote, 0.0),
                        new Event(Events.CoolantLeak, 0.0),
                        new Event(Events.PartBreakdownOne, 0.0),
                        new Event(Events.PartBreakdownTwo, 0.0),
                        new Event(Events.OppositionHacker, 0.0),
                        new Event(Events.EarthQuake, 0.0)
                },
                1000.0
        );
    }
}
