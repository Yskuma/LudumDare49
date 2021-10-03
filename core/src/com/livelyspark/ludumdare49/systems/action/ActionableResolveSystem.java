package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

import java.util.EnumSet;

public class ActionableResolveSystem extends EntitySystem {

    private final ScreenState state;
    private ComponentMapper<ActionableComponent> am = ComponentMapper.getFor(ActionableComponent.class);

    public ActionableResolveSystem(ScreenState state)  {
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

        state.completedEffects  = EnumSet.noneOf(Effects.class);
    }

    private void ResolveReadNote() {
    }

    private void ResolveCoolantLeak() {
    }

    private void ResolveWaterPumpBreakdown() {
    }

    private void ResolveCoolantPumpBreakdown() {
    }

    private void ResolveHackedComputer() {
    }

    private void ResolveOSReinstall() {
    }

    private void ResolveRubble() {
    }
}
