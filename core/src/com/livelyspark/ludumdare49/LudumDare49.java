package com.livelyspark.ludumdare49;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.screen.LoadingScreen;
import com.livelyspark.ludumdare49.screen.MainMenuScreen;
import com.livelyspark.ludumdare49.screen.Screens;

import java.util.HashMap;


public class LudumDare49 extends ApplicationAdapter implements IScreenManager {

    private Screen screen;
    private final AssetManager assetManager;

    private LoadingScreen loadingScreen;
    private MainMenuScreen mainMenuScreen;

    public void switchScreen(Screens screen)  {
        switch (screen) {
            case Loading:
                if(loadingScreen == null){loadingScreen = new LoadingScreen(this, assetManager);}
                setScreen(loadingScreen);
                break;
            case MainMenu:
                if(mainMenuScreen == null){mainMenuScreen = new MainMenuScreen(this, assetManager);}
                setScreen(mainMenuScreen);
                break;
        }
    }

    @Override
    public void create() {
        switchScreen(Screens.Loading);
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);
    }

    @Override
    public void render() {
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
    }

    @Override
    public void dispose() {
        if (screen != null) screen.hide();
    }

    private HashMap<Screens, Screen> screenStore = new HashMap<>();

    public LudumDare49(){
        this.assetManager = new AssetManager();
    }

    /** Sets the current screen. {@link Screen#hide()} is called on any old screen, and {@link Screen#show()} is called on the new
     * screen, if any.
     * @param screen may be {@code null} */
    private void setScreen (Screen screen) {
        if (this.screen != null)
        {
            this.screen.hide();
        }
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    /** @return the currently active {@link Screen}. */
    public Screen getScreen () {
        return screen;
    }

}
