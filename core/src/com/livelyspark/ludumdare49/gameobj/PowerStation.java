package com.livelyspark.ludumdare49.gameobj;

public class PowerStation {
    public final float heatPerFission = 100;
    public final float coolantLevelMax = 100;
    public final float coolantThermalMass = 10;
    public final float reactorThermalMass = 10;
    public final float tempAmbient = 42;
    public final float coolantFlowMax = 1;
    public final float deltaArtificialNeutrons = 100000;

    public boolean pumpOK = true;
    public boolean coolantLeakActive = false;
    public boolean artificialNeutronActive = true;

    public float controlRodPosition = 1;
    public float coolantPumpSpeed = 1;

    public float coolantLevel = 1;

    public float coolantLeakRate = 0.01f;

    public float reactorHeat = 42;
    public float reactorTemp = 42;

    public float deltaFuelAtoms = 0;

    public float deltaSlowNeutrons = 0;

    public float power = 0;

    public boolean isPaused = false;
    public boolean isDebug = false;
}
