package com.livelyspark.ludumdare49.gameobj;

import com.badlogic.gdx.math.Vector2;
import com.livelyspark.ludumdare49.components.PositionComponent;
import com.livelyspark.ludumdare49.components.VelocityComponent;
import com.livelyspark.ludumdare49.enums.Direction;

public class Player {
    public final float speedMax = 100f;

    public PositionComponent position;
    public VelocityComponent velocity;

    public float distWalked = 0;
    public float distWalkedDirection = 0;
    public Direction direction = Direction.DOWN;

    public Player (PositionComponent position, VelocityComponent velocity)
    {
        this.position = position;
        this.velocity = velocity;
    }
}
