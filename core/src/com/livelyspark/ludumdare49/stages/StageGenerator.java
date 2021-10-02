package com.livelyspark.ludumdare49.stages;

import java.util.ArrayList;

public class StageGenerator {
    public Stage Stage1 = new Stage(
            1,
            new Event[]{
                new Event(EventType.CoolantLeak, 25.0),
                new Event(EventType.PartBreakdownOne, 50.0),
                new Event(EventType.GloriousLeader, 75.0)
            },
            1000.0
            );
    public Stage Stage2 = new Stage(
            1,
            new Event[]{},
            1000.0
    );
    public Stage Stage3 = new Stage(
            1,
            new Event[]{},
            1000.0
    );
    public Stage Stage4 = new Stage(
            1,
            new Event[]{},
            1000.0
    );
    public Stage Stage5 = new Stage(
            1,
            new Event[]{},
            1000.0
    );
}
