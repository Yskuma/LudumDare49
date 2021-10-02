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
        ps.deltaFuelAtoms = 0.44f * (1 - ps.controlRodPosition) * (ps.deltaSlowNeutrons * deltaTime);
        ps.deltaSlowNeutrons = 2.5f * ps.coolantLevel * ps.deltaFuelAtoms
                + (ps.artificialNeutronActive ? (ps.deltaArtificialNeutrons * deltaTime) : 0f);

        ps.coolantLevel -= ps.coolantLeakActive ? ps.coolantLeakRate * deltaTime : 0;
        ps.coolantLevel += ps.pumpOK ? ps.coolantPumpSpeed * ps.coolantFlowMax * deltaTime : 0;
        ps.coolantLevel = MathUtils.clamp(ps.coolantLevel, 0, ps.coolantLevelMax);

        float deltaReactorHeat = ps.deltaFuelAtoms * ps.heatPerFission * deltaTime;
        float deltaTurbineHeat = 0;
        if(ps.coolantLevel > 0 && ps.pumpOK) {
            deltaTurbineHeat = ps.coolantPumpSpeed * ps.coolantFlowMax * ps.coolantThermalMass * ps.tempReactor * deltaTime;
        }
        // TODO: Deal with coolant temp changes

        ps.reactorHeat = ps.reactorHeat + deltaReactorHeat - deltaTurbineHeat;

        ps.tempReactor = ps.reactorHeat / ((ps.coolantLevel * ps.coolantThermalMass) + ps.reactorThermalMass);

        if(ps.tempReactor < 373)
        {
            ps.power = 0;
        }
        else if(ps.tempReactor >= 373 && ps.tempReactor < 673)
        {
            float e = (ps.tempReactor - 373)/(673 - 373);
            ps.power = (deltaTurbineHeat * e) / deltaTime;
        }
        else
        {
            ps.power = deltaTurbineHeat / deltaTime;
        }

    }

}
