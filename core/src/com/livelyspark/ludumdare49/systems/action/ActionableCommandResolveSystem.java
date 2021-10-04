package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.MathUtils;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

import java.util.EnumSet;

public class ActionableCommandResolveSystem extends EntitySystem {

    private final PowerStation ps;
    private final ScreenState state;


    public ActionableCommandResolveSystem(ScreenState state, PowerStation powerStation)  {
        this.state = state;
        this.ps = powerStation;
    }

    @Override
    public void addedToEngine (Engine engine) {
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(state.completedCommands.contains(Commands.ControlRodIncrease))
        {
            ps.controlRodPosition += 0.1f;
            ps.controlRodPosition = MathUtils.clamp(ps.controlRodPosition, 0f, 1f);
        }

        if(state.completedCommands.contains(Commands.ControlRodDecrease))
        {
            ps.controlRodPosition -= 0.1f;
            ps.controlRodPosition = MathUtils.clamp(ps.controlRodPosition, 0f, 1f);
        }

        if(state.completedCommands.contains(Commands.CoolantPumpIncrease))
        {
            ps.coolantPumpSpeed += 0.1f;
            ps.coolantPumpSpeed = MathUtils.clamp(ps.coolantPumpSpeed, 0f, 1f);
        }

        if(state.completedCommands.contains(Commands.CoolantPumpDecrease))
        {
            ps.coolantPumpSpeed -= 0.1f;
            ps.coolantPumpSpeed = MathUtils.clamp(ps.coolantPumpSpeed, 0f, 1f);
        }

        if(state.completedCommands.contains(Commands.CoolantLevelIncrease))
        {
            ps.coolantLevel += 0.01f;
            ps.coolantLevel = MathUtils.clamp(ps.coolantLevel, 0f, 1f);
        }

        if(state.completedCommands.contains(Commands.CoolantLevelDecrease))
        {
            ps.coolantLevel -= 0.01f;
            ps.coolantLevel = MathUtils.clamp(ps.coolantLevel, 0f, 1f);
        }

        state.completedCommands.clear();
    }

}
