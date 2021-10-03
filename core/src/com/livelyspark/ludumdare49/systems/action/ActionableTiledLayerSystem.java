package com.livelyspark.ludumdare49.systems.action;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.livelyspark.ludumdare49.components.ActionableComponent;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.enums.Effects;
import com.livelyspark.ludumdare49.gameobj.ScreenState;


public class ActionableTiledLayerSystem extends EntitySystem {

    private final TiledMap tiledMap;
    private final ScreenState state;
    private final MapLayer coolantLeak1Layer;

    public ActionableTiledLayerSystem(TiledMap tiledMap, ScreenState state) {
        this.tiledMap = tiledMap;
        this.state = state;

        this.coolantLeak1Layer = tiledMap.getLayers().get("CoolantLeak1");
    }

    @Override
    public void addedToEngine (Engine engine) {
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        coolantLeak1Layer.setVisible(state.activeEffects.contains(Effects.CoolantLeak));
    }
}
