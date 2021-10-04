package com.livelyspark.ludumdare49.systems.player;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.livelyspark.ludumdare49.components.SpriteComponent;
import com.livelyspark.ludumdare49.gameobj.Player;

public class PlayerRenderSystem extends EntitySystem {

    private final Array<TextureAtlas.AtlasRegion> upFrames;
    private final Array<TextureAtlas.AtlasRegion> downFrames;
    private final Array<TextureAtlas.AtlasRegion> leftFrames;
    private final Array<TextureAtlas.AtlasRegion> rightFrames;
    private final Player player;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public PlayerRenderSystem(OrthographicCamera camera, Player player, AssetManager assetManager) {
        batch = new SpriteBatch();
        this.camera = camera;
        this.player = player;

        TextureAtlas frames = assetManager.get("textures/dude.atlas", TextureAtlas.class);

        upFrames = frames.findRegions("dudeUp");
        downFrames = frames.findRegions("dudeDown");
        leftFrames = frames.findRegions("dudeLeft");
        rightFrames = frames.findRegions("dudeRight");
    }

    @Override
    public void addedToEngine (Engine engine) {
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {
        if(camera == null){
            return;
        }

        int frame = ((int)(player.distWalked / 16)) % 4;

        SpriteComponent sprite;
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        switch (player.direction)
        {
            case UP:
                batch.draw(upFrames.get(frame), player.position.x, player.position.y);
                break;
            case RIGHT:
                batch.draw(rightFrames.get(frame), player.position.x, player.position.y);
                break;
            case DOWN:
                batch.draw(downFrames.get(frame), player.position.x, player.position.y);
                break;
            case LEFT:
                batch.draw(leftFrames.get(frame), player.position.x, player.position.y);
                break;
        }


        batch.end();
    }
}
