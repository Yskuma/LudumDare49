package com.livelyspark.ludumdare49.gameobj;

import com.livelyspark.ludumdare49.components.MessageComponent;
import com.livelyspark.ludumdare49.enums.Effects;

import java.util.EnumSet;

public class ScreenState {
    public MessageComponent activeMessage = null;
    public EnumSet<Effects> activeEffects = EnumSet.noneOf(Effects.class);
    public EnumSet<Effects> completedEffects = EnumSet.noneOf(Effects.class);
}
