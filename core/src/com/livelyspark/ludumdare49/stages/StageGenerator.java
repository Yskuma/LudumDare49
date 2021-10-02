package com.livelyspark.ludumdare49.stages;

import com.livelyspark.ludumdare49.enums.Events;

public class StageGenerator {
    public Stage stage1 = new Stage(
            new Event[]{
                new Event(Events.CoolantLeak, 25.0),
                new Event(Events.PartBreakdownOne, 50.0),
                new Event(Events.GloriousLeader, 75.0)
            },
            1000.0
            );
    public Stage stage2 = new Stage(
            new Event[]{},
            1000.0
    );
    public Stage stage3 = new Stage(
            new Event[]{},
            1000.0
    );
    public Stage stage4 = new Stage(
            new Event[]{},
            1000.0
    );
    public Stage stage5 = new Stage(
            new Event[]{},
            1000.0
    );
}
