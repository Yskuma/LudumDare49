package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent implements Component {
    public final Boolean isLooped;
    public long soundId;
    public Sound sound;
    public float volume;

    public SoundComponent(Sound sound, Boolean isLooped) {
        this(sound, isLooped, 0.0f);
    }

    public SoundComponent(Sound sound, Boolean isLooped, float volume) {
        this.sound = sound;
        this.isLooped = isLooped;
        this.volume = volume;
    }

}
