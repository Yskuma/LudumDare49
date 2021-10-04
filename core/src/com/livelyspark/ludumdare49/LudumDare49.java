package com.livelyspark.ludumdare49;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.livelyspark.ludumdare49.managers.FloatFormatter;
import com.livelyspark.ludumdare49.managers.FormatManager;
import com.livelyspark.ludumdare49.managers.IScreenManager;
import com.livelyspark.ludumdare49.screen.*;
import com.livelyspark.ludumdare49.enums.Screens;

import java.util.HashMap;


public class LudumDare49 extends ApplicationAdapter implements IScreenManager {

    private Screen screen;
    private final AssetManager assetManager;

    private LoadingScreen loadingScreen;
    private MainMenuScreen mainMenuScreen;
    private PowerStationScreen powerStationScreen;
    private GameOverScreen gameOverScreen;
    private YouWinScreen youWinScreen;

    private String gameOverMessage;

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
            case PowerStation:
                powerStationScreen = new PowerStationScreen(this, assetManager);
                setScreen(powerStationScreen);
                break;
            case GameOver:
                if(gameOverScreen == null){gameOverScreen = new GameOverScreen(this, assetManager);}
                gameOverScreen.gameOverMessage = gameOverMessage;
                setScreen(gameOverScreen);
                break;
            case YouWin:
                if(youWinScreen == null){youWinScreen = new YouWinScreen(this, assetManager);}
                setScreen(youWinScreen);
                break;
        }
    }

    @Override
    public void setGameOverMessage(String gameOverMessage) {
        this.gameOverMessage = gameOverMessage;
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

    public LudumDare49(FloatFormatter floatFormatter){

        this.assetManager = new AssetManager();
        FormatManager.floatFormatter = floatFormatter;
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
