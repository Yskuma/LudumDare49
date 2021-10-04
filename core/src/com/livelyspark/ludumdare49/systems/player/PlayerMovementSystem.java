package com.livelyspark.ludumdare49.systems.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.*;
import com.livelyspark.ludumdare49.enums.Direction;
import com.livelyspark.ludumdare49.gameobj.Player;


public class PlayerMovementSystem extends EntitySystem {

    private final Player player;

    public PlayerMovementSystem(Player player) {
        this.player = player;
            }

    @Override
    public void update (float deltaTime) {

        Vector2 velVec = new Vector2();

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
        {
            velVec.x -= 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
        {
            velVec.x += 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
        {
            velVec.y -= 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
        {
            velVec.y += 1;
        }

        velVec = velVec.nor().scl(player.speedMax);
        player.velocity.x = velVec.x;
        player.velocity.y = velVec.y;

    }

}
