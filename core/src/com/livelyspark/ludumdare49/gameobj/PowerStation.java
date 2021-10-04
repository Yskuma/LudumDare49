package com.livelyspark.ludumdare49.gameobj;

public class PowerStation {
    public final float REACTOR_TEMP_LOW = 473;
    public final float REACTOR_TEMP_OK = 673;
    public final float REACTOR_TEMP_HIGH = 973;
    public final float REACTOR_TEMP_BOOM = 1073;

    public final float heatPerFission = 0.00000000083333f;
    public final float coolantLevelMax = 1;
    public final float coolantThermalMass = 30;
    public final float reactorThermalMass = 20;
    public final float tempAmbient = 283;
    public final float coolantFlowMax = 0.005f;
    public final float deltaArtificialNeutrons = 10000000;

    public boolean pumpOK = true;
    public boolean coolantLeakActive = false;
    public boolean artificialNeutronActive = true;

    public float controlRodPosition = 0.6f;
    public float coolantPumpSpeed = 1;

    public float coolantLevel = 1;

    public float coolantLeakRate = 0.015f;

    public float reactorHeat = 33650;
    public float reactorTemp = 673;

    public float reactorTempDelta = 0;
    public float coolantLevelDelta = 0;

    public float deltaFuelAtoms = 1.2E11f;

    public float deltaSlowNeutrons = 3E11f;

    public float power = 0;
    public float targetPower = 150f;

    public boolean isPaused = false;
}
