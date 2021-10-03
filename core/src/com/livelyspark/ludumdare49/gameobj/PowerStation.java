package com.livelyspark.ludumdare49.gameobj;

public class PowerStation {
    public final float heatPerFission = 0.00000000083333f;
    public final float coolantLevelMax = 1;
    public final float coolantThermalMass = 40;
    public final float reactorThermalMass = 10;
    public final float tempAmbient = 283;
    public final float coolantFlowMax = 0.008f;
    public final float deltaArtificialNeutrons = 10000000;

    public boolean pumpOK = true;
    public boolean coolantLeakActive = false;
    public boolean artificialNeutronActive = true;

    public float controlRodPosition = 1;
    public float coolantPumpSpeed = 1;

    public float coolantLevel = 1;

    public float coolantLeakRate = 0.01f;

    public float reactorHeat = 14650;
    public float reactorTemp = 293;

    public float deltaFuelAtoms = 0;

    public float deltaSlowNeutrons = 0;

    public float power = 0;

    public boolean isPaused = false;
}
