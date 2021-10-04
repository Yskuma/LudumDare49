package com.livelyspark.ludumdare49.systems.player;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.PlayerComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;
import com.livelyspark.ludumdare49.enums.Direction;
import com.livelyspark.ludumdare49.gameobj.Player;


public class PlayerUpdateSystem extends EntitySystem {

    private final Player player;
    public PlayerUpdateSystem(Player player) {
        this.player = player;
            }

    @Override
    public void update (float deltaTime) {

        Direction oldDir = player.direction;

        if(player.velocity.x > 0)
        {
            player.direction = Direction.RIGHT;
        }

        if(player.velocity.x < 0)
        {
            player.direction = Direction.LEFT;
        }

        if(player.velocity.y > 0)
        {
            player.direction = Direction.UP;
        }

        if(player.velocity.y < 0)
        {
            player.direction = Direction.DOWN;
        }

        if(oldDir != player.direction)
        {
            player.distWalkedDirection = 0f;
        }

        player.distWalked += player.velocity.len() * deltaTime;
        player.distWalkedDirection += player.velocity.len() * deltaTime;

    }

}
