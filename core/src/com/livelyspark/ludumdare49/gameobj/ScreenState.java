package com.livelyspark.ludumdare49.gameobj;

import com.badlogic.gdx.InputMultiplexer;
import com.livelyspark.ludumdare49.components.MessageComponent;
import com.livelyspark.ludumdare49.enums.CameraModes;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.enums.Effects;

import java.util.EnumSet;

public class ScreenState {
    public boolean isDebug = false;

    public CameraModes cameraMode = CameraModes.Lock;

    public MessageComponent activeMessage = null;

    public EnumSet<Effects> activeEffects = EnumSet.noneOf(Effects.class);

    public EnumSet<Commands> completedCommands = EnumSet.noneOf(Commands.class);
    public EnumSet<Effects> completedEffects = EnumSet.noneOf(Effects.class);

    public EnumSet<Commands> actioningCommands = EnumSet.noneOf(Commands.class);
    public EnumSet<Effects> actioningEffects = EnumSet.noneOf(Effects.class);

    public EnumSet<Commands> disabledCommands = EnumSet.noneOf(Commands.class);

    public int timoutCountdown = 30;

}
