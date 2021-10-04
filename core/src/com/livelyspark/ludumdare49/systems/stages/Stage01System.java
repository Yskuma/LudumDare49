package com.livelyspark.ludumdare49.systems.stages;

import com.livelyspark.ludumdare49.enums.MessageTextures;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.stages.Event;
import com.livelyspark.ludumdare49.enums.Events;
import com.livelyspark.ludumdare49.stages.Stage;

public class Stage01System extends StageSystemBase {


    public Stage01System(PowerStation powerStation, IScreenManager screenManager) {
        super(powerStation, screenManager);
    }

    @Override
    protected void GenerateStage() {
        thisStage = new Stage(
                new Event[]{
                        new Event(Events.StartNote, 0.0, "First day on the job! It looks like someone has left me a note.", MessageTextures.Player),
                        new Event(Events.CoolantLeak, 5, "Our world leading pipe manufacturers make the best pipes, they never leak!", MessageTextures.Leader),
                        new Event(Events.PartBreakdownOne, 10.0, "We make the best, most reliable, and heaviest pumps. No one makes heavier pumps!", MessageTextures.Leader),
                        new Event(Events.CoolantLeak, 15.0, "Coolant levels are dropping again, the pipe must be leaking", MessageTextures.Player),
                        new Event(Events.PartBreakdownTwo, 20.0, "Any rumours about our pumps being unreliable is unprovable slander!", MessageTextures.Leader),
                        new Event(Events.OppositionHacker, 25.0, "We have the most accessible IT infrastructure, allowing easy remote access for our workers. " +
                                "\n\nAny Security concerns are just fearmongering!", MessageTextures.Leader),
                        new Event(Events.PartBreakdownThree, 30.0, "Heat Exchangers? I'm not sure what they are. " +
                                "\n\nBut I bet we manufacture the best, cleanest heat exchangers in the world!", MessageTextures.Leader),
                        new Event(Events.PartBreakdownOne, 35.0, "Power levels are dropping, better check the coolant pump.", MessageTextures.Player),
                        new Event(Events.CoolantLeak, 40.0, "Sounds like I'll be getting wet feet again.", MessageTextures.Player),
                        new Event(Events.PartBreakdownTwo, 45.0, "Water pump breakdown, this is getting ridiculous.", MessageTextures.Player),
                        new Event(Events.MoarPowah, 45.0, "MOOOAAAR POOWAAAAH!!! " +
                                "\n\nIncrease output to 110 MW!", MessageTextures.Leader),
                        new Event(Events.EarthQuake, 55.0, "Our buildings are the strongest in the world. " +
                                "\n\nThey are able to withstand any natural disaster!", MessageTextures.Leader),
                        new Event(Events.CoolantLeak, 65.0, "I hear water running.", MessageTextures.Player),
                        new Event(Events.EvenMoarPowah, 75.0, "EVEEEN MOOOAAAR POOWAAAAH!!! " +
                                "\n\nIncrease output to 120 MW!", MessageTextures.Leader),
                        new Event(Events.EarthQuake, 80.0, "AFTERSHOCK!!!", MessageTextures.Player),
                        new Event(Events.PartBreakdownOne, 85.0,"It's gone quiet...", MessageTextures.Player),
                        new Event(Events.SheCantTakeMuchMoreCaptain, 90.0,"She can't take much more of this!", MessageTextures.Player),
                },
                45000.0
        );
    }
}
