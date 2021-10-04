package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.MessageComponent;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.enums.MessageTextures;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

import java.util.EnumSet;

public class ActionableEffectResolveSystem extends EntitySystem {

    private final ScreenState state;

    public ActionableEffectResolveSystem(ScreenState state)  {
        this.state = state;
    }

    @Override
    public void addedToEngine (Engine engine) {
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(state.completedEffects.contains(Effects.CoolantLeak))
        {
            ResolveCoolantLeak();
        }
        if(state.completedEffects.contains(Effects.ReadNote))
        {
            ResolveReadNote();
        }
        if(state.completedEffects.contains(Effects.HackedComputer))
        {
            ResolveHackedComputer();
        }

        state.completedEffects  = EnumSet.noneOf(Effects.class);
    }

    private void ResolveReadNote() {
        this.getEngine().addEntity(new Entity()
                .add(new MessageComponent(
                        "Sorry I couldn't give you an induction, the state won't pay for more than one worker on site. " +
                                "\n\nYou must keep the reactor running and meet your power production targets. " +
                                "\n\nIf you fail our Glorious Leader will not be happy!", MessageTextures.Note)));
    }

    private void ResolveCoolantLeak() {
    }

    private void ResolveWaterPumpBreakdown() {
    }

    private void ResolveCoolantPumpBreakdown() {
    }

    private void ResolveHackedComputer() {
        state.disabledCommands.remove(Commands.ControlRodIncrease);
        state.disabledCommands.remove(Commands.ControlRodDecrease);
    }

    private void ResolveOSReinstall() {
    }

    private void ResolveRubble() {
    }
}
