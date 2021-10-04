package com.livelyspark.ludumdare49.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.MathUtils;
import com.livelyspark.ludumdare49.gameobj.PowerStation;

public class ReactorSystem extends EntitySystem {

    private final PowerStation ps;

    public ReactorSystem(PowerStation powerStation) {
        this.ps = powerStation;
    }

    @Override
    public void addedToEngine(Engine engine) {
    }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {

        //deltaTime *= 10;
        if(ps.isPaused)
        {
            return;
        }

        ps.deltaFuelAtoms = 0.4f * (1.00150f - 0.0025f*ps.controlRodPosition) * (ps.deltaSlowNeutrons);
        ps.deltaFuelAtoms = MathUtils.clamp(ps.deltaFuelAtoms, 4.5e10f, 4.5e11f);

        if(ps.coolantLevel > 0.5f){
            ps.deltaSlowNeutrons = 2.5f * ps.deltaFuelAtoms
                    + (ps.artificialNeutronActive ? (ps.deltaArtificialNeutrons * deltaTime) : 0f);
        }
        else if(ps.coolantLevel <= 0.5f){
            ps.deltaSlowNeutrons = 2.5f * (ps.coolantLevel/0.5f) * ps.deltaFuelAtoms
                    + (ps.artificialNeutronActive ? (ps.deltaArtificialNeutrons * deltaTime) : 0f);
        }

        float oldCoolantLevel  = ps.coolantLevel;
        ps.coolantLevel -= ps.coolantLeakActive ? ps.coolantLeakRate * deltaTime : 0;
        ps.coolantLevel += ps.pumpOK ? ps.coolantPumpSpeed * ps.coolantFlowMax * deltaTime : 0;
        ps.coolantLevel = MathUtils.clamp(ps.coolantLevel, 0, ps.coolantLevelMax);
        ps.coolantLevelDelta = (ps.coolantLevel - oldCoolantLevel ) / deltaTime;

        float deltaReactorHeat = ps.deltaFuelAtoms * ps.heatPerFission * deltaTime;

        float deltaTurbineHeat = 0;
        if(ps.coolantLevel > 0 && ps.pumpOK) {
            deltaTurbineHeat = ps.coolantPumpSpeed * ps.coolantFlowMax * ps.coolantThermalMass * ps.reactorTemp * deltaTime;
        }

        float deltaCoolantHeat = 0;
        if(ps.coolantLevel < 1 && ps.pumpOK) {
            deltaCoolantHeat = ps.coolantPumpSpeed*ps.coolantFlowMax*ps.coolantThermalMass*ps.reactorTemp*deltaTime;
        }
        if(ps.coolantLevel > 0 && ps.coolantLeakActive) {
            deltaCoolantHeat = deltaCoolantHeat - ps.coolantLeakRate*ps.coolantThermalMass*ps.reactorTemp*deltaTime;
        }

        ps.reactorHeat = ps.reactorHeat + deltaReactorHeat - deltaTurbineHeat + deltaCoolantHeat;

        float oldReactorTemp = ps.reactorTemp;
        ps.reactorTemp = ps.reactorHeat / ((ps.coolantLevel * ps.coolantThermalMass) + ps.reactorThermalMass);
        ps.reactorTempDelta = (ps.reactorTemp - oldReactorTemp) / deltaTime;

        if(ps.reactorTemp < ps.REACTOR_TEMP_LOW)
        {
            ps.power = 0;
        }
        else if(ps.reactorTemp >= ps.REACTOR_TEMP_LOW && ps.reactorTemp < ps.REACTOR_TEMP_OK)
        {
            float e = (ps.reactorTemp - ps.REACTOR_TEMP_LOW)/(ps.REACTOR_TEMP_OK - ps.REACTOR_TEMP_LOW);
            ps.power = (deltaTurbineHeat * e) / deltaTime;
        }
        else
        {
            ps.power = deltaTurbineHeat / deltaTime;
        }

    }

}
