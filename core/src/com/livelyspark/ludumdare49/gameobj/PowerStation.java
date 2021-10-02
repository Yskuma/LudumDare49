package com.livelyspark.ludumdare49.gameobj;

public class PowerStation {
    public final float heatPerFission = 42;
    public final float coolantLevelMax = 42;
    public final float coolantThermalMass = 42;
    public final float tempAmbient = 42;

    public float controlRodPosition;

    public float coolantLevel;
    public float coolantPumpSpeed;

    public float coolantFlowMax;
    public boolean coolantLeakActive;
    public float coolantLeakRate;
    public boolean pumpOK;

    public boolean artificialNeutronActive;

    public float tempReactor;

    public float reactorThermalMass;

    public float deltaArtificialNeutrons;
    public float deltaSlowNeutrons;
    public float deltaFuelAtoms;

    public float reactorHeat;
    public float power;
}
