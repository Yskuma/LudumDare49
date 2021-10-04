package com.livelyspark.ludumdare49.systems.sound;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.livelyspark.ludumdare49.enums.Direction;
import com.livelyspark.ludumdare49.gameobj.Player;


public class PlayerSoundSystem extends EntitySystem {

    private final Player player;
    private final Sound[] sounds;
    private float lastDist = 0;
    private int nextSound = 0;

    public PlayerSoundSystem(Player player, AssetManager assetManager) {
        this.player = player;
        sounds = new Sound[]{
                        assetManager.get("sound/footstep/Footstep1.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep2.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep3.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep4.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep5.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep6.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep7.mp3", Sound.class),
                        assetManager.get("sound/footstep/Footstep8.mp3", Sound.class)
                };

    }

    @Override
    public void update(float deltaTime) {

        if (lastDist + 16 < player.distWalked) {
            sounds[nextSound].play(0.2f);
            nextSound = (nextSound + 1) % sounds.length;
            lastDist = player.distWalked;
        }

    }

}
