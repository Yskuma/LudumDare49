package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class MessageComponent implements Component {
    public String message;
    public boolean isRead;
    public long addedTime;
    public TextureRegion icon;

    public MessageComponent(String message, TextureRegion icon)
    {
        this.message = message;
        this.isRead = false;
        this.addedTime = TimeUtils.millis();
        this.icon = icon;
    }

}
