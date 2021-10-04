package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent implements Component {
    public final Boolean isLooped;

    public long soundId;
    public Sound sound;

    public SoundComponent(Sound sound, Boolean isLooped) {
        this.sound = sound;
        this.isLooped = isLooped;
    }

}
