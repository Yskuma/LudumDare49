package com.livelyspark.ludumdare49.gameobj;

public class PowerStation {

    public final int fuelAtomsInit = 42;
    public int fuelAtoms;

    public int fastNeutronFlow;
    public int slowNeutronFlow;
    public int artificialNeutronFlow;

    public float heatPerFission;

    public float controlRodPosition;

    public float coolantLevel;
    public boolean coolantFull;
    public boolean coolantEmpty;
    public float coolantPumpSpeed;
    public float coolantThermalMass;
    public float coolantFlowMax;
    public boolean coolantLeakActive;
    public float coolantLeakRate;
    public boolean pumpOK;

    public boolean artificialNeutronActive;

    public float slowNeutronColProb;

    public float heatFromReactor;
    public float heatToTurbine;

    public float tempReactor;
    public float tempTurbine;
    public float tempAmbient;

    public float reactorThermalMass;

}
